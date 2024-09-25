// package au.edu.rmit.sept.webapp.controllers;

// import au.edu.rmit.sept.webapp.models.Vet;
// import au.edu.rmit.sept.webapp.models.VetAppointmentTypeOffered;
// import au.edu.rmit.sept.webapp.models.User;
// import au.edu.rmit.sept.webapp.services.VetAppointmentTypeOfferedService;
// import au.edu.rmit.sept.webapp.services.VetService;
// import au.edu.rmit.sept.webapp.services.UserService;

// import org.junit.jupiter.api.Test;
// import org.springframework.boot.test.mock.mockito.MockBean;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

// import static org.mockito.Mockito.when;
// import org.springframework.test.web.servlet.MockMvc;
// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
// import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

// import java.time.LocalDate;
// import java.util.Arrays;
// import java.util.Collection;

// @WebMvcTest(ScheduleAppointmentSelectVeterinarianController.class)
// class ScheduleAppointmentSelectVeterinarianControllerTest {

//     @Autowired
//     private MockMvc mockMvc;

//     @MockBean
//     private VetAppointmentTypeOfferedService vetAppointmentTypeOfferedService;

//     @MockBean
//     private VetService vetService;

//     @MockBean
//     private UserService userService;

//     @Test
//     void testShowVetForAppointmentTypeID1() throws Exception {
//         // Mock the behavior of VetAppointmentTypeOfferedService to return a list of vet appointment types offered
//         Collection<VetAppointmentTypeOffered> vetAppointmentTypeOffereds = Arrays.asList(
//             new VetAppointmentTypeOffered(1, 1),
//             new VetAppointmentTypeOffered(2, 1)
//         );
//         when(vetAppointmentTypeOfferedService.getVetAppointmentTypeOfferedByAppointmentTypeID(1)).thenReturn(vetAppointmentTypeOffereds);

//         // Mock the behavior of VetService to return a vet
//         Vet vet1 = new Vet("Dr.", "English, Russian, Italian", "Basic description for a vet... uh... I'm very good at taking care of dogs and cats??", 2, 1);
//         Vet vet2 = new Vet("Surgeon", "English, Spanish", "I have very steady hands and is skillful with knifes on the operating room", 4, 2);
//         when(vetService.getVetByVetID(1)).thenReturn(vet1);
//         when(vetService.getVetByVetID(2)).thenReturn(vet2);

//         // Mock the behavior of UserService to return a user
//         User user1 = new User("Jude", "Bellingham", LocalDate.of(1980, 12, 10), "Male", "0782937410", "j_bellingham@gmail.com", "unknownHollowFeathers(hashed)");
//         User user2 = new User("Sarah", "Thompson", LocalDate.of(1979, 4, 20), "Female", "0295783902", "thompsonS@yahoo.com", "hashedpassword");
//         when(userService.getUserByUserID(2)).thenReturn(user1);
//         when(userService.getUserByUserID(4)).thenReturn(user2);

//         // Perform a GET request to /appointment/new/select_veterinarian with appointmentTypeId=1
//         mockMvc.perform(get("/appointment/new/select_veterinarian")
//                 .param("appointmentTypeId", "1"))
//                 .andExpect(status().isOk()) // Expect the status to be OK
//                 .andExpect(view().name("appointmentSelectVeterinarian")) // Expect the view to be appointmentSelectVeterinarian
//                 .andExpect(model().attributeExists("vetAppointmentTypeOffereds")) // Expect the model to have an attribute "vetAppointmentTypeOffereds"
//                 .andExpect(model().attribute("vetAppointmentTypeOffereds", vetAppointmentTypeOffereds)); // Expect the "vetAppointmentTypeOffereds" attribute to match the mocked data
//     }

//     @Test
//     void testShowVetForAppointmentTypeID2() throws Exception {
//         // Mock the behavior of VetAppointmentTypeOfferedService to return a list of vet appointment types offered
//         Collection<VetAppointmentTypeOffered> vetAppointmentTypeOffereds = Arrays.asList(
//             new VetAppointmentTypeOffered(1, 2)
//         );
//         when(vetAppointmentTypeOfferedService.getVetAppointmentTypeOfferedByAppointmentTypeID(2)).thenReturn(vetAppointmentTypeOffereds);

