package au.edu.rmit.sept.webapp.repositories;

import java.util.List;

import org.springframework.stereotype.Repository;

import au.edu.rmit.sept.webapp.models.PrescribedMedication;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface PrescribedMedicationRepository extends JpaRepository<PrescribedMedication, Integer> {
    // Get a list of prescribed medication by appointment ID
    List<PrescribedMedication> findByAppointmentID(int appointmentID);
}
