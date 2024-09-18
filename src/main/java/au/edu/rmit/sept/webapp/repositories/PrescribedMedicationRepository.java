package au.edu.rmit.sept.webapp.repositories;

import java.util.List;
import au.edu.rmit.sept.webapp.models.PrescribedMedication;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrescribedMedicationRepository extends JpaRepository<PrescribedMedication, Integer> {
    // Get a list of prescribed medication by appointment ID
    List<PrescribedMedication> findByAppointmentID(int appointmentID);
}
