package au.edu.rmit.sept.webapp.repositories;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;

import au.edu.rmit.sept.webapp.models.Appointment;

public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {
    public Collection<Appointment> findByVetID(int vetID);

    public Collection<Appointment> findByPetID(int petID);

    //Find appointment using vet_id and date
    public Collection<Appointment> findByVetIDAndDate(int vetID, Date date);

    //Find appointment and automatically order by date and startTime
    public Collection<Appointment> findByVetIDOrderByDateAscStartTimeAsc(int vetID);

    // Find appointments within a specific date range and order by date and startTime
    public Collection<Appointment> findByVetIDAndDateBetweenOrderByDateAscStartTimeAsc(int vetID, LocalDate startDate,
            LocalDate endDate);

}
