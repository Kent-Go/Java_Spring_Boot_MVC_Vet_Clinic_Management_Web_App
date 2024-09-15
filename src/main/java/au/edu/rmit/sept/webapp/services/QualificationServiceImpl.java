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
    return qualificationRepository.save(qualification);
  }
}