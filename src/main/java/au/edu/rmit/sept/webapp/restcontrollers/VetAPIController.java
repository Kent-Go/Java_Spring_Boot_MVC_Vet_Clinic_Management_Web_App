package au.edu.rmit.sept.webapp.restcontrollers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import au.edu.rmit.sept.webapp.repositories.VetRepository;
import au.edu.rmit.sept.webapp.models.Vet;

import java.util.List;

@RestController
@RequestMapping("/api")
public class VetAPIController {
    @Autowired
    private VetRepository vetRepository;

    @GetMapping("/vets")
    public List<Vet> getVets() {
        return vetRepository.findAll();
    }
}
