package gr.hua.dit.petapp.services;

import gr.hua.dit.petapp.entities.*;
import gr.hua.dit.petapp.repositories.*;
import jdk.jshell.JShellException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final CitizenRepository citizenRepository;
    private final VetRepository vetRepository;
    private final ShelterRepository shelterRepository;
    private final EmailService emailService;

    public UserService(UserRepository userRepository, CitizenRepository citizenRepository, VetRepository vetRepository, ShelterRepository shelterRepository, EmailService emailService) {
        this.userRepository = userRepository;
        this.citizenRepository = citizenRepository;
        this.vetRepository = vetRepository;
        this.shelterRepository = shelterRepository;
        this.emailService = emailService;
    }
    @Transactional
    public void approveAccount(String userType, Integer id) {
        switch (userType.toLowerCase()) {
            case "citizen":
                Citizen citizen = citizenRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Citizen not found"));
                updateStatus(Optional.of(citizen), AccountStatus.APPROVED);
                break;
            case "shelter":
                Shelter shelter = shelterRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Shelter not found"));
                updateStatus(Optional.of(shelter), AccountStatus.APPROVED);
                break;
            case "vet":
                Vet vet = vetRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Vet not found"));
                updateStatus(Optional.of(vet), AccountStatus.APPROVED);
                break;
        }
    }

    @Transactional
    public void rejectAccount(String userType, Integer id, String remarks) {
        switch (userType.toLowerCase()) {
            case "citizen":
                Citizen citizen = citizenRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Citizen not found"));
                updateStatus(Optional.of(citizen), AccountStatus.REJECTED);
                break;
            case "shelter":
                Shelter shelter = shelterRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Shelter not found"));
                updateStatus(Optional.of(shelter), AccountStatus.REJECTED);
                break;
            case "vet":
                Vet vet = vetRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Vet not found"));
                updateStatus(Optional.of(vet), AccountStatus.REJECTED);
                break;
        }
    }

    private void updateStatus(Optional<? extends User> userOptional, AccountStatus status) {
        userOptional.ifPresent(user -> {
            if (user instanceof Citizen) {
                ((Citizen) user).setStatus(status);
            } else if (user instanceof Shelter) {
                ((Shelter) user).setStatus(status);
            } else if (user instanceof Vet) {
                ((Vet) user).setStatus(status);
            }

            if (status == AccountStatus.APPROVED) {
                emailService.sendEmail(user.getEmail(), "Account Approved", "Your account has been approved!");
            } else if (status == AccountStatus.REJECTED) {
                emailService.sendEmail(user.getEmail(), "Account Rejected", "Your account has been rejected.");
            }
        });
    }

    // Fetch all accounts
    public List<User> getAllAccounts() {
        List<User> accounts = new ArrayList<>();

        List<Shelter> shelets = shelterRepository.findAll();
        List<Vet> vets = vetRepository.findAll();
        List<Citizen> citizens = citizenRepository.findAll();

        accounts.addAll(shelets);
        accounts.addAll(vets);
        accounts.addAll(citizens);

        return accounts;
    }
    @Transactional
    public void deleteAccount(Integer id) {
        Optional<Shelter> shelter = shelterRepository.findById(id);
        if (shelter.isPresent()) {
            shelterRepository.delete(shelter.get());
            emailService.sendDeletionNotification(shelter.get().getEmail(), "Shelter account deleted successfully.");
            return;
        }

        Optional<Vet> vet = vetRepository.findById(id);
        if (vet.isPresent()) {
            userRepository.deleteById(vet.get().getId());
            emailService.sendDeletionNotification(vet.get().getEmail(), "Vet account deleted successfully.");
            return;
        }

        Optional<Citizen> citizen = citizenRepository.findById(id);
        if (citizen.isPresent()) {
            citizenRepository.delete(citizen.get());
            emailService.sendDeletionNotification(citizen.get().getEmail(), "Citizen account deleted successfully.");
            return;
        }

        throw new IllegalArgumentException("Account not found with id: " + id);
    }

    @Transactional
    public User getAccount(Integer id) {
        Optional<Shelter> shelter = shelterRepository.findById(id);
        if(shelter.isPresent()) {
            return shelter.get();
        }

        Optional<Vet> vet = vetRepository.findById(id);
        if(vet.isPresent())
        {
            return vet.get();
        }

        Optional<Citizen> citizen = citizenRepository.findById(id);
        if(citizen.isPresent())
        {
            return citizen.get();
        }

        throw new IllegalArgumentException("Account not found with id: " + id);
    }

}
