package au.edu.rmit.sept.webapp.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import au.edu.rmit.sept.webapp.models.SurgeryHistory;
import au.edu.rmit.sept.webapp.repositories.SurgeryHistoryRepository;

@Service
public class SurgeryHistoryServiceImpl implements SurgeryHistoryService {

    private SurgeryHistoryRepository surgeryHistoryRepository;

    @Autowired
    public SurgeryHistoryServiceImpl(SurgeryHistoryRepository surgeryHistoryRepository) {
        this.surgeryHistoryRepository = surgeryHistoryRepository;
    }

    // Get surgery history by pet ID sorted by date
    @Override
    public List<SurgeryHistory> getSurgeryHistoryByPetID(int petID) {
        return surgeryHistoryRepository.findByPetIDOrderByDateAsc(petID)
                .orElseThrow(() -> new RuntimeException("Surgery History not found"));
    }

    @Override
    public SurgeryHistory getSurgeryHistoryByID(int id) {
        return surgeryHistoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Surgery History not found"));
    }

    @Override
    public SurgeryHistory saveOrUpdateSurgeryHistory(SurgeryHistory surgeryHistory) {
        return surgeryHistoryRepository.save(surgeryHistory);
    }
}