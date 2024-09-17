package au.edu.rmit.sept.webapp.services;

import java.util.List;
import au.edu.rmit.sept.webapp.models.SurgeryHistory;

public interface SurgeryHistoryService {
    // Get immunisation history by pet ID
    public List<SurgeryHistory> getSurgeryHistoryByPetID(int petID);
}
