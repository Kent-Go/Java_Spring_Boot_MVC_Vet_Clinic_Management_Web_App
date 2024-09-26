package au.edu.rmit.sept.webapp.services;

import java.util.Collection;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import au.edu.rmit.sept.webapp.models.Clinic;
import au.edu.rmit.sept.webapp.repositories.ClinicRepository;

@Service
public class ClinicServiceImpl implements ClinicService {

  private ClinicRepository clinicRepository;

  @Autowired
  public ClinicServiceImpl(ClinicRepository clinicRepository) {
    this.clinicRepository = clinicRepository;
  }

  @Override
  public Collection<Clinic> getAllClinics() {
    return clinicRepository.findAll();
  }

  @Override
  public Clinic getClinicByClinicID(int clinicID) {
    return clinicRepository.findById(clinicID).orElseThrow(() -> new RuntimeException("Clinic not found"));
  }
}