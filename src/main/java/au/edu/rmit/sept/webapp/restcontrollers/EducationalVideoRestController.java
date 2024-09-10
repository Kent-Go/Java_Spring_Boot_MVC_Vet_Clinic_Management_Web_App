package au.edu.rmit.sept.webapp.restcontrollers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;

import au.edu.rmit.sept.webapp.models.EducationalVideo;
import au.edu.rmit.sept.webapp.repositories.EducationalVideoRepository;

import java.util.List;

@RestController
@RequestMapping("/api/educationalVideos")
public class EducationalVideoRestController {
    @Autowired
    private EducationalVideoRepository educationalVideoRepository;

    @GetMapping
    public List<EducationalVideo> getVideos() {
        return educationalVideoRepository.findAll();
    }
}
