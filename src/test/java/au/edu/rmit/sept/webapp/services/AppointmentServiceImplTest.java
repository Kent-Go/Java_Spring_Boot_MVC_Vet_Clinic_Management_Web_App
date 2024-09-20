package au.edu.rmit.sept.webapp.services;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Collection;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import au.edu.rmit.sept.webapp.models.Appointment;
import au.edu.rmit.sept.webapp.repositories.AppointmentRepository;

public class AppointmentServiceImplTest {

    @Mock
    private AppointmentRepository appointmentRepository;

    @InjectMocks
    private AppointmentServiceImpl appointmentService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // Test getting all appointments
    @Test
    public void testGetAllAppointments() {
        // Create multiple appointment object
        Appointment appointment1 = new Appointment(LocalDate.of(2024, 9, 30), LocalTime.of(10, 0),LocalTime.of(11, 30), 1, 1, 1);
        Appointment appointment2 = new Appointment(LocalDate.of(2024, 9, 25), LocalTime.of(15, 35),LocalTime.of(16, 35), 1, 2, 5);
        Appointment appointment3 = new Appointment(LocalDate.of(2024, 9, 28), LocalTime.of(13, 00),LocalTime.of(13, 05), 2, 1, 3);

        // Mock the findAll method to return a list of all appointments
        List<Appointment> appointments = Arrays.asList(appointment1, appointment2, appointment3);
        when(appointmentRepository.findAll()).thenReturn(appointments);

        // Call the getAllAppointments method and verify the size of the result
        Collection<Appointment> result = appointmentService.getAllAppointments();
        assertEquals(3, result.size());

        // Verify that the findAll method is called exactly once with the correct petID
        verify(appointmentRepository, times(1)).findAll();

        // Check that the appointments in the result match the expected appointments
        Appointment[] resultArray = result.toArray(new Appointment[0]);
        assertEquals(appointment1, resultArray[0]);
        assertEquals(appointment2, resultArray[1]);
        assertEquals(appointment3, resultArray[2]);
    }

    // Test get appointment based on vet id
    @Test
    public void testGetAppointmentByVetID() {
        // Create multiple appointment object
        Appointment appointment1 = new Appointment(LocalDate.of(2024, 9, 30), LocalTime.of(10, 0),LocalTime.of(11, 30), 1, 1, 1);
        Appointment appointment2 = new Appointment(LocalDate.of(2024, 9, 25), LocalTime.of(15, 35),LocalTime.of(16, 35), 1, 2, 5);
        Appointment appointment3 = new Appointment(LocalDate.of(2024, 9, 25), LocalTime.of(15, 35),LocalTime.of(16, 35), 2, 3, 5);
        Appointment appointment4 = new Appointment(LocalDate.of(2024, 10, 1), LocalTime.of(11, 00),LocalTime.of(11, 30), 1, 1, 3);
        Appointment appointment5 = new Appointment(LocalDate.of(2024, 10, 2), LocalTime.of(10, 10),LocalTime.of(10, 40), 2, 3, 5);
        Appointment appointment6 = new Appointment(LocalDate.of(2024, 10, 1), LocalTime.of(12, 00),LocalTime.of(12, 45), 1, 3, 2);

        // Mock the findByVetIDOrderByDateAscStartTimeAsc method to return a list of appointments for a specific vetID
        List<Appointment> appointments = Arrays.asList(appointment2, appointment1, appointment4, appointment6);
        when(appointmentRepository.findByVetIDOrderByDateAscStartTimeAsc(1)).thenReturn(appointments);

        // Call the getAppointmentByVetID method and verify the size of the result
        Collection<Appointment> result = appointmentService.getAppointmentByVetID(1);
        assertEquals(4, result.size());

        // Verify that the findByVetIDOrderByDateAscStartTimeAsc method is called exactly once with the correct petID
        verify(appointmentRepository, times(1)).findByVetIDOrderByDateAscStartTimeAsc(1);

        // Check the order of the appointments based on date and start time
        Appointment[] resultArray = result.toArray(new Appointment[0]);
        assertEquals(appointment2, resultArray[0]);
        assertEquals(appointment1, resultArray[1]);
        assertEquals(appointment4, resultArray[2]);
        assertEquals(appointment6, resultArray[3]);

    }

