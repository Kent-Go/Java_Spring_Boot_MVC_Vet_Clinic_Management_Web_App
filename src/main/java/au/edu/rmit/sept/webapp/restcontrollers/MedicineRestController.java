package au.edu.rmit.sept.webapp.restcontrollers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;

import au.edu.rmit.sept.webapp.models.Medicine;
import au.edu.rmit.sept.webapp.repositories.MedicineRepository;

import java.util.List;

@RestController
@RequestMapping("/api/medicines")
public class MedicineRestController {
    @Autowired
    private MedicineRepository medicineRepository;

    @GetMapping
    public List<Medicine> getMedicines() {
        return medicineRepository.findAll();
    }
}
