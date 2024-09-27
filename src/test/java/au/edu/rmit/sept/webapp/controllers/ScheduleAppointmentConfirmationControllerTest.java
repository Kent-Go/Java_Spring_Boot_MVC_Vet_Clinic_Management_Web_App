package au.edu.rmit.sept.webapp.controllers;

import au.edu.rmit.sept.webapp.models.Appointment;
import au.edu.rmit.sept.webapp.models.AppointmentType;
import au.edu.rmit.sept.webapp.models.Clinic;
import au.edu.rmit.sept.webapp.models.ClinicAppointmentTypePrice;
import au.edu.rmit.sept.webapp.models.Pet;
import au.edu.rmit.sept.webapp.models.Vet;
import au.edu.rmit.sept.webapp.models.User;
import au.edu.rmit.sept.webapp.services.AppointmentTypeService;
import au.edu.rmit.sept.webapp.services.PetService;
import au.edu.rmit.sept.webapp.services.VetService;
import au.edu.rmit.sept.webapp.services.UserService;
import au.edu.rmit.sept.webapp.services.AppointmentService;
import au.edu.rmit.sept.webapp.services.ClinicService;
import au.edu.rmit.sept.webapp.services.ClinicAppointmentTypePriceService;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import static org.hamcrest.Matchers.*;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.time.LocalDate;
import java.time.LocalTime;

