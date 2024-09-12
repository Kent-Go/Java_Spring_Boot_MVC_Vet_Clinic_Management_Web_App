package au.edu.rmit.sept.webapp.repositories;

import java.util.Collection;

import au.edu.rmit.sept.webapp.models.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {
    public Collection<Appointment> findByVetID(int vetID);

    public Collection<Appointment> findByPetID(int petID);

    // Automatically order by date and startTime
    public Collection<Appointment> findByVetIDOrderByDateAscStartTimeAsc(int vetID);
}
