package au.edu.rmit.sept.webapp.controllers;

import au.edu.rmit.sept.webapp.models.Pet;

import au.edu.rmit.sept.webapp.services.PetService;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.Mockito.when;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Arrays;

@WebMvcTest(ScheduleAppointmentSelectPetController.class)
//@SpringBootTest
//@AutoConfigureMockMvc
class ScheduleAppointmentSelectPetControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PetService petService;

    // Test rendering the appointmentSelectPet page
    @Test
    void testDisplayPet() throws Exception {
        // Mock the behavior of PetService to return a list of pets
        Collection<Pet> pets = Arrays.asList(
            new Pet("Rocky", LocalDate.of(2024, 5, 31),"Dog", "Chihuahua",	"Male", 1.5f, 1),
            new Pet("Luna", LocalDate.of(2023, 1, 31), "Dog", "Chihuahua", "Female", 2.1f, 1)
        );
        when(petService.getPetsByPetOwnerID(1)).thenReturn(pets);

        // Perform a GET request to /appointment/new/select_pet with petOwnerId=1
        mockMvc.perform(get("/appointment/new/select_pet")
                .param("petOwnerId", "1"))
                .andExpect(status().isOk()) // Expect the status to be OK
                .andExpect(view().name("appointmentSelectPet")) // Expect the view to be appointmentSelectPet
                .andExpect(model().attributeExists("pets")) // Expect the model to have an attribute "pets"
                .andExpect(model().attribute("pets", pets)); // Expect the "pets" attribute to match the mocked pets
    }
}