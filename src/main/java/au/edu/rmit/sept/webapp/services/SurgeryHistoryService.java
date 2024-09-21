package au.edu.rmit.sept.webapp.services;

import java.util.List;
import au.edu.rmit.sept.webapp.models.SurgeryHistory;

public interface SurgeryHistoryService {
    // Get surgery history by pet ID sorted by date
    public List<SurgeryHistory> getSurgeryHistoryByPetID(int petID);

    // Get surgery history by ID
    public SurgeryHistory getSurgeryHistoryByID(int id);

    // Save or update surgery history
    public SurgeryHistory saveOrUpdateSurgeryHistory(SurgeryHistory surgeryHistory);
}
