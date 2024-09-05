package au.edu.rmit.sept.webapp.repositories;


import org.springframework.data.jpa.repository.JpaRepository;

import au.edu.rmit.sept.webapp.models.Vet;

public interface VetRepository extends JpaRepository<Vet, Integer> {
}
