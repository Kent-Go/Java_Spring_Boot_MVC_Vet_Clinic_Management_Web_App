package au.edu.rmit.sept.webapp.services;

import java.util.List;
import au.edu.rmit.sept.webapp.models.ImmunisationHistory;

public interface ImmunisationHistoryService {
    // Get immunisation history by pet ID
    public List<ImmunisationHistory> getImmunisationHistoryByPetID(int petID);
}
