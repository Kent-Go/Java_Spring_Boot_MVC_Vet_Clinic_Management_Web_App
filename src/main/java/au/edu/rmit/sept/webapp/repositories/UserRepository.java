package au.edu.rmit.sept.webapp.repositories;


import org.springframework.data.jpa.repository.JpaRepository;

import au.edu.rmit.sept.webapp.models.User;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByEmail(String email);
}
