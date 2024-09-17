package au.edu.rmit.sept.webapp.models;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

class AddressTest {
    private Address address;

    @BeforeEach
    void setUp() {
        address = new Address();
    }

    @AfterEach
    void tearDown() {
        address = null;
    }

    // Test getters and setters
    @Test
    void testAddressSetAndGetId() {
        address.setId(1);
        assertEquals(1, address.getId());
    }

    @Test
    void testAddressSetAndGetStreet() {
        address.setStreet("105 Brunswick St");
        assertEquals("105 Brunswick St", address.getStreet());
    }

    @Test
    void testAddressSetAndGetSuburb() {
        address.setSuburb("Altona North");
        assertEquals("Altona North", address.getSuburb());
    }

    @Test
    void testAddressSetAndGetState() {
        address.setState("New South Wales");
        assertEquals("New South Wales", address.getState());
    }

    @Test
    void testAddressSetAndGetPostcode() {
        address.setPostcode("3025");
        assertEquals("3025", address.getPostcode());
    }

    @Test
    void testAddressSetAndGetUserID() {
        address.setUserID(1);
        assertEquals(1, address.getUserID());
    }

    @Test
    void testAddressSetAndGetUser() {
        User user = new User();
        address.setUser(user);
        assertEquals(user, address.getUser());
    }

    // Test constructors
    @Test
    void testAddressDefaultConstructor() {
        address = new Address();
        assertAll("Default address constructor",
                () -> assertEquals(null, address.getStreet(), "Street should be null"),
                () -> assertEquals(null, address.getSuburb(), "Suburb should be null"),
                () -> assertEquals(null, address.getState(), "State should be null"),
                () -> assertEquals(null, address.getPostcode(), "Postcode should be null"),
                () -> assertEquals(0, address.getUserID(), "User ID should be 0"),
                () -> assertEquals(null, address.getUser(), "User should be null"));
    }

    @Test
    void testAddressConstructor() {
        address = new Address("105 Brunswick St", "Altona North", "New South Wales", "3025", 1);
        assertAll("Address constructor",
                () -> assertEquals("105 Brunswick St", address.getStreet(), "Street should be 105 Brunswick St"),
                () -> assertEquals("Altona North", address.getSuburb(), "Suburb should be Altona North"),
                () -> assertEquals("New South Wales", address.getState(), "State should be New South Wales"),
                () -> assertEquals("3025", address.getPostcode(), "Postcode should be 3025"),
                () -> assertEquals(1, address.getUserID(), "User ID should be 1"));
    }

    // Test address - user association
    @Test
    void testAddressUserAssociation() {
        User user = new User("Ben", "Parker", LocalDate.parse("1990-01-01"), "Male", "123456789",
                "parker.ben@example.com", "password");

        address = new Address("105 Brunswick St", "Altona North", "New South Wales", "3025", 1);
        address.setUser(user);

        assertAll("Address user association",
                () -> assertEquals("105 Brunswick St", address.getStreet(), "Street should be 105 Brunswick St"),
                () -> assertEquals("Altona North", address.getSuburb(), "Suburb should be Altona North"),
                () -> assertEquals("New South Wales", address.getState(), "State should be New South Wales"),
                () -> assertEquals("3025", address.getPostcode(), "Postcode should be 3025"),
                () -> assertEquals(1, address.getUserID(), "User ID should be 1"),
                () -> assertNotNull(address.getUser(), "User should not be null"));
    }
}
