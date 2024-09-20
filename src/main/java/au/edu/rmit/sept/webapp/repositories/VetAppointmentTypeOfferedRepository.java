package au.edu.rmit.sept.webapp.repositories;

import java.util.Collection;

import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import au.edu.rmit.sept.webapp.models.VetAppointmentTypeOffered;

@Repository
public interface VetAppointmentTypeOfferedRepository extends JpaRepository<VetAppointmentTypeOffered, Integer> {
    Collection<VetAppointmentTypeOffered> findByAppointmentTypeID(int appointmentTypeId);
}
