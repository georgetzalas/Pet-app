package gr.hua.dit.petapp.services;

import gr.hua.dit.petapp.entities.Citizen;
import gr.hua.dit.petapp.entities.Vet;
import gr.hua.dit.petapp.repositories.CitizenRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CitizenServices {
    private CitizenRepository citizenRepository;

    @Transactional
    public Citizen updateCitizen(Integer citizenId, Citizen updatedCitizen) {
        Citizen citizen = citizenRepository.findById(citizenId)
                .orElseThrow(() -> new IllegalArgumentException("Vet not found"));

        // Update fields
        if (updatedCitizen.getEmail() != null) {
            citizen.setEmail(updatedCitizen.getEmail());
        }

        if (updatedCitizen.getUsername() != null) {
            citizen.setUsername(updatedCitizen.getUsername());
        }

        if (updatedCitizen.getPassword() != null) {
            citizen.setPassword(updatedCitizen.getPassword());
        }

        if (updatedCitizen.getName() != null) {
            citizen.setName(updatedCitizen.getName());
        }

        if (updatedCitizen.getSurname() != null) {
            citizen.setSurname(updatedCitizen.getSurname());
        }

        return citizenRepository.save(citizen);
    }

    public CitizenServices(CitizenRepository citizenRepository) {
        this.citizenRepository = citizenRepository;
    }

    @Transactional
    public List<Citizen> getAllCitizens() {
        return citizenRepository.findAll();
    }

    @Transactional
    public Citizen getCitizenById(Integer id) {
        return citizenRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Citizen not found"));
    }

    @Transactional
    public void saveCitizen(Citizen citizen) {
        if (citizenRepository.findByEmail(citizen.getEmail()).isPresent()) {
            throw new IllegalArgumentException("A citizen with this email already exists.");
        }
        citizenRepository.save(citizen);
    }

    @Transactional
    public void deleteCitizen(Integer id) {
        Citizen citizen = citizenRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Citizen not found"));
        citizenRepository.deleteById(id);
    }
}
