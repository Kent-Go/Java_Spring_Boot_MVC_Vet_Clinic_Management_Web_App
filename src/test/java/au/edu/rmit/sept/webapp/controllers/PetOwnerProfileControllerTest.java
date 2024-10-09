package au.edu.rmit.sept.webapp.controllers;

import au.edu.rmit.sept.webapp.models.Address;
import au.edu.rmit.sept.webapp.models.User;
import au.edu.rmit.sept.webapp.services.AddressService;
import au.edu.rmit.sept.webapp.services.PetOwnerService;
import au.edu.rmit.sept.webapp.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PetOwnerProfileControllerTest {

    @Mock
    private PetOwnerService petOwnerService;

    @Mock
    private UserService userService;

    @Mock
    private AddressService addressService;

    @InjectMocks
    private PetOwnerProfileController petOwnerProfileController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(petOwnerProfileController).build();
    }

    // Negative Test: Test petOwnerProfile when no PetOwner is found
    @Test
    public void testShowPetOwnerProfile_NotFound() throws Exception {
        // Mock the correct method that is used in the controller
        when(petOwnerService.getPetOwnerByPetOwnerID(1)).thenReturn(null);

        // Perform the request
        mockMvc.perform(get("/petOwnerProfile").param("petOwnerId", "1"))
                .andExpect(status().isNotFound());

        // Verify that the correct method was invoked
        verify(petOwnerService, times(1)).getPetOwnerByPetOwnerID(1);
    }

    // Boundary Test: Test with invalid parameter values (negative ID)
    @Test
    public void testShowPetOwnerProfile_InvalidId() throws Exception {
        // Perform GET request with an invalid ID (negative value) and expect a 400 Bad
        // Request
        mockMvc.perform(get("/petOwnerProfile").param("petOwnerId", "-1"))
                .andExpect(status().isBadRequest());

        // Verify that no services were called due to invalid input
        verify(petOwnerService, times(0)).getPetOwnerByUserID(anyInt());
    }

    // Positive Test: Test updating PetOwner profile
    @Test
    public void testUpdatePetOwnerProfile_Success() throws Exception {
        // Mock objects
        User mockUser = new User("John", "Doe", LocalDate.of(1990, 1, 1), "Male", "1234567890", "john@example.com",
                "password");
        Address mockAddress = new Address("123 Street", "Suburb", "State", "3000", 1);

        // Define mock behavior
        when(userService.getUserByUserID(1)).thenReturn(mockUser);
        when(addressService.getAddressByUserID(1)).thenReturn(mockAddress);

        // Perform POST request
        mockMvc.perform(post("/petOwnerProfile")
                .param("edit-userId", "1")
                .param("edit-petOwnerId", "1")
                .param("edit-firstname", "Jane")
                .param("edit-lastname", "Doe")
                .param("edit-dob", "1990-01-01")
                .param("gender", "Female")
                .param("edit-phone", "9876543210")
                .param("edit-email", "jane@example.com")
                .param("edit-password", "newPassword123") // Add this missing parameter
                .param("edit-streetAddress", "456 New Street")
                .param("edit-suburb", "New Suburb")
                .param("edit-state", "New State")
                .param("edit-postcode", "3001"))
                .andExpect(status().is3xxRedirection()); // Expect redirection

        // Verify that services were called to update user and address
        verify(userService, times(1)).createUser(mockUser);
        verify(addressService, times(1)).createAddress(mockAddress);
    }

    // Negative Test: Test updating PetOwner profile with invalid data
    @Test
    public void testUpdatePetOwnerProfile_InvalidData() throws Exception {
        mockMvc.perform(post("/petOwnerProfile")
                .param("edit-userId", "1")
                .param("edit-firstname", "")
                .param("edit-lastname", "")
                .param("edit-dob", "invalid-date"))
                .andExpect(status().isBadRequest());

        verify(userService, times(0)).createUser(any(User.class));
        verify(addressService, times(0)).createAddress(any(Address.class));
    }
}
