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
class ArticlesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    // Test for retrieving the articles page
    @Test
    void testShowArticles() throws Exception {
        // Perform GET request to "/articlesPage"
        mockMvc.perform(get("/articlesPage"))
                .andExpect(status().isOk()) // Expect HTTP 200 status code
                .andExpect(view().name("articlesPage")); // Expect the view name to be "articlesPage"
    }

    // Test for retrieving the article 1 page
    @Test
    void testShowArticle1() throws Exception {
        // Perform GET request to "/article1"
        mockMvc.perform(get("/article1"))
                .andExpect(status().isOk()) // Expect HTTP 200 status code
                .andExpect(view().name("article1")); // Expect the view name to be "article1"
    }

    // Test for retrieving the article 2 page
    @Test
    void testShowArticle2() throws Exception {
        // Perform GET request to "/article2"
        mockMvc.perform(get("/article2"))
                .andExpect(status().isOk()) // Expect HTTP 200 status code
                .andExpect(view().name("article2")); // Expect the view name to be "article2"
    }

    // Test for retrieving the article 3 page
    @Test
    void testShowArticle3() throws Exception {
        // Perform GET request to "/article3"
        mockMvc.perform(get("/article3"))
                .andExpect(status().isOk()) // Expect HTTP 200 status code
                .andExpect(view().name("article3")); // Expect the view name to be "article3"
    }
}
