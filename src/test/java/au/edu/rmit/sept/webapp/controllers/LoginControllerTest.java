package au.edu.rmit.sept.webapp.controllers;

import au.edu.rmit.sept.webapp.models.Vet;
import au.edu.rmit.sept.webapp.models.User;
import au.edu.rmit.sept.webapp.models.PetOwner;
import au.edu.rmit.sept.webapp.services.VetService;
import au.edu.rmit.sept.webapp.services.UserService;
import au.edu.rmit.sept.webapp.services.PetOwnerService;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import static org.mockito.Mockito.when;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
class LoginControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private VetService vetService;

    @MockBean
    private PetOwnerService petOwnerService;

    // Test rendering the login page
    @Test
    void testShowLoginPage() throws Exception {
        // Perform a GET request to /login and expect the login view to be returned
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk()) // Check that the status is 200 OK
                .andExpect(view().name("login")); // Check that the view is named "login"
    }

    // Test processing a login request with invalid email
    @Test
    void testProcessLoginWithInvalidEmail() throws Exception {
        // Mock the behavior of UserService to return null when queried by email
        when(userService.getUserByEmail(anyString())).thenReturn(null);
        // Perform the POST request to /login with invalid email
        mockMvc.perform(post("/login")
                .param("email", "email@email.com")
                .param("password", "password123"))
                .andExpect(status().isOk()) // Expect the status to be OK (login failed)
                .andExpect(view().name("login")) // Expect the view to be the login page
                .andExpect(model().attributeExists("error")); // Expect an error message in the model
    }

    // Test processing a login request with an email that has no @ symbol
    @Test
    void testProcessLoginWithInvalidEmailNoAtSymbol() throws Exception {
        // Perform the POST request to /login with an email that has no @ symbol
        mockMvc.perform(post("/login")
                .param("email", "email.com")
                .param("password", "password123"))
                .andExpect(status().isOk()) // Expect the status to be OK (login failed)
                .andExpect(view().name("login")) // Expect the view to be the login page
                .andExpect(model().attributeExists("error")); // Expect an error message in the model
    }

    // Test processing a login request with an email that has no domain
    @Test
    void testProcessLoginWithInvalidEmailNoDomain() throws Exception {
        // Perform the POST request to /login with an email that has no domain
        mockMvc.perform(post("/login")
                .param("email", "email@")
                .param("password", "password123"))
                .andExpect(status().isOk()) // Expect the status to be OK (login failed)
                .andExpect(view().name("login")) // Expect the view to be the login page
                .andExpect(model().attributeExists("error")); // Expect an error message in the model
    }

    // Test processing a login request with an email that has no username
    @Test
    void testProcessLoginWithInvalidEmailNoUsername() throws Exception {
        // Perform the POST request to /login with an email that has no username
        mockMvc.perform(post("/login")
                .param("email", "@example.com")
                .param("password", "password123"))
                .andExpect(status().isOk()) // Expect the status to be OK (login failed)
                .andExpect(view().name("login")) // Expect the view to be the login page
                .andExpect(model().attributeExists("error")); // Expect an error message in the model
    }

    // Test processing a login request with an empty email
    @Test
    void testProcessLoginWithEmptyEmail() throws Exception {
        // Perform the POST request to /login with an empty email
        mockMvc.perform(post("/login")
                .param("email", "")
                .param("password", "password123"))
                .andExpect(status().isOk()) // Expect the status to be OK (login failed)
                .andExpect(view().name("login")) // Expect the view to be the login page
                .andExpect(model().attributeExists("error")); // Expect an error message in the model
    }

    // Test processing a login request with an email that has more than 1 special character
    @Test
    void testProcessLoginWithInvalidEmailMultipleSpecialCharacters() throws Exception {
        // Perform the POST request to /login with an email that has more than 1 special
        // character
        mockMvc.perform(post("/login")
                .param("email", "email@com@")
                .param("password", "password123"))
                .andExpect(status().isOk()) // Expect the status to be OK (login failed)
                .andExpect(view().name("login")) // Expect the view to be the login page
                .andExpect(model().attributeExists("error")); // Expect an error message in the model
    }

    // Test processing a login request with invalid password
    @Test
    void testProcessLoginWithInvalidCredentials() throws Exception {
        // Create a mock user with invalid credentials
        User user = new User();
        user.setEmail("thisIsAnExample@example.com");
        user.setPassword("password123");
        // Mock the behavior of UserService to return this user when queried by email
        when(userService.getUserByEmail("thisIsAnExample@example.com")).thenReturn(user);
        // Perform the POST request to /login with invalid credentials
        mockMvc.perform(post("/login")
                .param("email", "thisIsAnExample@example.com")
                .param("password", "wrongPassword"))
                .andExpect(status().isOk()) // Expect the status to be OK (login failed)
                .andExpect(view().name("login")) // Expect the view to be the login page
                .andExpect(model().attributeExists("error")); // Expect an error message in the model
    }

    // Test processing a login request with empty password
    @Test
    void testProcessLoginWithEmptyPassword() throws Exception {
        // Perform the POST request to /login with an empty password
        mockMvc.perform(post("/login")
                .param("email", "fake@email.com")
                .param("password", ""))
                .andExpect(status().isOk()) // Expect the status to be OK (login failed)
                .andExpect(view().name("login")) // Expect the view to be the login page
                .andExpect(model().attributeExists("error")); // Expect an error message in the model
    }

    // Test processing a login request with empty email and password
    @Test
    void testProcessLoginWithEmptyEmailAndPassword() throws Exception {
        // Perform the POST request to /login with an empty email and password
        mockMvc.perform(post("/login")
                .param("email", "")
                .param("password", ""))
                .andExpect(status().isOk()) // Expect the status to be OK (login failed)
                .andExpect(view().name("login")) // Expect the view to be the login page
                .andExpect(model().attributeExists("error")); // Expect an error message in the model
    }

    // Test processing a login request with valid credentials for a pet owner
    @Test
    void testProcessLoginWithValidCredentialsForPetOwner() throws Exception {
        // Create a mock user with valid credentials
        User user = new User();
        user.setId(1);
        user.setEmail("littlejohn@gmail.com");
        user.setPassword("galvanisedSquareSteel(hashed)");

        // Create a mock PetOwner
        PetOwner petOwner = new PetOwner();
        petOwner.setId(1);
        petOwner.setUserID(user.getId());

        // Mock the behavior of UserService to return this user when queried by email
        when(userService.getUserByEmail("littlejohn@gmail.com")).thenReturn(user);

        // Mock VetService to return null (indicating the user is not a vet)
        when(vetService.getVetByUserID(user.getId())).thenReturn(null);

        // Mock PetOwnerService to return the mock PetOwner
        when(petOwnerService.getPetOwnerByUserID(user.getId())).thenReturn(petOwner);

        // Perform the POST request to /login with valid credentials
        mockMvc.perform(post("/login")
                .param("email", "littlejohn@gmail.com")
                .param("password", "galvanisedSquareSteel(hashed)"))
                .andExpect(status().is3xxRedirection()) // Expect a redirect status (successful login)
                .andExpect(redirectedUrl("/petOwnerWelcome?userId=" + user.getId() + "&petOwnerId=" + petOwner.getId())); // Verify the redirection URL
    }

    // Test processing a login request with valid credentials for a vet
    @Test
    void testProcessLoginWithValidCredentialsForVet() throws Exception {
        // Create a mock user with valid credentials
        User user = new User();
        user.setId(1);
        user.setEmail("vet@test.com");
        user.setPassword("password123");
        // Create a mock vet
        Vet vet = new Vet();
        vet.setId(1);
        vet.setUserID(user.getId());
        // Mock the behavior of UserService to return this user when queried by email
        when(userService.getUserByEmail("vet@test.com")).thenReturn(user);
        // Mock the behavior of VetService to return this vet when queried by user ID
        when(vetService.getVetByUserID(user.getId())).thenReturn(vet);
        // Perform the POST request to /login with valid credentials
        mockMvc.perform(post("/login")
                .param("email", "vet@test.com")
                .param("password", "password123"))
                .andExpect(status().is3xxRedirection()) // Expect a redirect status (successful login)
                .andExpect(redirectedUrl("/vetDashboard?userId=" + user.getId() +  "&vetId=" + vet.getId())); // Verify the redirection URL
    }
    // Test rendering the register page
    @Test
    void testShowRegisterPage() throws Exception {
        // Perform a GET request to /register and expect the register view to be
        // returned
        mockMvc.perform(get("/register"))
                .andExpect(status().isOk()) // Check that the status is 200 OK
                .andExpect(view().name("register")); // Check that the view is named "register"
    }
}