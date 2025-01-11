package gr.hua.dit.petapp.services;

import gr.hua.dit.petapp.entities.Shelter;
import gr.hua.dit.petapp.repositories.ShelterRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShelterService {
    @Autowired
    private  ShelterRepository shelterRepository;

    @Transactional
    public Shelter updateShelter(Integer shelterId, Shelter updatedShelter) {
        Shelter shelter = shelterRepository.findById(shelterId)
                .orElseThrow(() -> new IllegalArgumentException("Shelter not found"));

        // Update fields
        if (updatedShelter.getEmail() != null) {
            shelter.setEmail(updatedShelter.getEmail());
        }

        if (updatedShelter.getUsername() != null) {
            shelter.setUsername(updatedShelter.getUsername());
        }

        if (updatedShelter.getPassword() != null) {
            shelter.setPassword(updatedShelter.getPassword());
        }

        if (updatedShelter.getName() != null) {
            shelter.setName(updatedShelter.getName());
        }

        if (updatedShelter.getRegion() != null) {
            shelter.setRegion(updatedShelter.getRegion());
        }

        return shelterRepository.save(shelter);
    }

    public ShelterService(ShelterRepository shelterRepository) {
        this.shelterRepository = shelterRepository;
    }

    @Transactional
    public void saveShelter(Shelter shelter) {
        if (shelterRepository.findByEmail(shelter.getEmail()).isPresent()) {
            throw new IllegalArgumentException("A shelter with this email already exists.");
        }
        shelterRepository.save(shelter);
    }

    @Transactional
    public List<Shelter> getShelters() {
        return shelterRepository.findAll();
    }
}
