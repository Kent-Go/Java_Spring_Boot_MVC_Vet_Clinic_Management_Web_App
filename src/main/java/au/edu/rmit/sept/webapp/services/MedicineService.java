package au.edu.rmit.sept.webapp.services;

import java.util.Collection;

import au.edu.rmit.sept.webapp.models.Medicine;

public interface MedicineService {
  // Get all the medicines
  public Collection<Medicine> getAllMedicines();

  // Get a medicine by their name
  public Medicine getMedicineByName(String medicineName);

  // Get a medicine by their ID
  public Medicine getMedicineByID(int medicineID);

  // Create a new medicine
  public Medicine createMedicine(Medicine medicine);
  
}