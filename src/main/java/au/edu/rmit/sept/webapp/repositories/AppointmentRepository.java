package au.edu.rmit.sept.webapp.repositories;

import java.time.LocalDate;
import java.util.Collection;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import au.edu.rmit.sept.webapp.models.Appointment;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {

    boolean existsByVetIdAndDate(int vetId, LocalDate date);
    public Collection<Appointment> findByVetID(int vetID);

    public Collection<Appointment> findByPetID(int petID);

    // Find appointment using vet_id and date
    public Collection<Appointment> findByVetIDAndDateOrderByStartTimeAsc(int vetID, LocalDate date);

    // Find appointment and automatically order by date and startTime
    public Collection<Appointment> findByVetIDOrderByDateAscStartTimeAsc(int vetID);

    // Find appointments within a specific date range and order by date and
    // startTime
    public Collection<Appointment> findByVetIDAndDateBetweenOrderByDateAscStartTimeAsc(int vetID, LocalDate startDate,
                                                                                       LocalDate endDate);

    // Find appointments after current date and order by date and startTime based on
    // a petID
    public Collection<Appointment> findByPetIDAndDateAfterOrderByDateAscStartTimeAsc(int petID, LocalDate date);
}