    // Test get appointment based on pet id
    @Test
    public void testGetAppointmentByPetID() {
        // Create multiple appointment object
        Appointment appointment1 = new Appointment(LocalDate.of(2024, 9, 30), LocalTime.of(10, 0),LocalTime.of(11, 30), 1, 1, 1);
        Appointment appointment2 = new Appointment(LocalDate.of(2024, 9, 25), LocalTime.of(15, 35),LocalTime.of(16, 35), 2, 1, 5);
        Appointment appointment3 = new Appointment(LocalDate.of(2024, 10, 1), LocalTime.of(11, 00),LocalTime.of(11, 30), 2, 1, 3);
        Appointment appointment4 = new Appointment(LocalDate.of(2024, 9, 30), LocalTime.of(10, 0),LocalTime.of(11, 30), 2, 2, 1);
        Appointment appointment5 = new Appointment(LocalDate.of(2024, 9, 25), LocalTime.of(15, 35),LocalTime.of(16, 35), 1, 3, 5);
        Appointment appointment6 = new Appointment(LocalDate.of(2024, 10, 1), LocalTime.of(11, 00),LocalTime.of(11, 30), 1, 2, 3);
        
        // Mock the findByPetID method to return a list of appointments for a specific petID
        List<Appointment> appointments = Arrays.asList(appointment1, appointment2, appointment3);
        when(appointmentRepository.findByPetID(1)).thenReturn(appointments);

        // Call the getAppointmentByPetID method and verify the size of the result
        Collection<Appointment> result = appointmentService.getAppointmentByPetID(1);
        assertEquals(3, result.size());

        // Verify that the findByPetID method is called exactly once with the correct petID
        verify(appointmentRepository, times(1)).findByPetID(1);

        // Check that the appointments in the result match the expected appointments
        Appointment[] resultArray = result.toArray(new Appointment[0]);
        assertEquals(appointment1, resultArray[0]);
        assertEquals(appointment2, resultArray[1]);
        assertEquals(appointment3, resultArray[2]);
    }

    // Test create a new appointment
    @Test
    public void testCreateAppointment() {
        // Create an Appointment object
        Appointment appointment = new Appointment(LocalDate.of(2024, 9, 30), LocalTime.of(10, 0), LocalTime.of(10, 30), 1, 1, 1);

        // Mock the save method to return the same appointment
        when(appointmentRepository.save(appointment)).thenReturn(appointment);

        // Call the createAppointment method and verify the result
        Appointment result = appointmentService.createAppointment(appointment);
        assertEquals(appointment, result);
        assertEquals(appointment.getDate(), result.getDate(), "Date should match");
        assertEquals(appointment.getStartTime(), result.getStartTime(), "StartTime should match");
        assertEquals(appointment.getEndTime(), result.getEndTime(), "EndTime should match");
        assertEquals(appointment.getVetID(), result.getVetID(), "VetID should match");
        assertEquals(appointment.getPetID(), result.getPetID(), "PetID should match");
        assertEquals(appointment.getAppointmentTypeID(), result.getAppointmentTypeID(), "AppointmentTypeID should match");

        // Verify that the save method is called exactly once with the correct appointment
        verify(appointmentRepository, times(1)).save(appointment);
    }

