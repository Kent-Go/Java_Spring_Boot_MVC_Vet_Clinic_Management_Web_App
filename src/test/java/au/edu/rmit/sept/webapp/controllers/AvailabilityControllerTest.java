package au.edu.rmit.sept.webapp.controllers;

import au.edu.rmit.sept.webapp.models.Appointment;
import au.edu.rmit.sept.webapp.models.Availability;
import au.edu.rmit.sept.webapp.models.VetAvailability;
import au.edu.rmit.sept.webapp.services.AvailabilityService;
import au.edu.rmit.sept.webapp.services.AppointmentService;
import au.edu.rmit.sept.webapp.services.VetAvailabilityService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import static org.mockito.Mockito.mockStatic;
import java.time.temporal.TemporalAdjusters;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.time.DayOfWeek;
import org.mockito.MockedStatic;


public class AvailabilityControllerTest {

    @Mock
    private AvailabilityService availabilityService;

    @Mock
    private VetAvailabilityService vetAvailabilityService;

    @Mock
    private AppointmentService appointmentService;

    @Mock
    private Model model;

    @Mock
    private RedirectAttributes redirectAttributes;

    @InjectMocks
    private AvailabilityController availabilityController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // Positive Test Case: getAvailability returns the correct view name
    @Test
    public void testGetAvailability_Success() {
        int vetId = 1;
        List<VetAvailability> availabilities = new ArrayList<>();

        // Set a fixed date for the test to control weekStart calculation
        LocalDate fixedToday = LocalDate.of(2024, 10, 12); // Set to the desired date
        LocalDate expectedWeekStart = fixedToday.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));

        try (MockedStatic<LocalDate> mockedLocalDate = mockStatic(LocalDate.class, CALLS_REAL_METHODS)) {
            mockedLocalDate.when(LocalDate::now).thenReturn(fixedToday);

            when(vetAvailabilityService.findAvailabilitiesByVetId(vetId)).thenReturn(availabilities);

            String viewName = availabilityController.getAvailability(vetId, model);

            verify(model).addAttribute("availabilities", availabilities);
            verify(model).addAttribute("vetId", vetId);
            verify(model).addAttribute("weekStart", expectedWeekStart); // Mocked weekStart
            assertEquals("availability", viewName);
        }
    }

    // Negative Test Case: getAvailability with non-existent vetId
    @Test
    public void testGetAvailability_NoData() {
        int vetId = 999; // non-existent vetId
        when(vetAvailabilityService.findAvailabilitiesByVetId(vetId)).thenReturn(new ArrayList<>());

        String viewName = availabilityController.getAvailability(vetId, model);

        verify(model).addAttribute("availabilities", new ArrayList<>());
        verify(model).addAttribute("vetId", vetId);
        assertEquals("availability", viewName);
    }


    // Negative Test Case: saveAvailability with missing date
    @Test
    public void testSaveAvailability_MissingDate() {
        int vetId = 1;
        LocalDate date = null; // Missing date
        LocalTime startTime = LocalTime.of(9, 0);
        LocalTime endTime = LocalTime.of(17, 0);

        // Mocking the redirectAttributes
        RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);

        // Call the saveAvailability method with the missing date
        String result = availabilityController.saveAvailability(vetId, date, startTime, endTime, redirectAttributes);

        // Verify that saveAvailability on availabilityService was never called
        verify(availabilityService, never()).saveAvailability(any(Availability.class));

        // Verify that an error message was added to redirectAttributes
        verify(redirectAttributes).addFlashAttribute("errorMessage", "Date is required.");

        // Assert that the redirect URL is correct
        assertEquals("redirect:/availability?vetId=" + vetId, result);
    }


    // Positive Test Case: saveAvailability creates a new availability
    @Test
    public void testSaveAvailability_CreateNew() {
        int vetId = 1;
        LocalDate date = LocalDate.now();
        LocalTime startTime = LocalTime.of(9, 0);
        LocalTime endTime = LocalTime.of(17, 0);

        Availability mockAvailability = new Availability(date, startTime, endTime);
        mockAvailability.setId(1); // Set an ID to avoid null when accessing getId()

        when(vetAvailabilityService.findByVetIdAndDate(vetId, date)).thenReturn(null);
        when(availabilityService.saveAvailability(any(Availability.class))).thenReturn(mockAvailability);
        when(appointmentService.existsByVetIdAndDate(vetId, date)).thenReturn(false); // No appointments

        String result = availabilityController.saveAvailability(vetId, date, startTime, endTime, redirectAttributes);

        verify(availabilityService, times(1)).saveAvailability(any(Availability.class));
        verify(vetAvailabilityService, times(1)).saveOrUpdateVetAvailability(vetId, mockAvailability.getId(), date);
        verify(redirectAttributes).addFlashAttribute("successMessage", "Availability created successfully.");
        assertEquals("redirect:/availability?vetId=" + vetId, result);
    }

    // Negative Test Case: saveAvailability when there are appointments
    @Test
    public void testSaveAvailability_WithAppointments() {
        int vetId = 1;
        LocalDate date = LocalDate.now();
        LocalTime startTime = LocalTime.of(9, 0);
        LocalTime endTime = LocalTime.of(17, 0);

        // Mock the appointmentService to return that there are existing appointments on the given date
        when(appointmentService.existsByVetIdAndDate(vetId, date)).thenReturn(true);

        String result = availabilityController.saveAvailability(vetId, date, startTime, endTime, redirectAttributes);

        // Verify that saveAvailability on availabilityService was never called
        verify(availabilityService, never()).saveAvailability(any(Availability.class));
        verify(redirectAttributes).addFlashAttribute("errorMessage", "Cannot update availability as there are appointments scheduled.");
        assertEquals("redirect:/availability?vetId=" + vetId, result);
    }

    // Positive Test Case: deleteAvailability deletes an existing availability
    @Test
    public void testDeleteAvailability_Success() {
        int vetId = 1;
        LocalDate date = LocalDate.now();
        VetAvailability vetAvailability = new VetAvailability();

        when(vetAvailabilityService.findByVetIdAndDate(vetId, date)).thenReturn(vetAvailability);
        when(appointmentService.existsByVetIdAndDate(vetId, date)).thenReturn(false); // No appointments

        String result = availabilityController.deleteAvailability(vetId, date, redirectAttributes);

        verify(vetAvailabilityService, times(1)).deleteVetAvailability(vetAvailability);
        verify(redirectAttributes).addFlashAttribute("successMessage", "Availability deleted successfully.");
        assertEquals("redirect:/availability?vetId=" + vetId, result);
    }

    // Negative Test Case: deleteAvailability when there are appointments
    @Test
    public void testDeleteAvailability_WithAppointments() {
        int vetId = 1;
        LocalDate date = LocalDate.now();

        // Mock that there are existing appointments on the date
        when(appointmentService.existsByVetIdAndDate(vetId, date)).thenReturn(true);

        String result = availabilityController.deleteAvailability(vetId, date, redirectAttributes);

        // Verify that deleteVetAvailability was never called
        verify(vetAvailabilityService, never()).deleteVetAvailability(any(VetAvailability.class));
        verify(redirectAttributes).addFlashAttribute("errorMessage", "Cannot delete availability as there are appointments scheduled.");
        assertEquals("redirect:/availability?vetId=" + vetId, result);
    }

    // Negative Test Case: deleteAvailability when no availability exists
    @Test
    public void testDeleteAvailability_NoAvailability() {
        int vetId = 1;
        LocalDate date = LocalDate.now();

        when(vetAvailabilityService.findByVetIdAndDate(vetId, date)).thenReturn(null);

        String result = availabilityController.deleteAvailability(vetId, date, redirectAttributes);

        verify(vetAvailabilityService, never()).deleteVetAvailability(any(VetAvailability.class));
        verify(redirectAttributes).addFlashAttribute("errorMessage", "No availability found for the given vet and date.");
        assertEquals("redirect:/availability?vetId=" + vetId, result);
    }
}
