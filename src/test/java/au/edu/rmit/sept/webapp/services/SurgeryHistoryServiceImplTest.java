package au.edu.rmit.sept.webapp.services;

import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;
import java.time.LocalDate;
import au.edu.rmit.sept.webapp.models.SurgeryHistory;
import au.edu.rmit.sept.webapp.repositories.SurgeryHistoryRepository;

@SpringBootTest
public class SurgeryHistoryServiceImplTest {
    // Mock the repository
    @MockBean
    private SurgeryHistoryRepository surgeryHistoryRepository;

    // Inject the service
    @Autowired
    private SurgeryHistoryServiceImpl surgeryHistoryService;

    // Test the getSurgeryHistoryByPetID method with no records
    @Test
    public void testGetSurgeryHistoryByPetIDNoRecordsFound() {
        int nonExistentPetID = 999;

        when(surgeryHistoryRepository.findByPetIDOrderByDateAsc(nonExistentPetID))
                .thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            surgeryHistoryService.getSurgeryHistoryByPetID(nonExistentPetID);
        });

        assertEquals("Surgery History not found", exception.getMessage());
    }

    // Test the getSurgeryHistoryByPetID method with records
    @Test
    public void testGetSurgeryHistoryByPetIDWithRecords() {
        int petID = 1;
        List<SurgeryHistory> mockSurgeryHistories = List.of(
                new SurgeryHistory("Surgery A", LocalDate.of(2024, 1, 1), "This is a surgery", 1),
                new SurgeryHistory("Surgery B", LocalDate.of(2024, 1, 2), "This is another surgery", 1));

        when(surgeryHistoryRepository.findByPetIDOrderByDateAsc(petID))
                .thenReturn(Optional.of(mockSurgeryHistories));

        List<SurgeryHistory> surgeryHistories = surgeryHistoryService.getSurgeryHistoryByPetID(petID);

        assertNotNull(surgeryHistories);
        assertEquals(2, surgeryHistories.size());
        assertEquals("Surgery A", surgeryHistories.get(0).getName());
    }

    // Test the getSurgeryHistoryByID method with a non-existent ID
    @Test
    public void testGetSurgeryHistoryByIDNoRecordFound() {
        int nonExistentID = 999;

        when(surgeryHistoryRepository.findById(nonExistentID))
                .thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            surgeryHistoryService.getSurgeryHistoryByID(nonExistentID);
        });

        assertEquals("Surgery History not found", exception.getMessage());
    }

    // Test the getSurgeryHistoryByID method with a valid ID
    @Test
    public void testGetSurgeryHistoryByIDWithRecord() {
        int id = 1;
        // Create a SurgeryHistory object with a known ID
        SurgeryHistory mockSurgeryHistory = new SurgeryHistory("Surgery A", LocalDate.of(2024, 1, 1), "N/A", 1);
        mockSurgeryHistory.setId(id); // Set the ID to match the ID used in the repository mock

        // Mock the repository's findById method
        when(surgeryHistoryRepository.findById(id))
                .thenReturn(Optional.of(mockSurgeryHistory));

        // Call the service method
        SurgeryHistory surgeryHistory = surgeryHistoryService.getSurgeryHistoryByID(id);

        // Verify that the retrieved SurgeryHistory object is not null
        assertNotNull(surgeryHistory);

        // Verify the attributes of the retrieved SurgeryHistory object
        assertEquals("Surgery A", surgeryHistory.getName());
        assertEquals(LocalDate.of(2024, 1, 1), surgeryHistory.getDate());
        assertEquals("N/A", surgeryHistory.getNotes());
        assertEquals(1, surgeryHistory.getPetID());
    }

    // Test the saveOrUpdateSurgeryHistory method with valid data
    @Test
    public void testSaveOrUpdateSurgeryHistory() {
        SurgeryHistory surgeryHistory = new SurgeryHistory();
        surgeryHistory.setPetID(1);
        surgeryHistory.setName("Surgery A");
        surgeryHistory.setDate(LocalDate.of(2024, 1, 1));

        when(surgeryHistoryRepository.save(surgeryHistory)).thenReturn(surgeryHistory);

        SurgeryHistory savedSurgeryHistory = surgeryHistoryService.saveOrUpdateSurgeryHistory(surgeryHistory);

        assertNotNull(savedSurgeryHistory);
        assertEquals("Surgery A", savedSurgeryHistory.getName());
        assertEquals(1, savedSurgeryHistory.getPetID());
    }

    // Test the saveOrUpdateSurgeryHistory method with an existing ID and modified name
    @Test
    public void testSaveOrUpdateSurgeryHistoryNameWithExistingID() {
        // Setup
        int id = 1;
        SurgeryHistory existingSurgeryHistory = new SurgeryHistory();
        existingSurgeryHistory.setId(id); // Preset ID
        existingSurgeryHistory.setPetID(1);
        existingSurgeryHistory.setName("Existing Surgery");

        // Mock repository to return the existing record
        when(surgeryHistoryRepository.findById(id)).thenReturn(Optional.of(existingSurgeryHistory));

        // Simulate modifications
        existingSurgeryHistory.setName("Updated Surgery");

        // Mock the save method to return the modified record
        when(surgeryHistoryRepository.save(existingSurgeryHistory)).thenReturn(existingSurgeryHistory);

        // Execute the service method
        SurgeryHistory savedSurgeryHistory = surgeryHistoryService.saveOrUpdateSurgeryHistory(existingSurgeryHistory);

        // Verify that save was called once with the modified record
        verify(surgeryHistoryRepository, times(1)).save(existingSurgeryHistory);

        // Assertions
        assertNotNull(savedSurgeryHistory);
        assertEquals(id, savedSurgeryHistory.getId());
        assertEquals("Updated Surgery", savedSurgeryHistory.getName());
    }

    // Test the saveOrUpdateSurgeryHistory method with a new ID
    @Test
    public void testSaveOrUpdateSurgeryHistoryNameWithNewID() {
        // Setup
        SurgeryHistory newSurgeryHistory = new SurgeryHistory();
        newSurgeryHistory.setPetID(1);
        newSurgeryHistory.setName("New Surgery");

        // Mock the save method to return the new record
        when(surgeryHistoryRepository.save(newSurgeryHistory)).thenReturn(newSurgeryHistory);

        // Execute the service method
        SurgeryHistory savedSurgeryHistory = surgeryHistoryService.saveOrUpdateSurgeryHistory(newSurgeryHistory);

        // Verify that save was called once with the new record
        verify(surgeryHistoryRepository, times(1)).save(newSurgeryHistory);

        // Assertions
        assertNotNull(savedSurgeryHistory);
        assertEquals("New Surgery", savedSurgeryHistory.getName());
    }

    // Test the saveOrUpdateSurgeryHistory method with an existing ID and modified date
    @Test
    public void testSaveOrUpdateSurgeryHistoryDateWithExistingID() {
        // Setup
        int id = 1;
        SurgeryHistory existingSurgeryHistory = new SurgeryHistory();
        existingSurgeryHistory.setId(id); // Preset ID
        existingSurgeryHistory.setPetID(1);
        existingSurgeryHistory.setName("Existing Surgery");
        existingSurgeryHistory.setDate(LocalDate.of(2024, 1, 1));

        // Mock repository to return the existing record
        when(surgeryHistoryRepository.findById(id)).thenReturn(Optional.of(existingSurgeryHistory));

        // Simulate modifications
        existingSurgeryHistory.setDate(LocalDate.of(2024, 1, 2));

        // Mock the save method to return the modified record
        when(surgeryHistoryRepository.save(existingSurgeryHistory)).thenReturn(existingSurgeryHistory);

        // Execute the service method
        SurgeryHistory savedSurgeryHistory = surgeryHistoryService.saveOrUpdateSurgeryHistory(existingSurgeryHistory);

        // Verify that save was called once with the modified record
        verify(surgeryHistoryRepository, times(1)).save(existingSurgeryHistory);

        // Assertions
        assertNotNull(savedSurgeryHistory);
        assertEquals(id, savedSurgeryHistory.getId());
        assertEquals(LocalDate.of(2024, 1, 2), savedSurgeryHistory.getDate());
    }

    // Test the saveOrUpdateSurgeryHistory method with a new ID
    @Test
    public void testSaveOrUpdateSurgeryHistoryDateWithNewID() {
        // Setup
        SurgeryHistory newSurgeryHistory = new SurgeryHistory();
        newSurgeryHistory.setPetID(1);
        newSurgeryHistory.setName("New Surgery");
        newSurgeryHistory.setDate(LocalDate.of(2024, 1, 1));

        // Mock the save method to return the new record
        when(surgeryHistoryRepository.save(newSurgeryHistory)).thenReturn(newSurgeryHistory);

        // Execute the service method
        SurgeryHistory savedSurgeryHistory = surgeryHistoryService.saveOrUpdateSurgeryHistory(newSurgeryHistory);

        // Verify that save was called once with the new record
        verify(surgeryHistoryRepository, times(1)).save(newSurgeryHistory);

        // Assertions
        assertNotNull(savedSurgeryHistory);
        assertEquals(LocalDate.of(2024, 1, 1), savedSurgeryHistory.getDate());
    }

    // Test the saveOrUpdateSurgeryHistory method with an existing ID and modified pet ID
    @Test
    public void testSaveOrUpdateSurgeryHistoryPetIDWithExistingID() {
        // Setup
        int id = 1;
        SurgeryHistory existingSurgeryHistory = new SurgeryHistory();
        existingSurgeryHistory.setId(id); // Preset ID
        existingSurgeryHistory.setPetID(1);
        existingSurgeryHistory.setName("Existing Surgery");

        // Mock repository to return the existing record
        when(surgeryHistoryRepository.findById(id)).thenReturn(Optional.of(existingSurgeryHistory));

        // Simulate modifications
        existingSurgeryHistory.setPetID(2);

        // Mock the save method to return the modified record
        when(surgeryHistoryRepository.save(existingSurgeryHistory)).thenReturn(existingSurgeryHistory);

        // Execute the service method
        SurgeryHistory savedSurgeryHistory = surgeryHistoryService.saveOrUpdateSurgeryHistory(existingSurgeryHistory);

        // Verify that save was called once with the modified record
        verify(surgeryHistoryRepository, times(1)).save(existingSurgeryHistory);

        // Assertions
        assertNotNull(savedSurgeryHistory);
        assertEquals(id, savedSurgeryHistory.getId());
        assertEquals(2, savedSurgeryHistory.getPetID());
    }

    // Test the saveOrUpdateSurgeryHistory method with a new ID
    @Test
    public void testSaveOrUpdateSurgeryHistoryPetIDWithNewID() {
        // Setup
        SurgeryHistory newSurgeryHistory = new SurgeryHistory();
        newSurgeryHistory.setPetID(1);
        newSurgeryHistory.setName("New Surgery");

        // Mock the save method to return the new record
        when(surgeryHistoryRepository.save(newSurgeryHistory)).thenReturn(newSurgeryHistory);

        // Execute the service method
        SurgeryHistory savedSurgeryHistory = surgeryHistoryService.saveOrUpdateSurgeryHistory(newSurgeryHistory);

        // Verify that save was called once with the new record
        verify(surgeryHistoryRepository, times(1)).save(newSurgeryHistory);

        // Assertions
        assertNotNull(savedSurgeryHistory);
        assertEquals(1, savedSurgeryHistory.getPetID());
    }

    // Test the saveOrUpdateSurgeryHistory method with an existing ID and modified notes
    @Test
    public void testSaveOrUpdateSurgeryHistoryNotesWithExistingID() {
        // Setup
        int id = 1;
        SurgeryHistory existingSurgeryHistory = new SurgeryHistory();
        existingSurgeryHistory.setId(id); // Preset ID
        existingSurgeryHistory.setPetID(1);
        existingSurgeryHistory.setName("Existing Surgery");
        existingSurgeryHistory.setNotes("N/A");

        // Mock repository to return the existing record
        when(surgeryHistoryRepository.findById(id)).thenReturn(Optional.of(existingSurgeryHistory));

        // Simulate modifications
        existingSurgeryHistory.setNotes("Updated notes");

        // Mock the save method to return the modified record
        when(surgeryHistoryRepository.save(existingSurgeryHistory)).thenReturn(existingSurgeryHistory);

        // Execute the service method
        SurgeryHistory savedSurgeryHistory = surgeryHistoryService.saveOrUpdateSurgeryHistory(existingSurgeryHistory);

        // Verify that save was called once with the modified record
        verify(surgeryHistoryRepository, times(1)).save(existingSurgeryHistory);

        // Assertions
        assertNotNull(savedSurgeryHistory);
        assertEquals(id, savedSurgeryHistory.getId());
        assertEquals("Updated notes", savedSurgeryHistory.getNotes());
    }

    // Test the saveOrUpdateSurgeryHistory method with a new ID
    @Test
    public void testSaveOrUpdateSurgeryHistoryNotesWithNewID() {
        // Setup
        SurgeryHistory newSurgeryHistory = new SurgeryHistory();
        newSurgeryHistory.setPetID(1);
        newSurgeryHistory.setName("New Surgery");
        newSurgeryHistory.setNotes("N/A");

        // Mock the save method to return the new record
        when(surgeryHistoryRepository.save(newSurgeryHistory)).thenReturn(newSurgeryHistory);

        // Execute the service method
        SurgeryHistory savedSurgeryHistory = surgeryHistoryService.saveOrUpdateSurgeryHistory(newSurgeryHistory);

        // Verify that save was called once with the new record
        verify(surgeryHistoryRepository, times(1)).save(newSurgeryHistory);

        // Assertions
        assertNotNull(savedSurgeryHistory);
        assertEquals("N/A", savedSurgeryHistory.getNotes());
    }
}