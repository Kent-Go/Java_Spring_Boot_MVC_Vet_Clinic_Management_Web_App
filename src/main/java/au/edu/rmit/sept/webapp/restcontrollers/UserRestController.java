package au.edu.rmit.sept.webapp.restcontrollers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;

import au.edu.rmit.sept.webapp.models.User;
import au.edu.rmit.sept.webapp.repositories.UserRepository;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserRestController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public List<User> getUsers() {
        return userRepository.findAll();
    }
}
