package au.edu.rmit.sept.webapp.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import au.edu.rmit.sept.webapp.models.ImmunisationHistory;
import au.edu.rmit.sept.webapp.repositories.ImmunisationHistoryRepository;

@Service
public class ImmunisationHistoryServiceImpl implements ImmunisationHistoryService {

    private ImmunisationHistoryRepository immunisationHistoryRepository;

    @Autowired
    public ImmunisationHistoryServiceImpl(ImmunisationHistoryRepository immunisationHistoryRepository) {
        this.immunisationHistoryRepository = immunisationHistoryRepository;
    }

    // Get immunisation history by pet ID sorted by date
    @Override
    public List<ImmunisationHistory> getImmunisationHistoryByPetID(int petID) {
        return immunisationHistoryRepository.findByPetIDOrderByDateAsc(petID)
                .orElseThrow(() -> new RuntimeException("Immunisation History not found"));
    }

    @Override
    public ImmunisationHistory saveOrUpdateImmunisationHistory(ImmunisationHistory immunisationHistory) {
        return immunisationHistoryRepository.save(immunisationHistory);
    }

    @Override
    public ImmunisationHistory getImmunisationHistoryByID(int id) {
        return immunisationHistoryRepository.findById(id).orElse(null);
    }
}