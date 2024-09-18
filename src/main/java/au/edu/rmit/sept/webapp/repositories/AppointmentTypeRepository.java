package au.edu.rmit.sept.webapp.repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;

import au.edu.rmit.sept.webapp.models.Appointment;
import au.edu.rmit.sept.webapp.models.AppointmentType;

public interface AppointmentTypeRepository extends JpaRepository<AppointmentType, Integer> {
    public AppointmentType findById(int appointmentTypeID);
}
