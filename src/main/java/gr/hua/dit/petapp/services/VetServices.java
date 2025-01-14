package gr.hua.dit.petapp.services;

import gr.hua.dit.petapp.entities.Shelter;
import gr.hua.dit.petapp.entities.Vet;
import gr.hua.dit.petapp.repositories.VetRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VetServices {
    private final VetRepository vetRepository;

    @Transactional
    public Vet updateVet(Integer vetId, Vet updatedVet) {
        Vet vet = vetRepository.findById(vetId)
                .orElseThrow(() -> new IllegalArgumentException("Vet not found"));

        // Update fields
        if (updatedVet.getEmail() != null) {
            vet.setEmail(updatedVet.getEmail());
        }

        if (updatedVet.getUsername() != null) {
            vet.setUsername(updatedVet.getUsername());
        }

        if (updatedVet.getPassword() != null) {
            vet.setPassword(updatedVet.getPassword());
        }

        if (updatedVet.getName() != null) {
            vet.setName(updatedVet.getName());
        }

        if (updatedVet.getSurname() != null) {
            vet.setSurname(updatedVet.getSurname());
        }

        return vetRepository.save(vet);
    }


    public VetServices(VetRepository vetRepository) {
        this.vetRepository = vetRepository;
    }

    @Transactional
    public List<Vet> getAllVets() {
        return vetRepository.findAll();
    }

    @Transactional
    public Vet getVetById(Integer id) {
        return vetRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Vet not found"));
    }

    @Transactional
    public void saveVet(Vet vet) {
        if (vetRepository.findByEmail(vet.getEmail()).isPresent()) {
            throw new IllegalArgumentException("A vet with this email already exists.");
        }
        vetRepository.save(vet);
    }

    @Transactional
    public void deleteVet(Integer id) {
        Vet vet = vetRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Vet not found"));
        vetRepository.deleteById(id);
    }
}

