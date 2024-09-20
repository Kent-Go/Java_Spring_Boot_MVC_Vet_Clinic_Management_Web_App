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
import java.util.Optional;
import au.edu.rmit.sept.webapp.models.Medicine;
import au.edu.rmit.sept.webapp.models.PrescribedMedication;
import au.edu.rmit.sept.webapp.repositories.PrescribedMedicationRepository;

@SpringBootTest
public class PrescribedMedicationServiceImplTest {

    // Mock the repository
    @MockBean
    private PrescribedMedicationRepository prescribedMedicationRepository;

    // Inject the service
    @Autowired
    private PrescribedMedicationServiceImpl prescribedMedicationService;

    // Test getting prescribed medication by appointment ID with no records found
    @Test
    public void testGetPrescribedMedicationByAppointmentIDNoRecordsFound() {
        int appointmentID = 999;

        // When the repository method is called, return an empty list
        when(prescribedMedicationRepository.findByAppointmentID(appointmentID))
                .thenReturn(List.of());

        // Get the medication from the service method by appointment ID
        List<PrescribedMedication> medications = prescribedMedicationService
                .getPrescribedMedicationByAppointmentID(appointmentID);

        // Verify the result between the mock medication and the medication returned by
        // the service method
        assertNotNull(medications);
        assertTrue(medications.isEmpty());

        // Verify the repository method was called once
        verify(prescribedMedicationRepository, times(1)).findByAppointmentID(appointmentID);
    }

    // Test getting prescribed medication by appointment ID with records found
    @Test
    public void testGetPrescribedMedicationByAppointmentIDWithRecords() {
        int appointmentID = 1;

        // Create mock medication
        Medicine mockMedicine = new Medicine(1, "Panadol", "100", "10.00");
        Medicine mockMedicine2 = new Medicine(2, "Nurofen", "50", "15.00");

        List<PrescribedMedication> mockMedications = List.of(
                new PrescribedMedication(1, 1, 1, "Take with food", mockMedicine.getId(), 1, appointmentID),
                new PrescribedMedication(2, 2, 2, "Take with water", mockMedicine2.getId(), 2, appointmentID));

        // When the repository method is called, return the mock medication
        when(prescribedMedicationRepository.findByAppointmentID(appointmentID))
                .thenReturn(mockMedications);

        // Get the medication from the service method by appointment ID
        List<PrescribedMedication> medications = prescribedMedicationService
                .getPrescribedMedicationByAppointmentID(appointmentID);

        // Verify the result between the mock medication and the medication returned by
        // the service method
        assertNotNull(medications);
        assertFalse(medications.isEmpty());
        assertEquals(2, medications.size());
        assertEquals(mockMedications, medications);

        // Verify the repository method was called
        verify(prescribedMedicationRepository, times(1)).findByAppointmentID(appointmentID);
    }

    // Test getting prescribed medication by ID with no record found
    @Test
    public void testGetPrescribedMedicationByIDNoRecordFound() {
        int nonExistentID = 999;

        when(prescribedMedicationRepository.findById(nonExistentID))
                .thenReturn(Optional.empty());

        PrescribedMedication medication = prescribedMedicationService.getPrescribedMedicationByID(nonExistentID);

        assertNull(medication);
    }

    // Test getting prescribed medication by ID with a record found
    @Test
    public void testGetPrescribedMedicationByIDWithRecord() {
        int id = 1;
        Medicine mockMedicine = new Medicine(1, "Panadol", "100", "10.00");
        PrescribedMedication mockMedication = new PrescribedMedication(1, 1, 1, "Take with food", mockMedicine.getId(),
                1, 1);

        // Set mockMedication to medicine and its id
        mockMedication.setId(1);
        mockMedication.setMedicine(mockMedicine);

        // When the repository method is called, return the mock medication
        when(prescribedMedicationRepository.findById(id))
                .thenReturn(Optional.of(mockMedication));

        // Get the medication from the service method by ID
        PrescribedMedication medication = prescribedMedicationService.getPrescribedMedicationByID(id);

        assertNotNull(medication);

        // Verify the result between the mock medication and the medication returned by
        // the service method
        assertAll("medication",
                () -> assertEquals(1, medication.getId(), "ID should be 1"),
                () -> assertEquals(mockMedication, medication, "Medication should be the same"),
                () -> assertEquals(1, medication.getDosage(), "Dosage should be 1"),
                () -> assertEquals(1, medication.getDailyFrequency(), "Daily frequency should be 1"),
                () -> assertEquals(1, medication.getDuration(), "Duration should be 1"),
                () -> assertEquals("Take with food", medication.getInstruction(),
                        "Instruction should be 'Take with food'"),
                () -> assertEquals(1, medication.getMedicineID(), "Medicine ID should be 1"),
                () -> assertEquals(1, medication.getMedicine().getId(), "Medicine ID should be 1"),
                () -> assertEquals("Panadol", medication.getMedicine().getName(), "Medicine name should be 'Panadol'"),
                () -> assertEquals("100", medication.getMedicine().getQuantity(), "Medicine quantity should be '100'"),
                () -> assertEquals("10.00", medication.getMedicine().getPrice(), "Medicine price should be '10.00'"),
                () -> assertEquals(1, medication.getAppointmentID(), "Appointment ID should be 1"),
                () -> assertEquals(1, medication.getOrderID(), "Order ID should be 1"));

        // Verify the repository method was called once
        verify(prescribedMedicationRepository, times(1)).findById(id);
    }

