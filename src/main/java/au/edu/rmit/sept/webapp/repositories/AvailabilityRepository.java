package au.edu.rmit.sept.webapp.repositories;

import au.edu.rmit.sept.webapp.models.Availability;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

@Repository
public interface AvailabilityRepository extends JpaRepository<Availability, Integer> {
    Optional<Availability> findByDateAndStartTimeAndEndTime(LocalDate date, LocalTime startTime, LocalTime endTime);
}