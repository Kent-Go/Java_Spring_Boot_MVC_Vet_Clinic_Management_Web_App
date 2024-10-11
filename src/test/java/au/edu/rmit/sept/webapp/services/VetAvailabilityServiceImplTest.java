package au.edu.rmit.sept.webapp.services;

import au.edu.rmit.sept.webapp.models.VetAvailability;
import au.edu.rmit.sept.webapp.repositories.VetAvailabilityRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class VetAvailabilityServiceImplTest {

    @Mock
    private VetAvailabilityRepository vetAvailabilityRepository;

    @InjectMocks
    private VetAvailabilityServiceImpl vetAvailabilityService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveOrUpdateVetAvailability_NewAvailability() {
        int vetId = 1;
        int availabilityId = 101;
        LocalDate date = LocalDate.of(2024, 10, 11);

        // Mock repository behavior for no existing availability
        when(vetAvailabilityRepository.findByVetIdAndDate(vetId, date)).thenReturn(Optional.empty());

        // Call the service method
        vetAvailabilityService.saveOrUpdateVetAvailability(vetId, availabilityId, date);

        // Verify that the repository's save method was called with a new VetAvailability
        verify(vetAvailabilityRepository, times(1)).save(any(VetAvailability.class));
        verify(vetAvailabilityRepository, never()).delete(any(VetAvailability.class));
    }

    @Test
    void testSaveOrUpdateVetAvailability_UpdateExistingAvailability() {
        int vetId = 1;
        int availabilityId = 102;
        LocalDate date = LocalDate.of(2024, 12, 12);
        VetAvailability existingAvailability = new VetAvailability(vetId, 100);

        // Mock repository behavior for existing availability
        when(vetAvailabilityRepository.findByVetIdAndDate(vetId, date)).thenReturn(Optional.of(existingAvailability));

        // Call the service method
        vetAvailabilityService.saveOrUpdateVetAvailability(vetId, availabilityId, date);

        // Verify that the existing availability was deleted and a new one was saved
        verify(vetAvailabilityRepository, times(1)).delete(existingAvailability);
        verify(vetAvailabilityRepository, times(1)).save(any(VetAvailability.class));
    }

    @Test
    void testFindAvailabilitiesByVetId() {
        int vetId = 1;
        List<VetAvailability> mockAvailabilities = new ArrayList<>();
        mockAvailabilities.add(new VetAvailability(vetId, 101));
        mockAvailabilities.add(new VetAvailability(vetId, 102));

        // Mock repository behavior to return a list of availabilities
        when(vetAvailabilityRepository.findAvailabilitiesByVetID(vetId)).thenReturn(mockAvailabilities);

        // Call the service method
        List<VetAvailability> result = vetAvailabilityService.findAvailabilitiesByVetId(vetId);

        // Verify the result and repository interaction
        assertEquals(2, result.size());
        verify(vetAvailabilityRepository, times(1)).findAvailabilitiesByVetID(vetId);
    }

    @Test
    void testFindByVetIdAndDate_Found() {
        int vetId = 1;
        LocalDate date = LocalDate.of(2024, 12, 12);
        VetAvailability mockAvailability = new VetAvailability(vetId, 103);

        // Mock repository behavior to return an availability
        when(vetAvailabilityRepository.findByVetIdAndDate(vetId, date)).thenReturn(Optional.of(mockAvailability));

        // Call the service method
        VetAvailability result = vetAvailabilityService.findByVetIdAndDate(vetId, date);

        // Verify the result
        assertNotNull(result);
        assertEquals(mockAvailability, result);
    }

    @Test
    void testFindByVetIdAndDate_NotFound() {
        int vetId = 1;
        LocalDate date = LocalDate.of(2024, 12, 12);

        // Mock repository behavior to return no availability
        when(vetAvailabilityRepository.findByVetIdAndDate(vetId, date)).thenReturn(Optional.empty());

        // Call the service method
        VetAvailability result = vetAvailabilityService.findByVetIdAndDate(vetId, date);

        // Verify the result is null
        assertNull(result);
    }

    @Test
    void testDeleteVetAvailability() {
        VetAvailability mockAvailability = new VetAvailability(1, 104);

        // Call the service method
        vetAvailabilityService.deleteVetAvailability(mockAvailability);

        // Verify that the repository's delete method was called
        verify(vetAvailabilityRepository, times(1)).delete(mockAvailability);
    }
}
