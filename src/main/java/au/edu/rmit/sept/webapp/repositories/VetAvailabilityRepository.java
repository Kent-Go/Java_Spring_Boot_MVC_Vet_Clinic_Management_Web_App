package au.edu.rmit.sept.webapp.repositories;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import au.edu.rmit.sept.webapp.models.VetAvailability;

@Repository
public interface VetAvailabilityRepository extends JpaRepository<VetAvailability, Integer> {
    // Find availability using vet_id and date
    @Query("SELECT va FROM VetAvailability va " +
            "JOIN va.vet v " +
            "JOIN va.availability a " +
            "WHERE va.vet.id = :vetId " +
            "AND a.date = :date")
    Optional<VetAvailability> findByVetIdAndDate(@Param("vetId") Long vetId, @Param("date") LocalDate date);
}
