package au.edu.rmit.sept.webapp.services;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;
import java.util.Collection;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import au.edu.rmit.sept.webapp.models.AppointmentType;
import au.edu.rmit.sept.webapp.repositories.AppointmentTypeRepository;

public class AppointmentTypeServiceImplTest {

    @Mock
    private AppointmentTypeRepository appointmentTypeRepository;

    @InjectMocks
    private AppointmentTypeServiceImpl appointmentTypeService;

    @BeforeEach
    public void setUp() {
        // Initialize mocks before each test
        MockitoAnnotations.openMocks(this);
    }

    // Test get appointment type by id
    @Test
    public void testGetAppointmentTypeByAppointmentTypeID() {
        // Create AppointmentType object
        AppointmentType appointmentType1 = new AppointmentType("General Clinical Consultation", 30, "This service involves a comprehensive assessment of your pet’s overall health. The veterinarian will discuss any concerns you have, review your pet’s medical history, and provide recommendations for preventive care or treatment.");
        AppointmentType appointmentType2 = new AppointmentType("Physical Examination", 45, "During a physical examination, the veterinarian will thoroughly check your pet’s body, including the eyes, ears, mouth, skin, and coat. This helps in identifying any signs of illness or abnormalities early on.");
        AppointmentType appointmentType3 = new AppointmentType("Dental Care", 30, "Dental care services focus on maintaining your pet’s oral health. This includes teeth cleaning, polishing, and addressing any dental issues such as plaque buildup, gum disease, or tooth extractions if necessary.");
        AppointmentType appointmentType4 = new AppointmentType("Surgery", 90, "Veterinary surgery encompasses a wide range of procedures, from routine spaying and neutering to more complex surgeries like tumor removal or orthopedic operations. These procedures are performed under anesthesia to ensure your pet’s comfort and safety.");
        AppointmentType appointmentType5 = new AppointmentType("Diet and Nutrition", 60, "This service involves creating a balanced and nutritious diet plan tailored to your pet’s specific needs. The veterinarian will provide guidance on the best types of food, portion sizes, and any necessary supplements to ensure your pet’s optimal health.");          

        // Mock the findById method to return the appointmentType
        when(appointmentTypeRepository.findById(1)).thenReturn(appointmentType1);
        when(appointmentTypeRepository.findById(2)).thenReturn(appointmentType2);
        when(appointmentTypeRepository.findById(3)).thenReturn(appointmentType3);
        when(appointmentTypeRepository.findById(4)).thenReturn(appointmentType4);
        when(appointmentTypeRepository.findById(5)).thenReturn(appointmentType5);

        // Call the getAppointmentTypeByAppointmentTypeID method and verify the result
        for (int i = 1; i <= 5; i++) {
                AppointmentType expected = null;
                switch (i) {
                case 1: expected = appointmentType1; break;
                case 2: expected = appointmentType2; break;
                case 3: expected = appointmentType3; break;
                case 4: expected = appointmentType4; break;
                case 5: expected = appointmentType5; break;
                }
                AppointmentType result = appointmentTypeService.getAppointmentTypeByAppointmentTypeID(i);
                assertEquals(expected, result);

                // Verify that the findById method is called exactly once with the correct ID
                verify(appointmentTypeRepository, times(1)).findById(i);
        }
    }

    // Test get all appointment types
    @Test
    public void testGetAllAppointmentType() {
        // Create AppointmentType object
        AppointmentType appointmentType1 = new AppointmentType("General Clinical Consultation", 30, "This service involves a comprehensive assessment of your pet’s overall health. The veterinarian will discuss any concerns you have, review your pet’s medical history, and provide recommendations for preventive care or treatment.");
        AppointmentType appointmentType2 = new AppointmentType("Physical Examination", 45, "During a physical examination, the veterinarian will thoroughly check your pet’s body, including the eyes, ears, mouth, skin, and coat. This helps in identifying any signs of illness or abnormalities early on.");
        AppointmentType appointmentType3 = new AppointmentType("Dental Care", 30, "Dental care services focus on maintaining your pet’s oral health. This includes teeth cleaning, polishing, and addressing any dental issues such as plaque buildup, gum disease, or tooth extractions if necessary.");
        AppointmentType appointmentType4 = new AppointmentType("Surgery", 90, "Veterinary surgery encompasses a wide range of procedures, from routine spaying and neutering to more complex surgeries like tumor removal or orthopedic operations. These procedures are performed under anesthesia to ensure your pet’s comfort and safety.");
        AppointmentType appointmentType5 = new AppointmentType("Diet and Nutrition", 60, "This service involves creating a balanced and nutritious diet plan tailored to your pet’s specific needs. The veterinarian will provide guidance on the best types of food, portion sizes, and any necessary supplements to ensure your pet’s optimal health.");          

        // Mock the findAll method to return a list of appointment types
        List<AppointmentType> appointmentTypes = Arrays.asList(appointmentType1, appointmentType2, appointmentType3, appointmentType4, appointmentType5);
        when(appointmentTypeRepository.findAll()).thenReturn(appointmentTypes);

        // Call the getAllAppointmentType method and verify the result
        Collection<AppointmentType> result = appointmentTypeService.getAllAppointmentType();
        assertEquals(5, result.size());

        // Verify that the findAll method is called exactly once
        verify(appointmentTypeRepository, times(1)).findAll();

        // Check the appointment types in the result
        AppointmentType[] resultArray = result.toArray(new AppointmentType[0]);
        assertEquals(appointmentType1, resultArray[0]);
        assertEquals(appointmentType2, resultArray[1]);
        assertEquals(appointmentType3, resultArray[2]);
        assertEquals(appointmentType4, resultArray[3]);
        assertEquals(appointmentType5, resultArray[4]);
    }
}