    // Test updating old prescribed medication to new prescribed medication
    @Test
    public void testUpdatePrescribedMedication() {
        // Create two medicine objects
        Medicine mockMedicine1 = new Medicine(1, "Panadol", "100", "10.00");
        Medicine mockMedicine2 = new Medicine(2, "Nurofen", "50", "15.00");

        // Create a PrescribedMedication object with the initial medicine
        PrescribedMedication medication = new PrescribedMedication();
        medication.setId(1);
        medication.setAppointmentID(1);
        medication.setDosage(1);
        medication.setDailyFrequency(1);
        medication.setDuration(1);
        medication.setInstruction("Take with food");
        medication.setMedicineID(mockMedicine1.getId());
        medication.setOrderID(1);
        medication.setMedicine(mockMedicine1);

        // Mock the repository to return the updated medication
        when(prescribedMedicationRepository.save(medication)).thenReturn(medication);

        // Update the PrescribedMedication object to use a different medicine
        medication.setMedicineID(mockMedicine2.getId());
        medication.setMedicine(mockMedicine2);

        // Call the service method to save the updated medication
        PrescribedMedication updatedMedication = prescribedMedicationService.updatePrescribedMedication(medication);

        // Verify that the updated medication is not null
        assertNotNull(updatedMedication);

        // Verify that the medicine associated with the updated medication is now
        // mockMedicine2
        assertEquals(mockMedicine2.getId(), updatedMedication.getMedicineID());
        assertEquals(mockMedicine2.getName(), updatedMedication.getMedicine().getName());

        // Verify that the save method was called once
        verify(prescribedMedicationRepository, times(1)).save(medication);
    }

    // Test saving a new prescribed medication
    @Test
    public void testSaveNewPrescribedMedication() {
        // Create a medicine object
        Medicine mockMedicine = new Medicine(1, "Panadol", "100", "10.00");

        // Create a PrescribedMedication object
        PrescribedMedication medication = new PrescribedMedication();
        medication.setAppointmentID(1);
        medication.setDosage(1);
        medication.setDailyFrequency(1);
        medication.setDuration(1);
        medication.setInstruction("Take with food");
        medication.setMedicineID(mockMedicine.getId());
        medication.setOrderID(1);
        medication.setMedicine(mockMedicine);

        // Mock the repository to return the saved medication
        when(prescribedMedicationRepository.save(medication)).thenReturn(medication);

        // Call the service method to save the medication
        PrescribedMedication savedMedication = prescribedMedicationService.updatePrescribedMedication(medication);

        // Verify that the saved medication is not null
        assertNotNull(savedMedication);

        // Verify that the medicine associated with the saved medication is mockMedicine
        assertEquals(mockMedicine.getId(), savedMedication.getMedicineID());
        assertEquals(mockMedicine.getName(), savedMedication.getMedicine().getName());

        // Verify that the save method was called once
        verify(prescribedMedicationRepository, times(1)).save(medication);
    }

    // Test deleting a prescribed medication by ID
    @Test
    public void testDeletePrescribedMedicationByID() {
        int id = 1;

        // Call the service method to delete the medication by ID
        prescribedMedicationService.deletePrescribedMedicationByID(id);

        // Verify that the delete method was called once
        verify(prescribedMedicationRepository, times(1)).deleteById(id);
    }
}
