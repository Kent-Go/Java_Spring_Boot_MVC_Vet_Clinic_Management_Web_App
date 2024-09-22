package au.edu.rmit.sept.webapp.services;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import au.edu.rmit.sept.webapp.models.Qualification;
import au.edu.rmit.sept.webapp.repositories.QualificationRepository;

@Service
public class QualificationServiceImpl implements QualificationService {

  private QualificationRepository qualificationRepository;

  @Autowired
  public QualificationServiceImpl(QualificationRepository qualificationRepository) {
    this.qualificationRepository = qualificationRepository;
  }

  // Find all qualifications by vetID
  @Override
  public List<Qualification> getQualificationsByVetID(int vetID) {
    return qualificationRepository.findByVetID(vetID);
  }

  // Create a new qualification and save it to the database
  @Override
  public Qualification createQualification(Qualification qualification) {
    if (qualification.getName() == null || qualification.getName().trim().isEmpty()) {
      throw new IllegalArgumentException("Qualification name cannot be empty");
    }
    if (qualification.getUniversity() == null || qualification.getUniversity().trim().isEmpty()) {
      throw new IllegalArgumentException("University cannot be empty");
    }
    if (qualification.getCountry() == null || qualification.getCountry().trim().isEmpty()) {
      throw new IllegalArgumentException("Country cannot be empty");
    }
    if (qualification.getYear() <= 0) {
      throw new IllegalArgumentException("Year must be a positive number");
    }

    return qualificationRepository.save(qualification);
  }
}