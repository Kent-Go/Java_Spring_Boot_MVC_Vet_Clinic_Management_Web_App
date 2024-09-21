package au.edu.rmit.sept.webapp.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
@AutoConfigureMockMvc
class RegistrationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    // Test for retrieving the registration page
    @Test
    void testShowRegistrationPage() throws Exception {
        mockMvc.perform(get("/userRegister"))
                .andExpect(status().isOk()) // Expect HTTP 200 OK status
                .andExpect(view().name("register")) // Expect the view name to be "register"
                .andExpect(model().attribute("selected", "")); // Expect model attribute "selected" to be an empty string
    }
}
