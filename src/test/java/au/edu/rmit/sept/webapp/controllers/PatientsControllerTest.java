package au.edu.rmit.sept.webapp.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import au.edu.rmit.sept.webapp.services.PetService;
import au.edu.rmit.sept.webapp.services.UserService;
import au.edu.rmit.sept.webapp.services.PetOwnerService;
import au.edu.rmit.sept.webapp.services.AppointmentService;

import au.edu.rmit.sept.webapp.models.Pet;
import au.edu.rmit.sept.webapp.models.User;
import au.edu.rmit.sept.webapp.models.PetOwner;
import au.edu.rmit.sept.webapp.models.Appointment;

import java.util.Arrays;
import java.time.LocalDate;
import java.util.Collections;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
@AutoConfigureMockMvc
class PatientsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PetService petService;

    @MockBean
    private UserService userService;

    @MockBean
    private PetOwnerService petOwnerService;

    @MockBean
    private AppointmentService appointmentService;

    // Test getPatients method with a valid vetId
    @Test
    void testGetPatientSuccess() throws Exception {
        int vetId = 1;

        // Mock appointments
        Appointment appointment1 = new Appointment();
        appointment1.setPetID(1);
        Appointment appointment2 = new Appointment();
        appointment2.setPetID(2);

        // Mock pets
        Pet pet1 = new Pet("Buddy", LocalDate.of(2021, 1, 17), "Dog", "Golden Retriever", "Male", 2.0f, 1);
        Pet pet2 = new Pet("Max", LocalDate.of(2022, 2, 7), "Rabbit", "Dutch", "Female", 1.2f, 1);
        when(appointmentService.getAppointmentByVetID(vetId)).thenReturn(Arrays.asList(appointment1, appointment2));
        when(petService.getPetByPetID(1)).thenReturn(pet1);
        when(petService.getPetByPetID(2)).thenReturn(pet2);

        // Mock pet owner
        PetOwner petOwner = new PetOwner(1, 1);
        when(petOwnerService.getPetOwnerByPetOwnerID(1)).thenReturn(petOwner);
        when(userService.getUserByUserID(1)).thenReturn(new User("Darren", "Cho", LocalDate.of(2003, 10, 10), "Male",
                "0429874678", "jcho@gmail.com", "password"));

        // Perform GET request
        mockMvc.perform(get("/patients?vetId=" + vetId))
                .andExpect(view().name("patients"))
                .andExpect(model().attributeExists("pets"))
                .andExpect(model().attribute("pets", Arrays.asList(
                        pet1, pet2)));

        // Verify that the model contains the vetId
        verify(appointmentService).getAppointmentByVetID(vetId);
        verify(petService).getPetByPetID(1);
        verify(petService).getPetByPetID(2);
    }

    // Test getPatients method with a valid vetId but no appointments
    @Test
    void testGetPatientsNoAppointments() throws Exception {
        int vetId = 2;

        when(appointmentService.getAppointmentByVetID(vetId)).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/patients?vetId=" + vetId))
            .andExpect(view().name("patients"))
            .andExpect(model().attribute("pets", Collections.emptyList()));
        
        verify(appointmentService).getAppointmentByVetID(vetId);
    }
}