//         // Mock the behavior of VetService to return a vet
//         Vet vet1 = new Vet("Dr.", "English, Russian, Italian", "Basic description for a vet... uh... I'm very good at taking care of dogs and cats??", 2, 1);
//         when(vetService.getVetByVetID(1)).thenReturn(vet1);

//         // Mock the behavior of UserService to return a user
//         User user1 = new User("Jude", "Bellingham", LocalDate.of(1980, 12, 10), "Male", "0782937410", "j_bellingham@gmail.com", "unknownHollowFeathers(hashed)");
//         when(userService.getUserByUserID(2)).thenReturn(user1);

//         // Perform a GET request to /appointment/new/select_veterinarian with appointmentTypeId=2
//         mockMvc.perform(get("/appointment/new/select_veterinarian")
//                 .param("appointmentTypeId", "2"))
//                 .andExpect(status().isOk()) // Expect the status to be OK
//                 .andExpect(view().name("appointmentSelectVeterinarian")) // Expect the view to be appointmentSelectVeterinarian
//                 .andExpect(model().attributeExists("vetAppointmentTypeOffereds")) // Expect the model to have an attribute "vetAppointmentTypeOffereds"
//                 .andExpect(model().attribute("vetAppointmentTypeOffereds", vetAppointmentTypeOffereds)); // Expect the "vetAppointmentTypeOffereds" attribute to match the mocked data
//     }

//     @Test
//     void testShowVetForAppointmentTypeID3() throws Exception {
//         // Mock the behavior of VetAppointmentTypeOfferedService to return a list of vet appointment types offered
//         Collection<VetAppointmentTypeOffered> vetAppointmentTypeOffereds = Arrays.asList(
//             new VetAppointmentTypeOffered(1, 1),
//             new VetAppointmentTypeOffered(2, 1)
//         );
//         when(vetAppointmentTypeOfferedService.getVetAppointmentTypeOfferedByAppointmentTypeID(3)).thenReturn(vetAppointmentTypeOffereds);

//         // Mock the behavior of VetService to return a vet
//         Vet vet1 = new Vet("Dr.", "English, Russian, Italian", "Basic description for a vet... uh... I'm very good at taking care of dogs and cats??", 2, 1);
//         Vet vet2 = new Vet("Surgeon", "English, Spanish", "I have very steady hands and is skillful with knifes on the operating room", 4, 2);
//         when(vetService.getVetByVetID(1)).thenReturn(vet1);
//         when(vetService.getVetByVetID(2)).thenReturn(vet2);

//         // Mock the behavior of UserService to return a user
//         User user1 = new User("Jude", "Bellingham", LocalDate.of(1980, 12, 10), "Male", "0782937410", "j_bellingham@gmail.com", "unknownHollowFeathers(hashed)");
//         User user2 = new User("Sarah", "Thompson", LocalDate.of(1979, 4, 20), "Female", "0295783902", "thompsonS@yahoo.com", "hashedpassword");
//         when(userService.getUserByUserID(2)).thenReturn(user1);
//         when(userService.getUserByUserID(4)).thenReturn(user2);

//         // Perform a GET request to /appointment/new/select_veterinarian with appointmentTypeId=1
//         mockMvc.perform(get("/appointment/new/select_veterinarian")
//                 .param("appointmentTypeId", "3"))
//                 .andExpect(status().isOk()) // Expect the status to be OK
//                 .andExpect(view().name("appointmentSelectVeterinarian")) // Expect the view to be appointmentSelectVeterinarian
//                 .andExpect(model().attributeExists("vetAppointmentTypeOffereds")) // Expect the model to have an attribute "vetAppointmentTypeOffereds"
//                 .andExpect(model().attribute("vetAppointmentTypeOffereds", vetAppointmentTypeOffereds)); // Expect the "vetAppointmentTypeOffereds" attribute to match the mocked data
//     }

