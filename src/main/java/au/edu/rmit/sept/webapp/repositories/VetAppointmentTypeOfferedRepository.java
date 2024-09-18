package au.edu.rmit.sept.webapp.repositories;

import java.time.LocalDate;
import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;

import au.edu.rmit.sept.webapp.models.VetAppointmentTypeOffered;

public interface VetAppointmentTypeOfferedRepository extends JpaRepository<VetAppointmentTypeOffered, Integer> {
    Collection<VetAppointmentTypeOffered> findByAppointmentTypeID(int appointmentTypeId);
}
