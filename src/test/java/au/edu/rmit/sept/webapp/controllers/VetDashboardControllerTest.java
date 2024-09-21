package au.edu.rmit.sept.webapp.controllers;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import au.edu.rmit.sept.webapp.models.Appointment;
import au.edu.rmit.sept.webapp.models.AppointmentType;
import au.edu.rmit.sept.webapp.models.Pet;
import au.edu.rmit.sept.webapp.models.PetOwner;
import au.edu.rmit.sept.webapp.models.User;
import au.edu.rmit.sept.webapp.services.AppointmentService;
import au.edu.rmit.sept.webapp.services.AppointmentTypeService;
import au.edu.rmit.sept.webapp.services.PetService;
import au.edu.rmit.sept.webapp.services.PetOwnerService;
import au.edu.rmit.sept.webapp.services.UserService;

import java.time.LocalDate;
import java.util.Collections;

@SpringBootTest
@AutoConfigureMockMvc
class VetDashboardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private PetOwnerService petOwnerService;

    @MockBean
    private PetService petService;

    @MockBean
    private AppointmentService appointmentService;

    @MockBean
    private AppointmentTypeService appointmentTypeService;

    // Test displaying the vet dashboard with valid parameters and confirming the
    // model attributes are set correctly
    @Test
    void testShowVetDashboardWithValidParams() throws Exception {
        // Mock data
        Integer userId = 1;
        Integer vetId = 1;
        LocalDate todayDate = LocalDate.now();

        User user = new User();
        user.setId(userId);

        PetOwner petOwner = new PetOwner();
        petOwner.setId(1);
        petOwner.setUserID(userId);
        petOwner.setUser(user);

        Pet pet = new Pet();
        pet.setId(1);
        pet.setPetOwnerID(petOwner.getId());
        pet.setPetOwner(petOwner);

        AppointmentType appointmentType = new AppointmentType();
        appointmentType.setId(1);

        Appointment appointment = new Appointment();
        appointment.setPetID(pet.getId());
        appointment.setAppointmentTypeID(appointmentType.getId());
        appointment.setAppointmentType(appointmentType);

        // Mock service methods

        // Get the user by their ID
        when(userService.getUserByUserID(userId)).thenReturn(user);

        // Get the pet owner by their ID
        when(petOwnerService.getPetOwnerByPetOwnerID(petOwner.getId())).thenReturn(petOwner);

        // Get the pet by its ID
        when(petService.getPetByPetID(pet.getId())).thenReturn(pet);

        // Get the appointment type by its ID
        when(appointmentTypeService.getAppointmentTypeByAppointmentTypeID(appointmentType.getId()))
                .thenReturn(appointmentType);

        // Get the appointments for the vet on the current date
        when(appointmentService.getAppointmentsByVetIDAndDate(vetId, todayDate))
                .thenReturn(Collections.singleton(appointment));

        // Perform the GET request
        MvcResult result = mockMvc.perform(get("/vetDashboard")
                .param("userId", userId.toString())
                .param("vetId", vetId.toString()))
                .andExpect(status().isOk())
                .andExpect(view().name("vetDashboard"))
                .andExpect(model().attributeExists("userId", "vetId", "weekStart", "today", "appointments"))
                .andReturn();

        // Verify the model attributes are set correctly
        assertEquals(userId, result.getModelAndView().getModel().get("userId"));
        assertEquals(vetId, result.getModelAndView().getModel().get("vetId"));
        assertEquals(todayDate, result.getModelAndView().getModel().get("today"));
        assertEquals(Collections.singleton(appointment), result.getModelAndView().getModel().get("appointments"));
    }

    // Test displaying the vet dashboard with missing userId in the request
    @Test
    void testShowVetDashboardWithInvalidUserId() throws Exception {
        // Mock data
        Integer vetId = 1;

        // Perform the GET request with missing userId
        mockMvc.perform(get("/vetDashboard")
                .param("vetId", vetId.toString()))
                .andExpect(status().isOk()) // Expecting an HTTP 200 status for successful rendering
                .andExpect(view().name("vetDashboard"))
                .andExpect(model().attributeExists("vetId", "weekStart", "today", "appointments")); // Expecting the
                                                                                                    // vetDashboard view
    }

    // Test displaying the vet dashboard with invalid vetId in the request
    @Test
    void testShowVetDashboardWithInvalidVetId() throws Exception {
        // Mock data
        Integer userId = 1;

        // Perform the GET request with invalid vetId
        mockMvc.perform(get("/vetDashboard")
                .param("userId", userId.toString())
                .param("vetId", "invalid"))
                .andExpect(status().isBadRequest()); // Expecting an HTTP 200 status for successful rendering
    }

    // Test displaying the vet dashboard with invalid userId and vetId in the
    // request
    @Test
    void testShowVetDashboardWithInvalidUserIdAndVetId() throws Exception {
        // Perform the GET request with missing both userId and vetId
        mockMvc.perform(get("/vetDashboard")
                .param("userId", "invalid")
                .param("vetId", "invalid"))
                .andExpect(status().isBadRequest());// Expecting an HTTP 200 status for successful rendering
    }

    // Test displaying the vet dashboard with missing userId and vetId in the
    // request
    @Test
    void testShowVetDashboardWithMissingVetIdAndUserId() throws Exception {
        mockMvc.perform(get("/vetDashboard"))
                .andExpect(status().isOk()) // Check for 200 OK status
                .andExpect(view().name("errorPage")) // Expect the errorPage view
                .andExpect(model().attributeExists("errorMessage")) // Ensure errorMessage is present in the model
                .andExpect(model().attribute("errorMessage", "Vet ID is missing"));
    }

    // Test displaying the vet dashboard with missing vetId
    @Test
    void testShowVetDashboardWithMissingVetId() throws Exception {
        mockMvc.perform(get("/vetDashboard")
                .param("userId", "1"))
                .andExpect(view().name("errorPage")) // Expect the errorPage view
                .andExpect(model().attributeExists("errorMessage")) // Ensure errorMessage is present in the model
                .andExpect(model().attribute("errorMessage", "Vet ID is missing"));
    }
}