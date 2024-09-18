package au.edu.rmit.sept.webapp.services;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import au.edu.rmit.sept.webapp.models.PrescribedMedication;
import au.edu.rmit.sept.webapp.repositories.PrescribedMedicationRepository;

@Service
public class PrescribedMedicationServiceImpl implements PrescribedMedicationService {
  private PrescribedMedicationRepository prescribedMedicationRepository;

  @Autowired
  public PrescribedMedicationServiceImpl(PrescribedMedicationRepository prescribedMedicationRepository) {
    this.prescribedMedicationRepository = prescribedMedicationRepository;
  }

  @Override
  public List<PrescribedMedication> getPrescribedMedicationByAppointmentID(int appointmentID) {
    return prescribedMedicationRepository.findByAppointmentID(appointmentID);
  }

  @Override
  public PrescribedMedication getPrescribedMedicationByID(int id) {
    return prescribedMedicationRepository.findById(id).orElse(null);
  }

  @Override
  public PrescribedMedication updatePrescribedMedication(PrescribedMedication prescribedMedication) {
    return prescribedMedicationRepository.save(prescribedMedication);
  }

  @Override
  public void deletePrescribedMedicationByID(int id) {
    prescribedMedicationRepository.deleteById(id);
  }
}