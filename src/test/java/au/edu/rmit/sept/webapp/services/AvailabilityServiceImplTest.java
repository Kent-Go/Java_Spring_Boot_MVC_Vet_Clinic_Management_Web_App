package au.edu.rmit.sept.webapp.services;

import au.edu.rmit.sept.webapp.models.Availability;
import au.edu.rmit.sept.webapp.repositories.AvailabilityRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AvailabilityServiceImplTest {

    @Mock
    private AvailabilityRepository availabilityRepository;

    @InjectMocks
    private AvailabilityServiceImpl availabilityService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveAvailability_Positive() {
        // Arrange
        Availability availability = new Availability(LocalDate.of(2024, 10, 12), LocalTime.of(9, 0), LocalTime.of(17, 0));
        when(availabilityRepository.save(any(Availability.class))).thenReturn(availability);

        // Act
        Availability savedAvailability = availabilityService.saveAvailability(availability);

        // Assert
        assertNotNull(savedAvailability);
        assertEquals(LocalDate.of(2024, 10, 12), savedAvailability.getDate());
        assertEquals(LocalTime.of(9, 0), savedAvailability.getStartTime());
        assertEquals(LocalTime.of(17, 0), savedAvailability.getEndTime());
        verify(availabilityRepository, times(1)).save(availability);
    }

    @Test
    void testSaveAvailability_NullInput() {
        // Act
        Availability savedAvailability = availabilityService.saveAvailability(null);

        // Assert
        assertNull(savedAvailability);
        verify(availabilityRepository, never()).save(any());
    }


    @Test
    void testFindAvailabilityByDateAndTime_Positive() {
        // Arrange
        LocalDate date = LocalDate.of(2024, 12, 25);
        LocalTime startTime = LocalTime.of(8, 0);
        LocalTime endTime = LocalTime.of(12, 0);
        Availability availability = new Availability(date, startTime, endTime);

        when(availabilityRepository.findByDateAndStartTimeAndEndTime(date, startTime, endTime)).thenReturn(Optional.of(availability));

        // Act
        Availability foundAvailability = availabilityService.findAvailabilityByDateAndTime(date, startTime, endTime);

        // Assert
        assertNotNull(foundAvailability);
        assertEquals(date, foundAvailability.getDate());
        assertEquals(startTime, foundAvailability.getStartTime());
        assertEquals(endTime, foundAvailability.getEndTime());
        verify(availabilityRepository, times(1)).findByDateAndStartTimeAndEndTime(date, startTime, endTime);
    }

    @Test
    void testFindAvailabilityByDateAndTime_NotFound() {
        // Arrange
        LocalDate date = LocalDate.of(2025, 1, 1);
        LocalTime startTime = LocalTime.of(14, 0);
        LocalTime endTime = LocalTime.of(18, 0);

        when(availabilityRepository.findByDateAndStartTimeAndEndTime(date, startTime, endTime)).thenReturn(Optional.empty());

        // Act
        Availability foundAvailability = availabilityService.findAvailabilityByDateAndTime(date, startTime, endTime);

        // Assert
        assertNull(foundAvailability);
        verify(availabilityRepository, times(1)).findByDateAndStartTimeAndEndTime(date, startTime, endTime);
    }
}
