package au.edu.rmit.sept.webapp.restcontrollers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;

import au.edu.rmit.sept.webapp.models.Qualification;
import au.edu.rmit.sept.webapp.repositories.QualificationRepository;

import java.util.List;

@RestController
@RequestMapping("/api/qualifications")
public class QualificationRestController {
    @Autowired
    private QualificationRepository qualificationRepository;

    @GetMapping
    public List<Qualification> getQualifications() {
        return qualificationRepository.findAll();
    }
}
