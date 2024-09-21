package au.edu.rmit.sept.webapp.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;
import java.util.List;
import java.util.Arrays;
import java.util.Collection;

import au.edu.rmit.sept.webapp.models.PetOwner;
import au.edu.rmit.sept.webapp.repositories.PetOwnerRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class PetOwnerServiceImplTest {

    @Autowired
    private PetOwnerServiceImpl petOwnerService;

    @MockBean
    private PetOwnerRepository petOwnerRepository;

    private PetOwner petOwner1;
    private PetOwner petOwner2;

    @BeforeEach
    public void setUp() {
        // Create mock PetOwners
        petOwner1 = new PetOwner(1, 101);
        petOwner2 = new PetOwner(2, 102);
    }

    @AfterEach
    public void tearDown() {
        petOwner1 = null;
        petOwner2 = null;
    }

    // Positive Test: Test retrieving all pet owners
    @Test
    public void testGetAllPetOwners() {
        List<PetOwner> petOwners = Arrays.asList(petOwner1, petOwner2);

        // Mock the repository to return the list of pet owners
        when(petOwnerRepository.findAll()).thenReturn(petOwners);

        Collection<PetOwner> result = petOwnerService.getAllPetOwners();

        // Assert that the result is not null and contains both pet owners
        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.contains(petOwner1));
        assertTrue(result.contains(petOwner2));
    }

    // Positive Test: Test getting a pet owner by PetOwner ID
    @Test
    public void testGetPetOwnerByPetOwnerID_ValidID() {
        // Mock the repository to return petOwner1
        when(petOwnerRepository.findById(1)).thenReturn(Optional.of(petOwner1));

        PetOwner result = petOwnerService.getPetOwnerByPetOwnerID(1);

        // Assert that the returned pet owner is correct
        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals(101, result.getUserID());
    }

    // Negative Test: Test getting a pet owner with an invalid PetOwner ID
    @Test
    public void testGetPetOwnerByPetOwnerID_InvalidID() {
        // Mock the repository to return empty when an invalid ID is provided
        when(petOwnerRepository.findById(999)).thenReturn(Optional.empty());

        // Expect a RuntimeException due to invalid ID
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            petOwnerService.getPetOwnerByPetOwnerID(999);
        });

        // Assert that the exception message matches
        assertEquals("Pet Owner not found", exception.getMessage());
    }

    // Boundary Test: Test retrieving a pet owner with the minimum possible PetOwner ID
    @Test
    public void testGetPetOwnerByPetOwnerID_Boundary() {
        // Mock the repository to return petOwner1
        when(petOwnerRepository.findById(1)).thenReturn(Optional.of(petOwner1));

        PetOwner result = petOwnerService.getPetOwnerByPetOwnerID(1);

        // Assert that the returned pet owner is correct
        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals(101, result.getUserID());
    }

    // Positive Test: Test getting a pet owner by User ID
    @Test
    public void testGetPetOwnerByUserID_ValidUserID() {
        // Mock the repository to return petOwner2 for userID 102
        when(petOwnerRepository.findByUserID(102)).thenReturn(Optional.of(petOwner2));

        PetOwner result = petOwnerService.getPetOwnerByUserID(102);

        // Assert that the returned pet owner is correct
        assertNotNull(result);
        assertEquals(2, result.getId());
        assertEquals(102, result.getUserID());
    }

    // Negative Test: Test getting a pet owner with an invalid User ID
    @Test
    public void testGetPetOwnerByUserID_InvalidUserID() {
        // Mock the repository to return empty when an invalid User ID is provided
        when(petOwnerRepository.findByUserID(999)).thenReturn(Optional.empty());

        // Expect a RuntimeException due to invalid User ID
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            petOwnerService.getPetOwnerByUserID(999);
        });

        // Assert that the exception message matches
        assertEquals("Pet Owner not found", exception.getMessage());
    }

    // Boundary Test: Test getting a pet owner by the minimum possible User ID
    @Test
    public void testGetPetOwnerByUserID_Boundary() {
        // Mock the repository to return petOwner1 for userID 101
        when(petOwnerRepository.findByUserID(101)).thenReturn(Optional.of(petOwner1));

        PetOwner result = petOwnerService.getPetOwnerByUserID(101);

        // Assert that the returned pet owner is correct
        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals(101, result.getUserID());
    }

    // Positive Test: Test creating a new pet owner
    @Test
    public void testCreatePetOwner_ValidPetOwner() {
        // Mock the repository to save the pet owner
        when(petOwnerRepository.save(petOwner1)).thenReturn(petOwner1);

        PetOwner result = petOwnerService.createPetOwner(petOwner1);

        // Assert that the returned pet owner is the saved one
        assertNotNull(result);
        assertEquals(petOwner1.getId(), result.getId());
    }

    // Negative Test: Test creating a pet owner with missing data (e.g., null userID)
    @Test
    public void testCreatePetOwner_NullUserID() {
        PetOwner petOwner = new PetOwner();
        petOwner.setUserID(0); // Invalid user ID

        // Expecting IllegalArgumentException to be thrown
        assertThrows(IllegalArgumentException.class, () -> {
            petOwnerService.createPetOwner(petOwner);
        }, "Expected IllegalArgumentException for null or zero user ID");
    }

}