    // Test get appointments by their vetID and Date and order by startTime
    @Test
    public void testGetAppointmentsByVetIDAndDate() {
        // Create a Appointment objects with the same vetID 1 and date
        Appointment appointment1 = new Appointment(LocalDate.of(2024, 9, 30), LocalTime.of(10, 0), LocalTime.of(10, 30), 1, 1, 1);
        Appointment appointment2 = new Appointment(LocalDate.of(2024, 9, 30), LocalTime.of(13, 0), LocalTime.of(14, 0), 1, 2, 5);
        Appointment appointment3 = new Appointment(LocalDate.of(2024, 9, 30), LocalTime.of(11, 0), LocalTime.of(11, 45), 1, 3, 2);
        Appointment appointment4 = new Appointment(LocalDate.of(2024, 9, 30), LocalTime.of(16, 30), LocalTime.of(18, 0), 1, 4, 4);
        Appointment appointment5 = new Appointment(LocalDate.of(2024, 9, 30), LocalTime.of(12, 30), LocalTime.of(13, 30), 1, 5, 5);
        Appointment appointment6 = new Appointment(LocalDate.of(2024, 9, 30), LocalTime.of(14, 30), LocalTime.of(15, 30), 1, 6, 5);
        // Create a Appointment objects with other vetID and same date
        Appointment appointment7 = new Appointment(LocalDate.of(2024, 9, 30), LocalTime.of(16, 30), LocalTime.of(18, 0), 2, 4, 4);
        Appointment appointment8 = new Appointment(LocalDate.of(2024, 9, 30), LocalTime.of(12, 30), LocalTime.of(13, 30), 3, 5, 5);
        Appointment appointment9 = new Appointment(LocalDate.of(2024, 9, 30), LocalTime.of(14, 30), LocalTime.of(15, 30), 2, 6, 5);
        
        // Mock the findByVetIDAndDateOrderByStartTimeAsc method to return a list of appointments
        List<Appointment> appointments = Arrays.asList(appointment1, appointment3, appointment5, appointment2, appointment6, appointment4);
        when(appointmentRepository.findByVetIDAndDateOrderByStartTimeAsc(1, LocalDate.of(2024, 9, 30))).thenReturn(appointments);

        // Call the getAppointmentsByVetIDAndDate method and verify the result
        Collection<Appointment> result = appointmentService.getAppointmentsByVetIDAndDate(1, LocalDate.of(2024, 9, 30));
        assertEquals(6, result.size());

        // Verify that the findByVetIDAndDateOrderByStartTimeAsc method is called exactly once with the correct parameters
        verify(appointmentRepository, times(1)).findByVetIDAndDateOrderByStartTimeAsc(1, LocalDate.of(2024, 9, 30));

        // Check the order of the appointments in the result
        Appointment[] resultArray = result.toArray(new Appointment[0]);
        assertEquals(appointment1, resultArray[0]);
        assertEquals(appointment3, resultArray[1]);
        assertEquals(appointment5, resultArray[2]);
        assertEquals(appointment2, resultArray[3]);
        assertEquals(appointment6, resultArray[4]);
        assertEquals(appointment4, resultArray[5]);
    }

    // Test get appointments by their petID after a certain date
    @Test
    public void testGetAppointmentsByPetIDAndDateAfter() {
        // Create a few Appointment objects with the same petID and different dates and start time
        Appointment appointment1 = new Appointment(LocalDate.of(2024, 10, 1), LocalTime.of(10, 0), LocalTime.of(11, 30), 1, 1, 1);
        Appointment appointment2 = new Appointment(LocalDate.of(2024, 10, 5), LocalTime.of(15, 0), LocalTime.of(16, 0), 2, 1, 2);
        Appointment appointment3 = new Appointment(LocalDate.of(2024, 10, 3), LocalTime.of(12, 0), LocalTime.of(13, 0), 2, 1, 3);
        Appointment appointment4 = new Appointment(LocalDate.of(2024, 10, 2), LocalTime.of(13, 0), LocalTime.of(13, 20), 1, 1, 4);
        Appointment appointment5 = new Appointment(LocalDate.of(2024, 10, 6), LocalTime.of(15, 0), LocalTime.of(16, 0), 2, 1, 5);
        Appointment appointment6 = new Appointment(LocalDate.of(2024, 10, 6), LocalTime.of(10, 0), LocalTime.of(10, 30), 1, 1, 1);

        // Mock the findByPetIDAndDateAfterOrderByDateAscStartTimeAsc method to return a list of appointments
        List<Appointment> appointments = Arrays.asList(appointment2, appointment6, appointment5);
        when(appointmentRepository.findByPetIDAndDateAfterOrderByDateAscStartTimeAsc(1, LocalDate.of(2024, 10, 3))).thenReturn(appointments);

        // Call the getAppointmentsByPetIDAndDateAfter method and verify the result
        Collection<Appointment> result = appointmentService.getAppointmentsByPetIDAndDateAfter(1, LocalDate.of(2024, 10, 3));
        assertEquals(3, result.size());

        // Verify that the findByPetIDAndDateAfterOrderByDateAscStartTimeAsc method is called exactly once with the correct parameters
        verify(appointmentRepository, times(1)).findByPetIDAndDateAfterOrderByDateAscStartTimeAsc(1, LocalDate.of(2024, 10,3));

        // Check the order of the appointments in the result
        Appointment[] resultArray = result.toArray(new Appointment[0]);
        assertEquals(appointment2, resultArray[0]);
        assertEquals(appointment6, resultArray[1]);
        assertEquals(appointment5, resultArray[2]);
    }

}
