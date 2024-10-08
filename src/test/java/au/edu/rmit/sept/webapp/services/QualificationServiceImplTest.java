package au.edu.rmit.sept.webapp.services;

import au.edu.rmit.sept.webapp.models.Qualification;
import au.edu.rmit.sept.webapp.repositories.QualificationRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class QualificationServiceImplTest {

    @Mock
    private QualificationRepository qualificationRepository;

    @InjectMocks
    private QualificationServiceImpl qualificationService;

    public QualificationServiceImplTest() {
        MockitoAnnotations.openMocks(this);
    }

    // Positive Test - Get qualifications by valid vetID
    @Test
    void testGetQualificationsByVetID() {
        int validVetID = 1;

        // Create a mock qualification list for the given vet ID
        Qualification qualification = new Qualification("Veterinary Science", "University A", "Country X", 2020,
                validVetID);
        List<Qualification> mockQualifications = List.of(qualification);

        // Mock the repository to return the qualifications
        when(qualificationRepository.findByVetID(validVetID)).thenReturn(mockQualifications);

        // Execute the service method
        List<Qualification> qualifications = qualificationService.getQualificationsByVetID(validVetID);

        // Verify the results
        assertNotNull(qualifications);
        assertEquals(1, qualifications.size());
        assertEquals("Veterinary Science", qualifications.get(0).getName());

        // Verify that the repository was called
        verify(qualificationRepository, times(1)).findByVetID(validVetID);
    }

    // Negative Test - Get qualifications by invalid vetID (empty list expected)
    @Test
    void testGetQualificationsByInvalidVetID() {
        int invalidVetID = 999;

        // Mock the repository to return an empty list
        when(qualificationRepository.findByVetID(invalidVetID)).thenReturn(List.of());

        // Execute the service method
        List<Qualification> qualifications = qualificationService.getQualificationsByVetID(invalidVetID);

        // Verify the results
        assertNotNull(qualifications);
        assertTrue(qualifications.isEmpty());

        // Verify that the repository was called
        verify(qualificationRepository, times(1)).findByVetID(invalidVetID);
    }

    // Positive Test - Create a valid qualification
    @Test
    void testCreateQualification() {
        int vetID = 1;
        Qualification qualification = new Qualification("Veterinary Medicine", "University B", "Country Y", 2021,
                vetID);

        // Mock the repository to save the qualification
        when(qualificationRepository.save(qualification)).thenReturn(qualification);

        // Execute the service method
        Qualification createdQualification = qualificationService.createQualification(qualification);

        // Verify the results
        assertNotNull(createdQualification);
        assertEquals("Veterinary Medicine", createdQualification.getName());
        assertEquals(vetID, createdQualification.getVetID());

        // Verify that the repository was called
        verify(qualificationRepository, times(1)).save(qualification);
    }

    // Negative Test - Try to create a qualification with missing data (invalid
    // input)
    @Test
    void testCreateQualificationWithMissingData() {
        int vetID = 1;
        Qualification invalidQualification = new Qualification("", "University B", "Country Y", 2021, vetID);

        // Mock the repository to save the qualification
        when(qualificationRepository.save(invalidQualification)).thenReturn(invalidQualification);

        // Execute the service method
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            qualificationService.createQualification(invalidQualification);
        });

        // Verify the exception message
        assertEquals("Qualification name cannot be empty", exception.getMessage());

        // Verify that the repository was not called
        verify(qualificationRepository, never()).save(invalidQualification);
    }

    // Boundary Test - Year for qualification (assume we have a boundary for the
    // year)
    @Test
    void testCreateQualificationBoundaryYear() {
        int vetID = 1;
        Qualification qualification = new Qualification("Veterinary Surgery", "University C", "Country Z", 1900, vetID);

        // Mock the repository to save the qualification
        when(qualificationRepository.save(qualification)).thenReturn(qualification);

        // Execute the service method
        Qualification createdQualification = qualificationService.createQualification(qualification);

        // Verify the results
        assertNotNull(createdQualification);
        assertEquals(1900, createdQualification.getYear());

        // Verify that the repository was called
        verify(qualificationRepository, times(1)).save(qualification);
    }
}
