package au.edu.rmit.sept.webapp.services;

import au.edu.rmit.sept.webapp.models.VetAvailability;
import au.edu.rmit.sept.webapp.repositories.VetAvailabilityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class VetAvailabilityService {

    @Autowired
    private VetAvailabilityRepository vetAvailabilityRepository;

    // Save or update VetAvailability
    public void saveOrUpdateVetAvailability(int vetId, int availabilityId, LocalDate date) {
        // Check if there is an existing availability for the given vet and date
        Optional<VetAvailability> existingVetAvailability = vetAvailabilityRepository.findByVetIdAndDate(vetId, date);

        if (existingVetAvailability.isPresent()) {
            // Delete the existing vet availability
            vetAvailabilityRepository.delete(existingVetAvailability.get());
        }

        // Create a new vet availability after deletion or if it doesn't exist
        VetAvailability vetAvailability = new VetAvailability(vetId, availabilityId);
        vetAvailabilityRepository.save(vetAvailability);
    }

    // Find all availabilities by vet ID
    public List<VetAvailability> findAvailabilitiesByVetId(int vetId) {
        return vetAvailabilityRepository.findAvailabilitiesByVetID(vetId);
    }
    // Find vet availability by vet ID and date
    public VetAvailability findByVetIdAndDate(int vetId, LocalDate date) {
        return vetAvailabilityRepository.findByVetIdAndDate(vetId, date).orElse(null);
    }
    public void deleteVetAvailability(VetAvailability vetAvailability) {
        vetAvailabilityRepository.delete(vetAvailability);
    }

}
