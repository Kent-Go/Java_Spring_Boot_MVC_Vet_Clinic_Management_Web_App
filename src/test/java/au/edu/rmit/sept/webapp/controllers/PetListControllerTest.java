package au.edu.rmit.sept.webapp.controllers;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

import au.edu.rmit.sept.webapp.models.Pet;
import au.edu.rmit.sept.webapp.models.User;
import au.edu.rmit.sept.webapp.models.PetOwner;
import au.edu.rmit.sept.webapp.services.PetService;
import au.edu.rmit.sept.webapp.services.UserService;
import au.edu.rmit.sept.webapp.services.PetOwnerService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;

import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import static org.mockito.Mockito.mock;
import static org.mockito.ArgumentMatchers.eq;


@ExtendWith(MockitoExtension.class)
public class PetListControllerTest {

    @Mock
    private PetService petService;

    @Mock
    private UserService userService;

    @Mock
    private PetOwnerService petOwnerService;

    @Mock
    private Model model;

    @InjectMocks
    private PetListController petListController;

    private int validPetOwnerId = 1;
    private int invalidPetOwnerId = 999; // Negative test case

    private Pet pet1, pet2;
    private PetOwner petOwner;
    private User user;

    @BeforeEach
    public void setup() {
        pet1 = new Pet("Max", LocalDate.of(2018, 1, 1), "Dog", "Labrador", "Male", 25.0f, validPetOwnerId);
        pet2 = new Pet("Bella", LocalDate.of(2020, 6, 5), "Cat", "Persian", "Female", 4.5f, validPetOwnerId);

        petOwner = new PetOwner(validPetOwnerId, 1); // Valid petOwnerID and userID

        user = new User("John", "Doe", LocalDate.of(1980, 1, 1), "Male", "123456789", "john@example.com", "password");
    }

    // Positive Test: Display pets (valid petOwnerId)
    @Test
    public void testDisplayPets_ValidPetOwnerId() {
        // Mock pet service to return pets
        when(petService.getPetsByPetOwnerID(validPetOwnerId)).thenReturn(List.of(pet1, pet2));

        // Mock pet owner service
        when(petOwnerService.getPetOwnerByPetOwnerID(validPetOwnerId)).thenReturn(petOwner);

        // Mock user service
        when(userService.getUserByUserID(petOwner.getUserID())).thenReturn(user);

        // Call controller method
        String result = petListController.displayPets(validPetOwnerId, model);

        // Verify that model contains the pets and petOwner
        verify(model, times(1)).addAttribute("pets", List.of(pet1, pet2));
        verify(model, times(1)).addAttribute("petOwner", petOwner);

        // Check that the correct view is returned
        assertEquals("petList", result);
    }

    // Negative Test: Invalid petOwnerId (no pets found)
    @Test
    public void testDisplayPets_InvalidPetOwnerId() {
        // Mock the PetOwnerService to return null for an invalid petOwnerId
        when(petOwnerService.getPetOwnerByPetOwnerID(invalidPetOwnerId)).thenReturn(null);

        // Call the displayPets method with an invalid petOwnerId
        String result = petListController.displayPets(invalidPetOwnerId, model);

        // Verify that the correct view (error page) is returned
        assertEquals("errorPage", result);

        // Verify that an error message was added to the model
        verify(model, times(1)).addAttribute(eq("errorMessage"), eq("Pet Owner not found."));
    }


    // Boundary Test: Pet owner with only one pet
    @Test
    public void testDisplayPets_OnePet() {
        // Mock pet service to return one pet
        when(petService.getPetsByPetOwnerID(validPetOwnerId)).thenReturn(List.of(pet1));

        // Mock pet owner service
        when(petOwnerService.getPetOwnerByPetOwnerID(validPetOwnerId)).thenReturn(petOwner);

        // Mock user service
        when(userService.getUserByUserID(petOwner.getUserID())).thenReturn(user);

        // Call controller method
        String result = petListController.displayPets(validPetOwnerId, model);

        // Verify that model contains the single pet and petOwner
        verify(model, times(1)).addAttribute("pets", List.of(pet1));
        verify(model, times(1)).addAttribute("petOwner", petOwner);

        // Check that the correct view is returned
        assertEquals("petList", result);
    }

    // Test: Delete a pet successfully
    @Test
    public void testDeletePet_Success() {
        // Mock the RedirectAttributes
        RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);

        // Call deletePet method
        String result = petListController.deletePet(pet1.getId(), validPetOwnerId, redirectAttributes);

        // Verify that petService.deletePetById is called with the correct petId
        verify(petService, times(1)).deletePetById(pet1.getId());

        // Verify that a flash attribute was added to the RedirectAttributes
        verify(redirectAttributes, times(1)).addFlashAttribute("message", "Pet deleted successfully.");

        // Check that the redirect URL is correct
        assertEquals("redirect:/petList?petOwnerId=" + validPetOwnerId, result);
    }
}
