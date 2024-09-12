package au.edu.rmit.sept.webapp.repositories;

import java.util.Collection;
import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;

import au.edu.rmit.sept.webapp.models.Appointment;

public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {
    public Collection<Appointment> findByVetID(int vetID);
    public Collection<Appointment> findByPetID(int petID);
    public Collection<Appointment> findByVetIDAndDate(int vetID, Date date);
}
