package au.edu.rmit.sept.webapp.services;

import java.util.List;
import au.edu.rmit.sept.webapp.models.PrescribedMedication;

public interface PrescribedMedicationService {
    // Get a list of prescribed medication by appointment ID
    public List<PrescribedMedication> getPrescribedMedicationByAppointmentID(int appointmentID);

    // Get prescribed medication by ID
    public PrescribedMedication getPrescribedMedicationByID(int id);

    // Update prescribed medication
    public PrescribedMedication updatePrescribedMedication(PrescribedMedication prescribedMedication);
}
