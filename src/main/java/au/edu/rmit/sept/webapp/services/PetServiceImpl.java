package au.edu.rmit.sept.webapp.services;

import java.util.Collection;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import au.edu.rmit.sept.webapp.models.Pet;
import au.edu.rmit.sept.webapp.repositories.PetRepository;

@Service
public class PetServiceImpl implements PetService {
    
    private PetRepository petRepository;

    @Autowired
    public PetServiceImpl(PetRepository petRepository){
        this.petRepository = petRepository;
    }

    @Override
    public Collection<Pet> getAllPets(){
        return petRepository.findAll();
    }

    @Override
    public Pet getPetByPetID(int petID){
        return petRepository.findById(petID).orElseThrow(() -> new RuntimeException("Pet not found"));
    }

    @Override
    public Collection<Pet> getPetsByOwnerID(int ownerID){
        return petRepository.findAllByOwnerId(ownerID);
    }

    @Override
    public Pet createPet(Pet pet){
        return petRepository.save(pet);
    }
}
