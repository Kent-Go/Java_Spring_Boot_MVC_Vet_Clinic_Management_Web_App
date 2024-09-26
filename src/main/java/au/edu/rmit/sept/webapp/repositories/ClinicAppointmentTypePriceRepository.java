package au.edu.rmit.sept.webapp.repositories;

import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;

import au.edu.rmit.sept.webapp.models.Appointment;
import au.edu.rmit.sept.webapp.models.ClinicAppointmentTypePrice;

@Repository
public interface ClinicAppointmentTypePriceRepository extends JpaRepository<ClinicAppointmentTypePrice, Integer> {
    // Find ClinicAppointmentTypePrice based on clinic id and appointment type id
    public ClinicAppointmentTypePrice findByClinicIDAndAppointmentTypeID(int clinicID, int appointmentTypeID);
}
