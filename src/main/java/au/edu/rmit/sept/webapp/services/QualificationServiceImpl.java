package au.edu.rmit.sept.webapp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import au.edu.rmit.sept.webapp.models.Qualification;
import au.edu.rmit.sept.webapp.repositories.QualificationRepository;

@Service
public class QualificationServiceImpl implements QualificationService {

  private QualificationRepository qualificationRepository;

  @Autowired
  public QualificationServiceImpl(QualificationRepository qualificationRepository) {
    this.qualificationRepository = qualificationRepository;
  }

  @Override
  public Qualification getQualificationByVetID(int vetID) {
    return qualificationRepository.findByVetID(vetID).orElse(null);
  }

  @Override
  public Qualification createQualification(Qualification qualification) {
    return qualificationRepository.save(qualification);
  }
}