package au.edu.rmit.sept.webapp.models;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {
    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
    }

    @AfterEach
    void tearDown() {
        user = null;
    }

    // Test getters and setters
    @Test
    void testUserSetAndGetId() {
        user.setId(1);
        assertEquals(1, user.getId());
    }

    @Test
    void testUserSetAndGetFirstName() {
        user.setFirstName("Test");
        assertEquals("Test", user.getFirstName());
    }

    @Test
    void testUserSetAndGetLastName() {
        user.setLastName("Test");
        assertEquals("Test", user.getLastName());
    }

    @Test
    void testUserSetAndGetBirthDate() {
        LocalDate expectedDate = LocalDate.parse("2021-10-10");
        user.setBirthDate(expectedDate);
        assertEquals(expectedDate, user.getBirthDate());
    }

    @Test
    void testUserSetAndGetGender() {
        user.setGender("Male");
        assertEquals("Male", user.getGender());
    }

    @Test
    void testUserSetAndGetPhoneNumber() {
        user.setPhoneNumber("1234567890");
        assertEquals("1234567890", user.getPhoneNumber());
    }

    @Test
    void testUserSetAndGetEmail() {
        user.setEmail("j_bellingham@gmail.com");
        assertEquals("j_bellingham@gmail.com", user.getEmail());
    }

    @Test
    void testUserSetAndGetPassword() {
        user.setPassword("password");
        assertEquals("password", user.getPassword());
    }

    // Test constructors
    @Test
    void testUserNoArgumentConstructor() {
        assertNotNull(user);
    }

    @Test
    void testUserConstructor() {
        User newUser = new User("Ben", "Parker", LocalDate.parse("1990-01-01"), "Male", "123456789",
        "parker.ben@example.com", "password");
        assertAll(
                "newUser",
                () -> assertEquals("Ben", newUser.getFirstName(), "First name should be Ben"),
                () -> assertEquals("Parker", newUser.getLastName(), "Last name should be Parker"),
                () -> assertEquals(LocalDate.parse("1990-01-01"), newUser.getBirthDate(),
                        "Birth date should be 1990-01-01"),
                () -> assertEquals("Male", newUser.getGender(), "Gender should be Male"),
                () -> assertEquals("123456789", newUser.getPhoneNumber(), "Phone number should be 123456789"),
                () -> assertEquals("parker.ben@example.com", newUser.getEmail(), "Email should be parker.ben@example.com"),
                () -> assertEquals("password", newUser.getPassword(), "Password should be password"));
    }
}