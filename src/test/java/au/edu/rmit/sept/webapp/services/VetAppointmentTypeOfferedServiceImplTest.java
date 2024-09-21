package au.edu.rmit.sept.webapp.services;

import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.ArrayList;
import java.util.Collection;
import au.edu.rmit.sept.webapp.models.AppointmentType;
import au.edu.rmit.sept.webapp.models.VetAppointmentTypeOffered;
import au.edu.rmit.sept.webapp.repositories.VetAppointmentTypeOfferedRepository;

@SpringBootTest
class VetAppointmentTypeOfferedServiceImplTest {

        // Mock the repository
        @MockBean
        private VetAppointmentTypeOfferedRepository vetAppointmentTypeOfferedRepository;

        // Inject the service
        @Autowired
        private VetAppointmentTypeOfferedServiceImpl vetAppointmentTypeOfferedService;

        // Test for successful retrieval of appointment types offered by appointment
        // type ID
        @Test
        void testGetVetAppointmentTypeOfferedByAppointmentTypeIDWithRecords() {
                // Get one of the appointment types from the database
                AppointmentType appointmentType1 = new AppointmentType("General Checkup", 30, "General Checkup");
                appointmentType1.setId(1);

                // Create mock VetAppointmentTypeOffered objects
                VetAppointmentTypeOffered appointmentTypeOffered1 = new VetAppointmentTypeOffered();
                appointmentTypeOffered1.setId(1);
                appointmentTypeOffered1.setAppointmentTypeID(appointmentType1.getId());
                appointmentTypeOffered1.setAppointmentType(appointmentType1);

                VetAppointmentTypeOffered appointmentTypeOffered2 = new VetAppointmentTypeOffered();
                appointmentTypeOffered2.setId(2);
                appointmentTypeOffered2.setAppointmentTypeID(appointmentType1.getId());
                appointmentTypeOffered1.setAppointmentType(appointmentType1);

                // Create a list of mock objects
                List<VetAppointmentTypeOffered> mockAppointmentTypes = List.of(appointmentTypeOffered1,
                                appointmentTypeOffered2);

                // Mock finding the appointment types by appointment type ID
                when(vetAppointmentTypeOfferedRepository.findByAppointmentTypeID(appointmentType1.getId()))
                                .thenReturn(mockAppointmentTypes);

                // Call the service method
                Collection<VetAppointmentTypeOffered> appointmentTypes = vetAppointmentTypeOfferedService
                                .getVetAppointmentTypeOfferedByAppointmentTypeID(appointmentType1.getId());

                // Verify that the result is not null and contains the expected number of
                // elements
                assertNotNull(appointmentTypes);
                assertEquals(2, appointmentTypes.size());
                assertTrue(appointmentTypes.contains(appointmentTypeOffered1));
                assertTrue(appointmentTypes.contains(appointmentTypeOffered2));

                // Verify both appointment types offered are appointment type 1
                for (VetAppointmentTypeOffered appointmentTypeOffered : appointmentTypes) {
                        assertEquals(appointmentType1.getId(), appointmentTypeOffered.getAppointmentTypeID());
                }

                // Verify that the repository's method was called exactly once
                verify(vetAppointmentTypeOfferedRepository, times(1)).findByAppointmentTypeID(appointmentType1.getId());
        }

        // Test for no records found
        @Test
        void testGetVetAppointmentTypeOfferedByAppointmentTypeIDNoRecordsFound() {
                int appointmentTypeId = 999; // Assuming this ID does not exist

                // Mock the repository to return an empty list
                when(vetAppointmentTypeOfferedRepository.findByAppointmentTypeID(appointmentTypeId))
                                .thenReturn(new ArrayList<>());

                // Call the service method
                Collection<VetAppointmentTypeOffered> appointmentTypes = vetAppointmentTypeOfferedService
                                .getVetAppointmentTypeOfferedByAppointmentTypeID(appointmentTypeId);

                // Verify that the result is not null and is empty
                assertNotNull(appointmentTypes);
                assertTrue(appointmentTypes.isEmpty());

                // Verify that the repository's method was called exactly once
                verify(vetAppointmentTypeOfferedRepository, times(1)).findByAppointmentTypeID(appointmentTypeId);
        }

        // Test with null appointment type ID
        @Test
        void testGetVetAppointmentTypeOfferedByAppointmentTypeIDWithNullID() {
                // Test for null appointmentTypeId
                int appointmentTypeId = 0;

                // Mock the repository to return an empty list
                when(vetAppointmentTypeOfferedRepository.findByAppointmentTypeID(appointmentTypeId))
                                .thenReturn(new ArrayList<>());

                // Call the service method
                Collection<VetAppointmentTypeOffered> appointmentTypes = vetAppointmentTypeOfferedService
                                .getVetAppointmentTypeOfferedByAppointmentTypeID(appointmentTypeId);

                // Verify that the result is not null and is empty
                assertNotNull(appointmentTypes);
                assertTrue(appointmentTypes.isEmpty());

                // Verify that the repository's method was called exactly once
                verify(vetAppointmentTypeOfferedRepository, times(1)).findByAppointmentTypeID(appointmentTypeId);
        }

        // Test with negative appointment type ID
        @Test
        void testGetVetAppointmentTypeOfferedByAppointmentTypeIDWithNegativeID() {
                // Test for negative appointmentTypeId
                int appointmentTypeId = -1;

                // Mock the repository to return an empty list
                when(vetAppointmentTypeOfferedRepository.findByAppointmentTypeID(appointmentTypeId))
                                .thenReturn(new ArrayList<>());

                // Call the service method
                Collection<VetAppointmentTypeOffered> appointmentTypes = vetAppointmentTypeOfferedService
                                .getVetAppointmentTypeOfferedByAppointmentTypeID(appointmentTypeId);

                // Verify that the result is not null and is empty
                assertNotNull(appointmentTypes);
                assertTrue(appointmentTypes.isEmpty());

                // Verify that the repository's method was called exactly once
                verify(vetAppointmentTypeOfferedRepository, times(1)).findByAppointmentTypeID(appointmentTypeId);
        }
}