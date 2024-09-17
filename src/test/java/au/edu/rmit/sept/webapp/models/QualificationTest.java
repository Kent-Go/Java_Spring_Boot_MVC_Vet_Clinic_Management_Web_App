package au.edu.rmit.sept.webapp.models;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

class QualificationTest {
    private Qualification qualification;

    @BeforeEach
    void setUp() {
        qualification = new Qualification();
    }

    @AfterEach
    void tearDown() {
        qualification = null;
    }

    // Test getters and setters
    @Test
    void testQualificationSetAndGetId() {
        qualification.setId(1);
        assertEquals(1, qualification.getId());
    }

    @Test
    void testQualificationSetAndGetName() {
        qualification.setName("Bachelor of Veterinarian Science");
        assertEquals("Bachelor of Veterinarian Science", qualification.getName());
    }

    @Test
    void testQualificationSetAndGetUniversity() {
        qualification.setUniversity("University of Melbourne");
        assertEquals("University of Melbourne", qualification.getUniversity());
    }

    @Test
    void testQualificationSetAndGetCountry() {
        qualification.setCountry("Australia");
        assertEquals("Australia", qualification.getCountry());
    }

    @Test
    void testQualificationSetAndGetYear() {
        qualification.setYear(2019);
        assertEquals(2019, qualification.getYear());
    }

    @Test
    void testQualificationSetAndGetVetID() {
        qualification.setVetID(1);
        assertEquals(1, qualification.getVetID());
    }

    @Test
    void testQualificationSetAndGetVet() {
        Vet vet = new Vet("Dr.", "English, Spanish", "I have no description of myself", 1);
        qualification.setVet(vet);
        assertEquals(vet, qualification.getVet());
    }

    // Test constructor
    
    @Test
    void testQualificationDefaultConstructor() {
        qualification = new Qualification();
        assertAll("qualification",
                () -> assertEquals(null, qualification.getName(), "Name should be null"),
                () -> assertEquals(null, qualification.getUniversity(), "University should be null"),
                () -> assertEquals(null, qualification.getCountry(), "Country should be null"),
                () -> assertEquals(0, qualification.getYear(), "Year should be 0"),
                () -> assertEquals(0, qualification.getVetID(), "Vet ID should be 0"),
                () -> assertNotNull(qualification));
    }

    @Test
    void testQualificationConstructor() {
        qualification = new Qualification("Bachelor of Veterinarian Science", "University of Melbourne", "Australia",
                2019, 1);
        assertAll("qualification",
                () -> assertEquals("Bachelor of Veterinarian Science", qualification.getName(),
                        "Name should be 'Bachelor of Veterinarian Science'"),
                () -> assertEquals("University of Melbourne", qualification.getUniversity(),
                        "University should be 'University of Melbourne'"),
                () -> assertEquals("Australia", qualification.getCountry(), "Country should be 'Australia'"),
                () -> assertEquals(2019, qualification.getYear(), "Year should be 2019"),
                () -> assertEquals(1, qualification.getVetID(), "Vet ID should be 1"));
    }

    // Test qualification - vet association
    @Test
    void testQualificationVetAssociation() {
        Vet vet = new Vet("Dr.", "English, Spanish", "I have no description of myself", 1);
        qualification.setVet(vet);
        assertAll("qualificationVetAssociation",
                () -> assertEquals(vet, qualification.getVet(), "Vet should be the same"),
                () -> assertEquals(vet.getId(), qualification.getVetID(), "Vet ID should be the same"),
                () -> assertEquals("Dr.", qualification.getVet().getTitle(), "Title should be 'Dr.'"),
                () -> assertEquals("English, Spanish", qualification.getVet().getLanguagesSpoken(),
                        "Languages spoken should be 'English, Spanish'"),
                () -> assertEquals("I have no description of myself", qualification.getVet().getSelfDescription(),
                        "Description should be 'I have no description of myself'"),
                () -> assertEquals(1, qualification.getVet().getUserID(), "User ID should be 1"));
    }

    // Test qualification - vet - user association
    @Test
    void testQualificationVetUserAssociation() {
        User user = new User("Ben", "Parker", LocalDate.parse("1990-01-01"), "Male", "123456789",
                "parker.ben@example.com", "password");
        user.setId(1);
        Vet vet = new Vet("Dr.", "English, Spanish", "I have no description of myself", 1);
        vet.setUser(user);
        qualification.setVet(vet);
        assertAll("qualificationVetUserAssociation",
                () -> assertEquals(user, qualification.getVet().getUser(), "User should be the same"),
                () -> assertEquals(user.getId(), qualification.getVet().getUserID(), "User ID should be the same"),
                () -> assertEquals("Ben", qualification.getVet().getUser().getFirstName(),
                        "First name should be 'Ben'"),
                () -> assertEquals("Parker", qualification.getVet().getUser().getLastName(),
                        "Last name should be 'Parker'"),
                () -> assertEquals(LocalDate.parse("1990-01-01"), qualification.getVet().getUser().getBirthDate(),
                        "Date of birth should be 1990-01-01"),
                () -> assertEquals("Male", qualification.getVet().getUser().getGender(), "Gender should be Male"),
                () -> assertEquals("123456789", qualification.getVet().getUser().getPhoneNumber(),
                        "Phone number should be 123456789"),
                () -> assertEquals("parker.ben@example.com", qualification.getVet().getUser().getEmail(),
                        "Email should be 'parker.ben@example.com'"),
                () -> assertEquals("password", qualification.getVet().getUser().getPassword(),
                        "Password should be 'password'"),
                () -> assertEquals(vet, qualification.getVet(), "Vet should be the same"),
                () -> assertEquals("Dr.", qualification.getVet().getTitle(), "Title should be 'Dr.'"),
                () -> assertEquals("English, Spanish", qualification.getVet().getLanguagesSpoken(),
                        "Languages spoken should be 'English, Spanish'"),
                () -> assertEquals("I have no description of myself", qualification.getVet().getSelfDescription(),
                        "Description should be 'I have no description of myself'"),
                () -> assertEquals(1, qualification.getVet().getUserID(), "User ID should be 1"));
    }
}
