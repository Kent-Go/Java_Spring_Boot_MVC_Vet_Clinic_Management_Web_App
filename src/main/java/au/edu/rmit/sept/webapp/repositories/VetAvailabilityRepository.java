package au.edu.rmit.sept.webapp.repositories;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import au.edu.rmit.sept.webapp.models.VetAvailability;
import java.util.List;

@Repository
public interface VetAvailabilityRepository extends JpaRepository<VetAvailability, Integer> {
    // Find all availabilities by vet ID
    @Query("SELECT va FROM VetAvailability va WHERE va.vetID = :vetId")
    List<VetAvailability> findAvailabilitiesByVetID(@Param("vetId") int vetId);
    // Find vet availability using vet_id and availability_id
    Optional<VetAvailability> findByVetIDAndAvailabilityID(int vetID, int availabilityID);

    // Find availability by vet and date
    @Query("SELECT va FROM VetAvailability va " +
            "JOIN va.availability a " +
            "WHERE va.vetID = :vetId " +
            "AND a.date = :date")
    Optional<VetAvailability> findByVetIdAndDate(@Param("vetId") int vetId, @Param("date") LocalDate date);
}