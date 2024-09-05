package au.edu.rmit.sept.webapp.restcontrollers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import au.edu.rmit.sept.webapp.models.Vet;
import au.edu.rmit.sept.webapp.services.VetServiceImpl;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.IOException;

@RestController
@RequestMapping("/api/vets")
public class VetRestController {
    @Autowired
    private VetServiceImpl vetServiceImpl;

    @GetMapping
    public Collection<Vet> getAllVets() {
        return vetServiceImpl.getAllVets();
    }

    @GetMapping("/userID={id}")
    public Vet getVetById(@PathVariable int userID) {
        return vetServiceImpl.getVetByUserID(userID);
    }

    @PostMapping
    public Vet addVet(
        @RequestParam("title") String title,
        @RequestParam("languagesSpoken") String languagesSpoken,
        @RequestParam("selfDescription") String selfDescription,
        // @RequestParam("profilePicture") MultipartFile profilePicture,
        @RequestParam("userID") int userID
        ) 
        throws IOException {
            Vet vet = new Vet();
            vet.setTitle(title);
            vet.setLanguagesSpoken(languagesSpoken);
            vet.setSelfDescription(selfDescription);
            vet.setUserID(userID);
            // vet.setProfilePicture(profilePicture.getBytes());

            return vetServiceImpl.createVet(vet);
        }
}
