package au.edu.rmit.sept.webapp.controllers;

import au.edu.rmit.sept.webapp.models.AppointmentType;
import au.edu.rmit.sept.webapp.models.Pet;
import au.edu.rmit.sept.webapp.services.AppointmentTypeService;

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

@WebMvcTest(ScheduleAppointmentSelectAppointmentTypeController.class)
//@SpringBootTest
//@AutoConfigureMockMvc
class ScheduleAppointmentSelectAppointmentTypeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AppointmentTypeService AppointmentTypeService;

    // Test rendering the appointmentSelectAppointmentType page
    @Test
    void testDisplayAppointmentType() throws Exception {
        // Mock the behavior of PetService to return a list of pets
        Collection<AppointmentType> appointmentTypes = Arrays.asList(
            new AppointmentType("General Clinical Consultation", 30, "This service involves a comprehensive assessment of your pet’s overall health. The veterinarian will discuss any concerns you have, review your pet’s medical history, and provide recommendations for preventive care or treatment."),
            new AppointmentType("Physical Examination", 45, "During a physical examination, the veterinarian will thoroughly check your pet’s body, including the eyes, ears, mouth, skin, and coat. This helps in identifying any signs of illness or abnormalities early on."),
            new AppointmentType("Dental Care", 30, "Dental care services focus on maintaining your pet’s oral health. This includes teeth cleaning, polishing, and addressing any dental issues such as plaque buildup, gum disease, or tooth extractions if necessary."),
            new AppointmentType("Surgery", 90, "Veterinary surgery encompasses a wide range of procedures, from routine spaying and neutering to more complex surgeries like tumor removal or orthopedic operations. These procedures are performed under anesthesia to ensure your pet’s comfort and safety."),
            new AppointmentType("Diet and Nutrition", 60, "This service involves creating a balanced and nutritious diet plan tailored to your pet’s specific needs. The veterinarian will provide guidance on the best types of food, portion sizes, and any necessary supplements to ensure your pet’s optimal health.")            
        );
        when(AppointmentTypeService.getAllAppointmentType()).thenReturn(appointmentTypes);

        // Perform a GET request to /appointment/new/select_appointment_type
        mockMvc.perform(get("/appointment/new/select_appointment_type"))
                .andExpect(status().isOk()) // Expect the status to be OK
                .andExpect(view().name("appointmentSelectAppointmentType")) // Expect the view to be appointmentSelectAppointmentType
                .andExpect(model().attributeExists("appointmentTypes")) // Expect the model to have an attribute "appointmentTypes"
                .andExpect(model().attribute("appointmentTypes", appointmentTypes)); // Expect the "appointmentTypes" attribute to match the mocked appointment types
    }
}