package au.edu.rmit.sept.webapp.services;

import java.util.List;
import au.edu.rmit.sept.webapp.models.ImmunisationHistory;

public interface ImmunisationHistoryService {
    // Get immunisation history by pet ID sorted by date
    public List<ImmunisationHistory> getImmunisationHistoryByPetID(int petID);

    // Save or update immunisation history
    public ImmunisationHistory saveOrUpdateImmunisationHistory(ImmunisationHistory immunisationHistory);

    // Get immunisation history by ID
    public ImmunisationHistory getImmunisationHistoryByID(int id);
}