@WebMvcTest(ScheduleAppointmentConfirmationController.class)
class ScheduleAppointmentConfirmationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AppointmentTypeService appointmentTypeService;

    @MockBean
    private PetService petService;

    @MockBean
    private VetService vetService;

    @MockBean
    private UserService userService;

    @MockBean
    private AppointmentService appointmentService;

    @MockBean
    private ClinicService clinicService;

    @MockBean
    private ClinicAppointmentTypePriceService clinicAppointmentTypePriceService;

    // Test rendering the appointmentConfirmation page
    @Test
    public void testDisplayAppointmentWithValidParams() throws Exception {
        Integer selectedAppointmentTypeId = 1;
        Integer selectedPetId = 1;
        Integer selectedVetId = 1;
        LocalDate selectedAppointmentDate = LocalDate.of(2023, 10, 10);
        LocalTime selectedAppointmentTime = LocalTime.of(14, 30);
        Integer selectedAppointmentTypeDuration = 30;

        // Mock data
        AppointmentType appointmentType = new AppointmentType();
        appointmentType.setId(1);
        appointmentType.setName("General Clinical Consultation");

        Pet pet = new Pet();
        pet.setId(1);
        pet.setName("Rocky");
        pet.setGender("Male");
        pet.setBreed("Chihuahua");

        Vet vet = new Vet();
        vet.setId(1);
        vet.setUserID(1);
        vet.setClinicID(1);
        vet.setTitle("Dr.");

        User user = new User();
        user.setId(1);
        user.setFirstName("Jude");
        user.setLastName("Bellingham");

        Clinic clinic = new Clinic();
        clinic.setId(1);
        clinic.setName("Frank Samways Veterinary Clinic");

        ClinicAppointmentTypePrice clinicAppointmentTypePrice = new ClinicAppointmentTypePrice();
        clinicAppointmentTypePrice.setPrice(50.0);

        // Mock service methods
        when(appointmentTypeService.getAppointmentTypeByAppointmentTypeID(selectedAppointmentTypeId)).thenReturn(appointmentType);
        when(petService.getPetByPetID(selectedPetId)).thenReturn(pet);
        when(vetService.getVetByVetID(selectedVetId)).thenReturn(vet);
        when(userService.getUserByUserID(1)).thenReturn(user);
        when(clinicService.getClinicByClinicID(1)).thenReturn(clinic);
        when(clinicAppointmentTypePriceService.getClinicAppointmentTypePriceByClinicIDAndAppointmentTypeID(1, selectedAppointmentTypeId)).thenReturn(clinicAppointmentTypePrice);

        // Perform the GET request
        mockMvc.perform(get("/appointment/new/confirmation")
                .param("selectedAppointmentTypeId", selectedAppointmentTypeId.toString())
                .param("selectedPetId", selectedPetId.toString())
                .param("selectedVetId", selectedVetId.toString())
                .param("selectedAppointmentDate", selectedAppointmentDate.toString())
                .param("selectedAppointmentTime", selectedAppointmentTime.toString())
                .param("selectedAppointmentTypeDuration", selectedAppointmentTypeDuration.toString()))
                .andExpect(status().isOk())
                .andExpect(view().name("appointmentConfirmation"))
                .andExpect(model().attributeExists("clinicName", "appointmentTypeName", "price", "petInfo", "appointmentDate", "appointmentTime", "vetTitleName"))
                .andExpect(model().attribute("clinicName", is("Frank Samways Veterinary Clinic")))
                .andExpect(model().attribute("appointmentTypeName", is("General Clinical Consultation")))
                .andExpect(model().attribute("price", is(50.0)))
                .andExpect(model().attribute("petInfo", is("Rocky (Male - Chihuahua)")))
                .andExpect(model().attribute("appointmentDate", is("10 October 2023")))
                .andExpect(model().attribute("appointmentTime", is("2:30 PM")))
                .andExpect(model().attribute("vetTitleName", is("Dr. Jude Bellingham")));
    }

    // Test rendering the rescheduleAppointmentConfirmation page
    @Test
    public void testDisplayRescheduledAppointmentWithValidParams() throws Exception {
        Integer appointmentId = 1;
        LocalDate selectedAppointmentDate = LocalDate.of(2023, 10, 10);
        LocalTime selectedAppointmentTime = LocalTime.of(14, 30);
        Integer selectedAppointmentTypeDuration = 30;

        // Mock data
        Appointment appointment = new Appointment(LocalDate.of(2023, 9, 10), LocalTime.of(11, 30), LocalTime.of(12, 0), 1, 1, 1);
        when(appointmentService.getAppointmentByAppointmentID(appointmentId)).thenReturn(appointment);

        Clinic clinic = new Clinic();
        clinic.setId(1);
        clinic.setName("Frank Samways Veterinary Clinic");

        AppointmentType appointmentType = new AppointmentType();
        appointmentType.setId(1);
        appointmentType.setName("General Clinical Consultation");

        ClinicAppointmentTypePrice clinicAppointmentTypePrice = new ClinicAppointmentTypePrice();
        clinicAppointmentTypePrice.setPrice(50.0);

        Vet vet = new Vet();
        vet.setId(1);
        vet.setUserID(1);
        vet.setClinicID(1);
        vet.setTitle("Dr.");

        User user = new User();
        user.setId(1);
        user.setFirstName("Jude");
        user.setLastName("Bellingham");
        
        Pet pet = new Pet();
        pet.setId(1);
        pet.setName("Rocky");
        pet.setGender("Male");
        pet.setBreed("Chihuahua");

        // Mock service methods
        when(clinicService.getClinicByClinicID(1)).thenReturn(clinic);
        when(appointmentTypeService.getAppointmentTypeByAppointmentTypeID(1)).thenReturn(appointmentType);
        when(clinicAppointmentTypePriceService.getClinicAppointmentTypePriceByClinicIDAndAppointmentTypeID(1, 1)).thenReturn(clinicAppointmentTypePrice);
        when(vetService.getVetByVetID(1)).thenReturn(vet);
        when(userService.getUserByUserID(1)).thenReturn(user);
        when(petService.getPetByPetID(1)).thenReturn(pet);

        // Perform the GET request
        mockMvc.perform(get("/appointment/reschedule/confirmation")
                .param("appointmentId", appointmentId.toString())
                .param("selectedAppointmentDate", selectedAppointmentDate.toString())
                .param("selectedAppointmentTime", selectedAppointmentTime.toString())
                .param("selectedAppointmentTypeDuration", selectedAppointmentTypeDuration.toString()))
                .andExpect(status().isOk())
                .andExpect(view().name("rescheduleAppointmentConfirmation"))
                .andExpect(model().attributeExists("clinicName", "appointmentTypeName", "price", "petInfo", "appointmentDate", "appointmentTime", "vetTitleName"))
                .andExpect(model().attribute("clinicName", is("Frank Samways Veterinary Clinic")))
                .andExpect(model().attribute("appointmentTypeName", is("General Clinical Consultation")))
                .andExpect(model().attribute("price", is(50.0)))
                .andExpect(model().attribute("petInfo", is("Rocky (Male - Chihuahua)")))
                .andExpect(model().attribute("appointmentDate", is("10 October 2023")))
                .andExpect(model().attribute("appointmentTime", is("2:30 PM")))
                .andExpect(model().attribute("vetTitleName", is("Dr. Jude Bellingham")));
    }

    // Test post method in the appointmentConfirmation page
    @Test
    void testConfirmAppointment() throws Exception {
        // Perform a POST request to /appointment/new/confirmation with the required parameters
        mockMvc.perform(post("/appointment/new/confirmation")
                .param("selectedAppointmentTypeId", "1")
                .param("selectedPetId", "1")
                .param("selectedVetId", "1")
                .param("selectedAppointmentDate", "2024-09-19")
                .param("selectedAppointmentTime", "10:00:00")
                .param("selectedAppointmentTypeDuration", "30")
                .param("userId", "1")
                .param("petOwnerId", "1"))
                .andExpect(status().is3xxRedirection()) // Expect a redirection status
                .andExpect(redirectedUrl("/petOwnerWelcome?userId=1&petOwnerId=1")); // Expect the redirection URL

        // Capture the Appointment object passed to the createAppointment method
        ArgumentCaptor<Appointment> appointmentCaptor = ArgumentCaptor.forClass(Appointment.class);
        verify(appointmentService, times(1)).createAppointment(appointmentCaptor.capture());

        // Verify the properties of the captured Appointment object
        Appointment capturedAppointment = appointmentCaptor.getValue();
        assertThat(capturedAppointment.getDate()).isEqualTo(LocalDate.of(2024, 9, 19));
        assertThat(capturedAppointment.getStartTime()).isEqualTo(LocalTime.of(10, 0));
        assertThat(capturedAppointment.getEndTime()).isEqualTo(LocalTime.of(10, 30));
        assertThat(capturedAppointment.getVetID()).isEqualTo(1);
        assertThat(capturedAppointment.getPetID()).isEqualTo(1);
        assertThat(capturedAppointment.getAppointmentTypeID()).isEqualTo(1);
    }
}