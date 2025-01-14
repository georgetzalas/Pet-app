package gr.hua.dit.petapp.services;

import gr.hua.dit.petapp.repositories.CitizenRepository;
import gr.hua.dit.petapp.repositories.ShelterRepository;
import gr.hua.dit.petapp.repositories.VetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    @Autowired
    private CitizenRepository citizenRepository;

    @Autowired
    private ShelterRepository shelterRepository;

    @Autowired
    private VetRepository vetRepository;

    public boolean emailExists(String email) {
        return citizenRepository.findByEmail(email).isPresent() ||
                shelterRepository.findByEmail(email).isPresent() ||
                vetRepository.findByEmail(email).isPresent();
    }
}