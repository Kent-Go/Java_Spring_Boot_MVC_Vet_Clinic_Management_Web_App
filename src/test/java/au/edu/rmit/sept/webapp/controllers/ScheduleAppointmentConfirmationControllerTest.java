// package au.edu.rmit.sept.webapp.controllers;

// import au.edu.rmit.sept.webapp.models.Appointment;
// import au.edu.rmit.sept.webapp.models.AppointmentType;
// import au.edu.rmit.sept.webapp.models.Pet;
// import au.edu.rmit.sept.webapp.models.Vet;
// import au.edu.rmit.sept.webapp.models.User;
// import au.edu.rmit.sept.webapp.services.AppointmentTypeService;
// import au.edu.rmit.sept.webapp.services.PetService;
// import au.edu.rmit.sept.webapp.services.VetService;
// import au.edu.rmit.sept.webapp.services.UserService;
// import au.edu.rmit.sept.webapp.services.AppointmentService;

// import org.junit.jupiter.api.Test;
// import org.mockito.ArgumentCaptor;
// import org.springframework.boot.test.mock.mockito.MockBean;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

// import static org.mockito.Mockito.times;
// import static org.mockito.Mockito.verify;
// import static org.mockito.Mockito.when;
// import static org.assertj.core.api.Assertions.assertThat;
// import org.springframework.test.web.servlet.MockMvc;
// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
// import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
// import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

// import java.time.LocalDate;
// import java.time.LocalTime;

// @WebMvcTest(ScheduleAppointmentConfirmationController.class)
// class ScheduleAppointmentConfirmationControllerTest {

//     @Autowired
//     private MockMvc mockMvc;

//     @MockBean
//     private AppointmentTypeService appointmentTypeService;

//     @MockBean
//     private PetService petService;

//     @MockBean
//     private VetService vetService;

//     @MockBean
//     private UserService userService;

//     @MockBean
//     private AppointmentService appointmentService;

//     // Test rendering the appointmentConfirmation page
//     @Test
//     void testDisplayAppointment() throws Exception {
//         // Mock the behavior of AppointmentTypeService to return an appointment type
//         AppointmentType appointmentType = new AppointmentType("General Clinical Consultation", 30, "This service involves a comprehensive assessment of your pet’s overall health. The veterinarian will discuss any concerns you have, review your pet’s medical history, and provide recommendations for preventive care or treatment.");
//         when(appointmentTypeService.getAppointmentTypeByAppointmentTypeID(1)).thenReturn(appointmentType);

//         // Mock the behavior of PetService to return a pet
//         Pet pet = new Pet("Rocky", LocalDate.of(2024, 5, 31), "Dog", "Chihuahua", "Male", 1.5f, 1);
//         when(petService.getPetByPetID(1)).thenReturn(pet);

//         // Mock the behavior of VetService to return a vet
//         Vet vet = new Vet("Dr.", "English, Russian, Italian", "Basic description for a vet... uh... I'm very good at taking care of dogs and cats??", 2, 1);
//         when(vetService.getVetByVetID(1)).thenReturn(vet);

//         // Mock the behavior of UserService to return a user
//         User user = new User("Jude", "Bellingham", LocalDate.of(1980, 12, 10), "Male", "0782937410", "j_bellingham@gmail.com", "unknownHollowFeathers(hashed)");
//         when(userService.getUserByUserID(2)).thenReturn(user);

//         // Perform a GET request to /appointment/new/confirmation with the required parameters
//         mockMvc.perform(get("/appointment/new/confirmation")
//                 .param("selectedAppointmentTypeId", "1")
//                 .param("selectedPetId", "1")
//                 .param("selectedVetId", "1")
//                 .param("selectedAppointmentDate", "2024-09-19")
//                 .param("selectedAppointmentTime", "10:00:00")
//                 .param("selectedAppointmentTypeDuration", "30"))
//                 .andExpect(status().isOk()) // Expect the status to be OK
//                 .andExpect(view().name("appointmentConfirmation")) // Expect the view to be appointmentConfirmation
//                 .andExpect(model().attributeExists("appointmentTypeName"))
//                 .andExpect(model().attributeExists("petInfo"))
//                 .andExpect(model().attributeExists("appointmentDate"))
//                 .andExpect(model().attributeExists("appointmentTime"))
//                 .andExpect(model().attributeExists("vetTitleName"))
//                 .andExpect(model().attribute("appointmentTypeName", "General Clinical Consultation"))
//                 .andExpect(model().attribute("petInfo", "Rocky (Male - Chihuahua)"))
//                 .andExpect(model().attribute("appointmentDate", "19 September 2024"))
//                 .andExpect(model().attribute("appointmentTime", "10:00 AM"))
//                 .andExpect(model().attribute("vetTitleName", "Dr. Jude Bellingham"));
//     }

//     // Test post method in the appointmentConfirmation page
//     @Test
//     void testConfirmAppointment() throws Exception {
//         // Perform a POST request to /appointment/new/confirmation with the required parameters
//         mockMvc.perform(post("/appointment/new/confirmation")
//                 .param("selectedAppointmentTypeId", "1")
//                 .param("selectedPetId", "1")
//                 .param("selectedVetId", "1")
//                 .param("selectedAppointmentDate", "2024-09-19")
//                 .param("selectedAppointmentTime", "10:00:00")
//                 .param("selectedAppointmentTypeDuration", "30")
//                 .param("userId", "1")
//                 .param("petOwnerId", "1"))
//                 .andExpect(status().is3xxRedirection()) // Expect a redirection status
//                 .andExpect(redirectedUrl("/petOwnerWelcome?userId=1&petOwnerId=1")); // Expect the redirection URL

//         // Capture the Appointment object passed to the createAppointment method
//         ArgumentCaptor<Appointment> appointmentCaptor = ArgumentCaptor.forClass(Appointment.class);
//         verify(appointmentService, times(1)).createAppointment(appointmentCaptor.capture());

//         // Verify the properties of the captured Appointment object
//         Appointment capturedAppointment = appointmentCaptor.getValue();
//         assertThat(capturedAppointment.getDate()).isEqualTo(LocalDate.of(2024, 9, 19));
//         assertThat(capturedAppointment.getStartTime()).isEqualTo(LocalTime.of(10, 0));
//         assertThat(capturedAppointment.getEndTime()).isEqualTo(LocalTime.of(10, 30));
//         assertThat(capturedAppointment.getVetID()).isEqualTo(1);
//         assertThat(capturedAppointment.getPetID()).isEqualTo(1);
//         assertThat(capturedAppointment.getAppointmentTypeID()).isEqualTo(1);
//     }
// }