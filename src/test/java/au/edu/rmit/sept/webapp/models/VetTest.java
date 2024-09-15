package au.edu.rmit.sept.webapp.models;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

class VetTest {
    private Vet vet;

    @BeforeEach
    void setUp() {
        vet = new Vet();
    }

    @AfterEach
    void tearDown() {
        vet = null;
    }

    // Test getters and setters
    @Test
    void testVetGetId() {
        vet.setId(1);
        assertEquals(1, vet.getId());
    }

    @Test
    void testVetGetTitle() {
        vet.setTitle("Dr.");
        assertEquals("Dr.", vet.getTitle());
    }

    @Test
    void testVetGetLanguagesSpoken() {
        vet.setLanguagesSpoken("Test");
        assertEquals("Test", vet.getLanguagesSpoken());
    }

    @Test
    void testVetGetSelfDescription() {
        vet.setSelfDescription("Test");
        assertEquals("Test", vet.getSelfDescription());
    }

    @Test
    void testVetGetProfilePicture() {
        byte[] profilePicture = new byte[1];
        vet.setProfilePicture(profilePicture);
        assertArrayEquals(profilePicture, vet.getProfilePicture());
    }

    @Test
    void testVetGetUserID() {
        vet.setUserID(1);
        assertEquals(1, vet.getUserID());
    }

    @Test
    void testVetGetUser() {
        User user = new User();
        vet.setUser(user);
        assertEquals(user, vet.getUser());
    }

    // Test constructors
    @Test
    void testVetNoArgumentConstructor() {
        assertNotNull(vet);
    }

    @Test
    void testVetConstructor() {
        vet = new Vet("Dr.", "English, Spanish", "I have no description of myself", 1);

        assertAll(
                "vetDetails",
                () -> assertEquals(1, vet.getId(), 1),
                () -> assertEquals("Dr.", vet.getTitle(), "Title should be Dr."),
                () -> assertEquals("English, Spanish", vet.getLanguagesSpoken(), "Languages spoken should be English, Spanish"),
                () -> assertEquals("I have no description of myself", vet.getSelfDescription(),
                        "Description should be 'I have no description of myself'"),
                () -> assertEquals(1, vet.getUserID(), 1));
    }

    // Test vet - user association
    @Test
    void testVetUserAssociation() {
        User user = new User("Ben", "Parker", LocalDate.parse("1990-01-01"), "Male", "123456789",
                "parker.ben@example.com", "password");

        vet = new Vet("Dr.", "English, Spanish", "I have no description of myself", 1);
        vet.setUser(user);

        assertAll(
                "vetUserAssociation",
                () -> assertNotNull(vet.getUser()),
                () -> assertEquals(user, vet.getUser(), "User objects are not equal"),
                () -> assertEquals("Ben", vet.getUser().getFirstName(), "First name is should be Ben"),
                () -> assertEquals("Parker", vet.getUser().getLastName(), "Last name should be Parker"),
                () -> assertEquals(LocalDate.parse("1990-01-01"), vet.getUser().getBirthDate(), "Birth date should be 1990-01-01"),
                () -> assertEquals("Male", vet.getUser().getGender(), "Gender should be Male"),
                () -> assertEquals("123456789", vet.getUser().getPhoneNumber(), "Phone number should be 123456789"),
                () -> assertEquals("parker.ben@example.com", vet.getUser().getEmail(), "Email should be parker.ben@example.com"),
                () -> assertEquals("password", vet.getUser().getPassword(), "Password should be password"));
    }
}