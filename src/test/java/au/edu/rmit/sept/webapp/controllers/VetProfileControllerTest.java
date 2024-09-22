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
import org.mockito.*;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import org.springframework.util.LinkedMultiValueMap;

import java.time.LocalDate;
import java.util.*;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class VetProfileControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private AddressService addressService;

    @Mock
    private VetService vetService;

    @Mock
    private QualificationService qualificationService;

    @InjectMocks
    private VetProfileController vetProfileController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(vetProfileController).build();
    }

    
    // Negative Test: Vet not found
    @Test
    public void testShowProfilePage_VetNotFound() throws Exception {
        int vetId = 1;

        // Define mock behavior
        when(vetService.getVetByVetID(vetId)).thenReturn(null);

        // Perform GET request and expect 404 Not Found
        mockMvc.perform(get("/vetProfile").param("vetId", String.valueOf(vetId)))
                .andExpect(status().isNotFound());

        // Verify that services were not called beyond vetService
        verify(vetService, times(1)).getVetByVetID(vetId);
        verifyNoMoreInteractions(userService, addressService, qualificationService);
    }

    // Boundary Test: Invalid vet ID (negative number)
    @Test
    public void testShowProfilePage_InvalidVetId() throws Exception {
        int vetId = -1;

        // Perform GET request and expect 400 Bad Request
        mockMvc.perform(get("/vetProfile").param("vetId", String.valueOf(vetId)))
                .andExpect(status().isBadRequest());

        // Verify that services were not called
        verifyNoInteractions(vetService, userService, addressService, qualificationService);
    }


    // Negative Test: Missing required fields in update
    @Test
    public void testUpdateProfile_MissingFields() throws Exception {
        // Empty form data (simulating missing fields)
        Map<String, String> formData = new HashMap<>();

        // Convert formData to MultiValueMap
        LinkedMultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.setAll(formData);

        // Simulate an empty file upload (since the controller expects a MultipartFile)
        MockMultipartFile emptyFile = new MockMultipartFile("profilePicture", new byte[0]);

        // Perform the multipart POST request with empty form data and file
        mockMvc.perform(multipart("/vetProfile")
                        .file(emptyFile)
                        .params(params))
                .andExpect(status().isBadRequest());

        // Verify that services were not called
        verifyNoInteractions(userService, addressService, vetService, qualificationService);
    }

    // Negative Test: Invalid date format
    @Test
    public void testUpdateProfile_InvalidDateFormat() throws Exception {
        // Mock data with an invalid date format
        Map<String, String> formData = new HashMap<>();
        formData.put("vetId", "1");
        formData.put("birthDate", "invalid-date");

        // Convert formData to MultiValueMap
        LinkedMultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.setAll(formData);

        // Mocked file upload
        MockMultipartFile emptyFile = new MockMultipartFile("profilePicture", new byte[0]);

        // Perform multipart POST request with invalid date format
        mockMvc.perform(multipart("/vetProfile")
                        .file(emptyFile)
                        .params(params))
                .andExpect(status().isBadRequest());

        // Verify that services were not called
        verifyNoInteractions(userService, addressService, vetService, qualificationService);
    }

}
