package au.edu.rmit.sept.webapp.controllers;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.flash;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import au.edu.rmit.sept.webapp.services.AppointmentService;
import au.edu.rmit.sept.webapp.services.PetService;
import au.edu.rmit.sept.webapp.services.PetOwnerService;
import au.edu.rmit.sept.webapp.models.Appointment;
import au.edu.rmit.sept.webapp.models.Pet;
import au.edu.rmit.sept.webapp.models.PetOwner;

@WebMvcTest(controllers = CancelAppointmentController.class)
public class CancelAppointmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AppointmentService appointmentService;

    @MockBean
    private PetService petService;

    @MockBean
    private PetOwnerService petOwnerService;

    // test cancel appointment
    @Test
    public void testCancelAppointment() throws Exception {
        int appointmentId = 1;
        int petId = 1;
        int petOwnerId = 1;
        int userId = 1;

        // Mock data
        Appointment appointment = new Appointment();
        appointment.setId(appointmentId);
        appointment.setPetID(petId);

        Pet pet = new Pet();
        pet.setId(petId);
        pet.setPetOwnerID(petOwnerId);

        PetOwner petOwner = new PetOwner();
        petOwner.setId(petOwnerId);
        petOwner.setUserID(userId);

        // Mock service methods
        when(appointmentService.getAppointmentByAppointmentID(appointmentId)).thenReturn(appointment);
        when(petService.getPetByPetID(petId)).thenReturn(pet);
        when(petOwnerService.getPetOwnerByPetOwnerID(petOwnerId)).thenReturn(petOwner);

        // Perform the GET request
        mockMvc.perform(get("/appointment/cancel")
                .param("appointmentId", String.valueOf(appointmentId)))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/petOwnerWelcome?userId=" + userId + "&petOwnerId=" + petOwnerId))
                .andExpect(flash().attribute("message", "Appointment cancelled successfully."));
    }
}