package au.edu.rmit.sept.webapp.restcontrollers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;

import au.edu.rmit.sept.webapp.models.VetAvailability;
import au.edu.rmit.sept.webapp.repositories.VetAvailabilityRepository;

import java.util.Optional;
import java.time.LocalDate;

@RestController
@RequestMapping("/api/vet_availability")
public class VetAvailabilityRestController {

    @Autowired
    private VetAvailabilityRepository vetAvailabilityRepository;

    @GetMapping("/{vetID}")
    public Optional<VetAvailability> getAvailabilityByVetIdAndDate(@PathVariable("vetID") int vetID, @RequestParam String date) {
        LocalDate localDate = LocalDate.parse(date);
        return vetAvailabilityRepository.findByVetIdAndDate(vetID, localDate);
    }
}