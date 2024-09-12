package au.edu.rmit.sept.webapp.repositories;

import java.time.LocalDate;
import java.util.Collection;

import au.edu.rmit.sept.webapp.models.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {
    public Collection<Appointment> findByVetID(int vetID);

    public Collection<Appointment> findByPetID(int petID);

    //Find appointment and automatically order by date and startTime
    public Collection<Appointment> findByVetIDOrderByDateAscStartTimeAsc(int vetID);

    // Find appointments within a specific date range and order by date and startTime
    public Collection<Appointment> findByVetIDAndDateBetweenOrderByDateAscStartTimeAsc(int vetID, LocalDate startDate,
            LocalDate endDate);

}
