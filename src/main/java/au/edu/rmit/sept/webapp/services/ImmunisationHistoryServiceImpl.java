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

    @Override
    public List<ImmunisationHistory> getImmunisationHistoryByPetID(int petID) {
        return immunisationHistoryRepository.findByPetID(petID)
                .orElseThrow(() -> new RuntimeException("Immunisation History not found"));
    }
}