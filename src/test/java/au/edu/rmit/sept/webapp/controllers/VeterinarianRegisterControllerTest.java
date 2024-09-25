package au.edu.rmit.sept.webapp.controllers;

import au.edu.rmit.sept.webapp.models.Address;
import au.edu.rmit.sept.webapp.models.Qualification;
import au.edu.rmit.sept.webapp.models.User;
import au.edu.rmit.sept.webapp.models.Vet;
import au.edu.rmit.sept.webapp.services.AddressService;
import au.edu.rmit.sept.webapp.services.QualificationService;
import au.edu.rmit.sept.webapp.services.UserService;
import au.edu.rmit.sept.webapp.services.VetService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;


public class VeterinarianRegisterControllerTest {

    @InjectMocks
    private VeterinarianRegisterController veterinarianRegisterController;

    @Mock
    private UserService userService;

    @Mock
    private AddressService addressService;

    @Mock
    private VetService vetService;

    @Mock
    private QualificationService qualificationService;

    @Mock
    private Model model;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Positive Test: Testing successful registration with valid data
     */
    @Test
    public void testRegisterVeterinarian_Success() {
        // Arrange: mock the valid inputs and responses
        User mockUser = new User("John", "Doe", LocalDate.of(1980, 1, 1), "Male", "1234567890", "john@example.com", "password123");
        when(userService.createUser(any(User.class))).thenReturn(mockUser);
        when(userService.getUserByEmail("john@example.com")).thenReturn(mockUser);

        Vet mockVet = new Vet("Dr.", "English", "Specializes in surgery", mockUser.getId(), 1);
        when(vetService.createVet(any(Vet.class))).thenReturn(mockVet);
        when(vetService.getVetByUserID(mockUser.getId())).thenReturn(mockVet);

        Qualification mockQualification = new Qualification("Veterinary Medicine", "Harvard", "USA", 2005, mockVet.getId());
        when(qualificationService.createQualification(any(Qualification.class))).thenReturn(mockQualification);

        Address mockAddress = new Address("123 Main St", "Suburbia", "Stateville", "12345", mockUser.getId());
        when(addressService.createAddress(any(Address.class))).thenReturn(mockAddress);

        // Act: call the method to register a veterinarian
        String viewName = veterinarianRegisterController.registerVeterinarian(
                "John", "Doe", "1980-01-01", "Male", "1234567890", "john@example.com", "password123",
                "123 Main St", "Suburbia", "Stateville", "12345", "Dr.", "English", "Specializes in surgery", 1,
                "Veterinary Medicine", "Harvard", "USA", "2005", model);

        // Assert: check that all services were called and the expected view was returned
        verify(userService, times(1)).createUser(any(User.class));
        verify(addressService, times(1)).createAddress(any(Address.class));
        verify(vetService, times(1)).createVet(any(Vet.class));
        verify(qualificationService, times(1)).createQualification(any(Qualification.class));

        assertEquals("redirect:/vetDashboard?userId=0&vetId=0", viewName);
    }

    /**
     * Negative Test: Missing required fields
     */
    @Test
    public void testRegisterVeterinarian_MissingRequiredFields() {
        // Act & Assert: Expect IllegalArgumentException due to missing first and last name
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            veterinarianRegisterController.registerVeterinarian(
                    "", "", "1990-01-01", "Male", "123456789", "john@example.com", "password",
                    "123 Street", "Suburb", "State", "3000", "Dr.", "English", "I am a vet", 1,
                    "Veterinary Science", "Uni of Melbourne", "Australia", "2015", model);
        });

        // Assert the error message
        assertEquals("First name and last name are required", exception.getMessage());
    }

    /**
     * Negative Test: Invalid Email Format
     */
    @Test
    public void testRegisterVeterinarian_InvalidEmail() {
        // Arrange: Set up invalid email scenario
        when(userService.getUserByEmail("invalidemail@example.com")).thenReturn(null);

        // Act & Assert: Expect IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> {
            veterinarianRegisterController.registerVeterinarian(
                    "John", "Doe", "1990-01-01", "Male", "123456789", "invalidemail@example.com", "password",
                    "123 Street", "Suburb", "State", "3000", "Dr.", "English", "I am a vet", 1,
                    "Veterinary Science", "Uni of Melbourne", "Australia", "2015", model);
        });
    }

    /**
     * Boundary Test: Testing the maximum allowed year for Qualification
     */
    @Test
    public void testRegisterVeterinarian_YearBoundary_Max() {
        // Act & Assert: Call the method with a future year
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            veterinarianRegisterController.registerVeterinarian(
                    "John", "Doe", "1980-01-01", "Male", "1234567890", "john@example.com", "password123",
                    "123 Main St", "Suburbia", "Stateville", "12345", "Dr.", "English", "Specializes in surgery", 1,
                    "Veterinary Medicine", "Harvard", "USA", String.valueOf(LocalDate.now().getYear() + 1), model); // Future year
        });

        assertEquals("Year awarded cannot be in the future", exception.getMessage());
    }

    /**
     * Boundary Test: Testing the minimum allowed year for Qualification
     */
    @Test
    public void testRegisterVeterinarian_YearBoundary_Min() {
        // Act & Assert: Call the method with the minimum valid year (modify as per business rules)
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            veterinarianRegisterController.registerVeterinarian(
                    "John", "Doe", "1980-01-01", "Male", "1234567890", "john@example.com", "password123",
                    "123 Main St", "Suburbia", "Stateville", "12345", "Dr.", "English", "Specializes in surgery", 1,
                    "Veterinary Medicine", "Harvard", "USA", "1899", model); // Minimum year threshold example
        });

        assertEquals("Year awarded cannot be in the past beyond a certain threshold", exception.getMessage());
    }
}
