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
class TrendsHomeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    // Test for retrieving the trends homepage
    @Test
    void testShowTrendsHomePage() throws Exception {
        // Perform GET request to "/trendsHome"
        mockMvc.perform(get("/trendsHome"))
                .andExpect(status().isOk()) // Expect HTTP 200 status code
                .andExpect(view().name("trendsHome")); // Expect the view name to be "trendsHome"
    }

}
