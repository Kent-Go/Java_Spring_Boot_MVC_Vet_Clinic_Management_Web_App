package au.edu.rmit.sept.webapp.repositories;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import au.edu.rmit.sept.webapp.models.AppointmentType;

@Repository
public interface AppointmentTypeRepository extends JpaRepository<AppointmentType, Integer> {
    public AppointmentType findById(int appointmentTypeID);
}
