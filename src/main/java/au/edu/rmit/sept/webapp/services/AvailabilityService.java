package au.edu.rmit.sept.webapp.services;

import au.edu.rmit.sept.webapp.models.Availability;

import java.time.LocalDate;
import java.time.LocalTime;

public interface AvailabilityService {
    Availability saveAvailability(Availability availability);

    Availability findAvailabilityByDateAndTime(LocalDate date, LocalTime startTime, LocalTime endTime);
}
