package au.edu.rmit.sept.webapp.models;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

class PetOwnerTest {
    private PetOwner petOwner;

    @BeforeEach
    void setUp() {
        petOwner = new PetOwner();
    }

    @AfterEach
    void tearDown() {
        petOwner = null;
    }

    // Test getters and setters
    @Test
    void testPetOwnerSetAndGetId() {
        petOwner.setId(1);
        assertEquals(1, petOwner.getId());
    }

    @Test
    void testPetOwnerSetAndGetUserID() {
        petOwner.setUserID(1);
        assertEquals(1, petOwner.getUserID());
    }

    @Test
    void testPetOwnerSetAndGetUser() {
        User user = new User();
        petOwner.setUser(user);
        assertEquals(user, petOwner.getUser());
    }

    // Test constructors
    @Test
    void testPetOwnerDefaultConstructor() {
        petOwner = new PetOwner();
        assertEquals(0, petOwner.getId());
        assertEquals(0, petOwner.getUserID());
        assertEquals(null, petOwner.getUser());
    }

    @Test
    void testPetOwnerConstructor() {
        petOwner = new PetOwner(1, 1);
        assertEquals(1, petOwner.getId());
        assertEquals(1, petOwner.getUserID());
    }

    // Test pet owner - user association
    @Test
    void testPetOwnerUserAssociation() {
        User user = new User("Ben", "Parker", LocalDate.parse("1990-01-01"), "Male", "123456789",
                "parker.ben@example.com", "password");
        petOwner = new PetOwner(1, 1);
        petOwner.setUser(user);

        assertAll("petOwnerUser association",
                () -> assertEquals(1, petOwner.getId(), "Pet owner ID should be 1"),
                () -> assertEquals(1, petOwner.getUserID(), "User ID should be 1"),
                () -> assertEquals(user, petOwner.getUser(), "User should be associated with pet owner"),
                () -> assertEquals("Ben", petOwner.getUser().getFirstName(), "First name should be Ben"),
                () -> assertEquals("Parker", petOwner.getUser().getLastName(), "Last name should be Parker"),
                () -> assertEquals(LocalDate.parse("1990-01-01"), petOwner.getUser().getBirthDate(),
                        "Birth date should be 1990-01-01"),
                () -> assertEquals("Male", petOwner.getUser().getGender(), "Gender should be male"),
                () -> assertEquals("123456789", petOwner.getUser().getPhoneNumber(),
                        "Phone number should be 123456789"),
                () -> assertEquals("parker.ben@example.com", petOwner.getUser().getEmail(),
                        "Email should be parker.ben@example.com"),
                () -> assertEquals("password", petOwner.getUser().getPassword(), "Password should be password"));
    }
}
