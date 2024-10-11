package au.edu.rmit.sept.webapp.services;

import au.edu.rmit.sept.webapp.models.VetAvailability;

import java.time.LocalDate;
import java.util.List;

public interface VetAvailabilityService {
    void saveOrUpdateVetAvailability(int vetId, int availabilityId, LocalDate date);
    List<VetAvailability> findAvailabilitiesByVetId(int vetId);
    VetAvailability findByVetIdAndDate(int vetId, LocalDate date);
    void deleteVetAvailability(VetAvailability vetAvailability);
}
