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
import au.edu.rmit.sept.webapp.models.ImmunisationHistory;
import au.edu.rmit.sept.webapp.repositories.ImmunisationHistoryRepository;

@SpringBootTest
class ImmunisationHistoryServiceImplTest {

    // Mock the repository
    @MockBean
    private ImmunisationHistoryRepository immunisationHistoryRepository;

    // Inject the service
    @Autowired
    private ImmunisationHistoryServiceImpl immunisationHistoryService;

    // Test the getImmunisationHistoryByID method with valid ID
    @Test
    void testGetImmunisationHistoryByID() {
        int validId = 1;

        // Create a sample ImmunisationHistory object
        ImmunisationHistory immunisationHistory = new ImmunisationHistory();
        immunisationHistory.setId(validId);

        // Mock the repository to return the optional immunisation history
        when(immunisationHistoryRepository.findById(validId))
                .thenReturn(Optional.of(immunisationHistory));

        // Call the service and assert that it is not null
        ImmunisationHistory result = immunisationHistoryService.getImmunisationHistoryByID(validId);
        assertNotNull(result, "ImmunisationHistory should not be null");

        // Verify that the repository method was called once
        verify(immunisationHistoryRepository, times(1)).findById(validId);
    }

    // Test the getImmunisationHistoryByPetID method with valid data
    @Test
    void testGetImmunisationHistoryByPetID() {
        int validPetID = 1;

        // Create a sample ImmunisationHistory object
        ImmunisationHistory immunisationHistory = new ImmunisationHistory();
        immunisationHistory.setPetID(validPetID);

        // Mock the repository to return the optional immunisation history
        when(immunisationHistoryRepository.findByPetIDOrderByDateAsc(validPetID))
                .thenReturn(Optional.of(List.of(immunisationHistory)));

        // Call the service and assert that it is not null
        assertNotNull(immunisationHistoryService.getImmunisationHistoryByPetID(validPetID),
                "ImmunisationHistory list should not be null");

        // Verify that the repository method was called once
        verify(immunisationHistoryRepository, times(1)).findByPetIDOrderByDateAsc(validPetID);
    }

    // Test the getImmunisationHistoryByPetID method with not found
    @Test
    void testGetImmunisationHistoryByPetIDNoRecordsFound() {
        int nonExistentPetID = 999;

        // Mock the repository to return an empty Optional when searching by pet ID
        when(immunisationHistoryRepository.findByPetIDOrderByDateAsc(nonExistentPetID))
                .thenReturn(Optional.empty());

        // Expect a RuntimeException since no records exist for this Pet ID
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            immunisationHistoryService.getImmunisationHistoryByPetID(nonExistentPetID);
        });

        // Check that the exception message is as expected
        assertEquals("Immunisation History not found", exception.getMessage());

        // Verify that the repository method was called once
        verify(immunisationHistoryRepository, times(1)).findByPetIDOrderByDateAsc(nonExistentPetID);
    }

    // Test the saveOrUpdateImmunisationHistory method with valid data
    @Test
    void testSaveOrUpdateImmunisationHistory() {
        // Create a new ImmunisationHistory object
        ImmunisationHistory immunisationHistory = new ImmunisationHistory();
        immunisationHistory.setPetID(1); // Assuming a Pet with ID 1 exists
        immunisationHistory.setName("Rabies");
        immunisationHistory.setDate(LocalDate.of(2024, 1, 1));

        // Mock the repository save behavior
        when(immunisationHistoryRepository.save(immunisationHistory)).thenReturn(immunisationHistory);

        // Save the immunisation record using the service
        ImmunisationHistory savedImmunisation = immunisationHistoryService
                .saveOrUpdateImmunisationHistory(immunisationHistory);

        // Assert that the record was saved and an ID was generated
        assertNotNull(savedImmunisation, "Saved immunisation record should not be null");
        assertEquals(immunisationHistory.getName(), savedImmunisation.getName(), "Immunisation name should match");
        assertEquals(immunisationHistory.getDate(), savedImmunisation.getDate(), "Immunisation date should match");

        // Verify that the save method was called once
        verify(immunisationHistoryRepository, times(1)).save(immunisationHistory);
    }

    // Test the getImmunisationHistoryByID method with a non-existent ID
    @Test
    void testGetImmunisationHistoryByIDNotFound() {
        // Test for a non-existent immunisation record with ID 999
        int nonExistentID = 999;

        // Attempt to retrieve the record, expecting null
        ImmunisationHistory result = immunisationHistoryService.getImmunisationHistoryByID(nonExistentID);

        // Assert that no record is found
        assertNull(result);

        // Verify that the repository method was called once
        verify(immunisationHistoryRepository, times(1)).findById(nonExistentID);
    }
}
