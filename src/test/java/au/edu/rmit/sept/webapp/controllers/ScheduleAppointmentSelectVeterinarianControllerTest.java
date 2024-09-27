package au.edu.rmit.sept.webapp.controllers;

import au.edu.rmit.sept.webapp.models.Vet;
import au.edu.rmit.sept.webapp.models.VetAppointmentTypeOffered;
import au.edu.rmit.sept.webapp.models.User;
import au.edu.rmit.sept.webapp.services.VetAppointmentTypeOfferedService;
import au.edu.rmit.sept.webapp.services.VetService;
import au.edu.rmit.sept.webapp.services.UserService;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import static org.mockito.Mockito.when;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.hamcrest.Matchers.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

@WebMvcTest(ScheduleAppointmentSelectVeterinarianController.class)
class ScheduleAppointmentSelectVeterinarianControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VetAppointmentTypeOfferedService vetAppointmentTypeOfferedService;

    @MockBean
    private VetService vetService;

    @MockBean
    private UserService userService;

    @Test
    void testShowVetWithValidParams() throws Exception {
        Integer appointmentTypeId = 1;
        Integer clinicId = 1;

        // Mock the behavior of VetAppointmentTypeOfferedService getVetAppointmentTypeOfferedByVetIDAndAppointmentTypeID() to return corresponding VetAppointmentTypeOffered entity
        VetAppointmentTypeOffered vetAppointmentTypeOffered1 = new VetAppointmentTypeOffered(1, 1);
        VetAppointmentTypeOffered vetAppointmentTypeOffered2 = new VetAppointmentTypeOffered(2, 1);

        when(vetAppointmentTypeOfferedService.getVetAppointmentTypeOfferedByVetIDAndAppointmentTypeID(1,1)).thenReturn(Optional.of(vetAppointmentTypeOffered1));
        when(vetAppointmentTypeOfferedService.getVetAppointmentTypeOfferedByVetIDAndAppointmentTypeID(2,1)).thenReturn(Optional.of(vetAppointmentTypeOffered2));

        // Mock the behavior of VetService getVetsByClinicID() to return a list of vet from clinic id 1
        Vet vet1 = new Vet("Dr.", "English, Russian, Italian", "Basic description for a vet... uh... I'm very good at taking care of dogs and cats??", 2, 1);
        Vet vet2 = new Vet("Surgeon", "English, Spanish", "I have very steady hands and is skillful with knifes on the operating room", 4, 1);
        when(vetService.getVetByVetID(1)).thenReturn(vet1);
        when(vetService.getVetByVetID(2)).thenReturn(vet2);

        Collection<Vet> vetsInClinic = Arrays.asList(vet1, vet2);
        when(vetService.getVetsByClinicID(1)).thenReturn(vetsInClinic);

        // Mock the behavior of UserService to return a user
        User user1 = new User("Jude", "Bellingham", LocalDate.of(1980, 12, 10), "Male", "0782937410", "j_bellingham@gmail.com", "unknownHollowFeathers(hashed)");
        User user2 = new User("Sarah", "Thompson", LocalDate.of(1979, 4, 20), "Female", "0295783902", "thompsonS@yahoo.com", "hashedpassword");
        when(userService.getUserByUserID(2)).thenReturn(user1);
        when(userService.getUserByUserID(4)).thenReturn(user2);

        // Perform the GET request
        mockMvc.perform(get("/appointment/new/select_veterinarian")
                .param("appointmentTypeId", appointmentTypeId.toString())
                .param("clinicId", clinicId.toString()))
                .andExpect(status().isOk())
                .andExpect(view().name("appointmentSelectVeterinarian"))
                .andExpect(model().attributeExists("vets"))
                .andExpect(model().attribute("vets", hasSize(2)))
                .andExpect(model().attribute("vets", hasItem(
                        allOf(
                                hasProperty("title", is("Dr.")),
                                hasProperty("user", hasProperty("firstName", is("Jude"))),
                                hasProperty("user", hasProperty("lastName", is("Bellingham"))),
                                hasProperty("languagesSpoken", is("English, Russian, Italian")),
                                hasProperty("selfDescription", is("Basic description for a vet... uh... I'm very good at taking care of dogs and cats??"))
                        )
                )))
                .andExpect(model().attribute("vets", hasItem(
                        allOf(
                                hasProperty("title", is("Surgeon")),
                                hasProperty("user", hasProperty("firstName", is("Sarah"))),
                                hasProperty("user", hasProperty("lastName", is("Thompson"))),
                                hasProperty("languagesSpoken", is("English, Spanish")),
                                hasProperty("selfDescription", is("I have very steady hands and is skillful with knifes on the operating room"))
                        )
                )));
    }
}