//     @Test
//     void testShowVetForAppointmentTypeID4() throws Exception {
//         // Mock the behavior of VetAppointmentTypeOfferedService to return a list of vet appointment types offered
//         Collection<VetAppointmentTypeOffered> vetAppointmentTypeOffereds = Arrays.asList(
//             new VetAppointmentTypeOffered(2, 1)
//         );
//         when(vetAppointmentTypeOfferedService.getVetAppointmentTypeOfferedByAppointmentTypeID(4)).thenReturn(vetAppointmentTypeOffereds);

//         // Mock the behavior of VetService to return a vet
//         Vet vet2 = new Vet("Surgeon", "English, Spanish", "I have very steady hands and is skillful with knifes on the operating room", 4, 1);
//         when(vetService.getVetByVetID(2)).thenReturn(vet2);

//         // Mock the behavior of UserService to return a user
//         User user2 = new User("Sarah", "Thompson", LocalDate.of(1979, 4, 20), "Female", "0295783902", "thompsonS@yahoo.com", "hashedpassword");
//         when(userService.getUserByUserID(4)).thenReturn(user2);

//         // Perform a GET request to /appointment/new/select_veterinarian with appointmentTypeId=1
//         mockMvc.perform(get("/appointment/new/select_veterinarian")
//                 .param("appointmentTypeId", "4"))
//                 .andExpect(status().isOk()) // Expect the status to be OK
//                 .andExpect(view().name("appointmentSelectVeterinarian")) // Expect the view to be appointmentSelectVeterinarian
//                 .andExpect(model().attributeExists("vetAppointmentTypeOffereds")) // Expect the model to have an attribute "vetAppointmentTypeOffereds"
//                 .andExpect(model().attribute("vetAppointmentTypeOffereds", vetAppointmentTypeOffereds)); // Expect the "vetAppointmentTypeOffereds" attribute to match the mocked data
//     }

//     @Test
//     void testShowVetForAppointmentTypeID5() throws Exception {
//         // Mock the behavior of VetAppointmentTypeOfferedService to return a list of vet appointment types offered
//         Collection<VetAppointmentTypeOffered> vetAppointmentTypeOffereds = Arrays.asList(
//             new VetAppointmentTypeOffered(1, 1),
//             new VetAppointmentTypeOffered(2, 1)
//         );
//         when(vetAppointmentTypeOfferedService.getVetAppointmentTypeOfferedByAppointmentTypeID(5)).thenReturn(vetAppointmentTypeOffereds);

//         // Mock the behavior of VetService to return a vet
//         Vet vet1 = new Vet("Dr.", "English, Russian, Italian", "Basic description for a vet... uh... I'm very good at taking care of dogs and cats??", 2, 1);
//         Vet vet2 = new Vet("Surgeon", "English, Spanish", "I have very steady hands and is skillful with knifes on the operating room", 4, 2);
//         when(vetService.getVetByVetID(1)).thenReturn(vet1);
//         when(vetService.getVetByVetID(2)).thenReturn(vet2);

//         // Mock the behavior of UserService to return a user
//         User user1 = new User("Jude", "Bellingham", LocalDate.of(1980, 12, 10), "Male", "0782937410", "j_bellingham@gmail.com", "unknownHollowFeathers(hashed)");
//         User user2 = new User("Sarah", "Thompson", LocalDate.of(1979, 4, 20), "Female", "0295783902", "thompsonS@yahoo.com", "hashedpassword");
//         when(userService.getUserByUserID(2)).thenReturn(user1);
//         when(userService.getUserByUserID(4)).thenReturn(user2);

//         // Perform a GET request to /appointment/new/select_veterinarian with appointmentTypeId=1
//         mockMvc.perform(get("/appointment/new/select_veterinarian")
//                 .param("appointmentTypeId", "5"))
//                 .andExpect(status().isOk()) // Expect the status to be OK
//                 .andExpect(view().name("appointmentSelectVeterinarian")) // Expect the view to be appointmentSelectVeterinarian
//                 .andExpect(model().attributeExists("vetAppointmentTypeOffereds")) // Expect the model to have an attribute "vetAppointmentTypeOffereds"
//                 .andExpect(model().attribute("vetAppointmentTypeOffereds", vetAppointmentTypeOffereds)); // Expect the "vetAppointmentTypeOffereds" attribute to match the mocked data
//     }
// }