package au.edu.rmit.sept.webapp.services;

import java.util.Collection;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import au.edu.rmit.sept.webapp.models.Medicine;
import au.edu.rmit.sept.webapp.repositories.MedicineRepository;

@Service
public class MedicineServiceImpl implements MedicineService {

  private MedicineRepository medicineRepository;

  @Autowired
  public MedicineServiceImpl(MedicineRepository medicineRepository) {
    this.medicineRepository = medicineRepository;
  }

  @Override
  public Collection<Medicine> getAllMedicines() {
    return medicineRepository.findAll();
  }

  @Override
  public Medicine getMedicineByName(String medicineName) {
    return medicineRepository.findByName(medicineName);
  }

  @Override
  public Medicine createMedicine(Medicine medicine) {
    return medicineRepository.save(medicine);
  }
}