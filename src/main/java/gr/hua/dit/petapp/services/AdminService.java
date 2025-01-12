package gr.hua.dit.petapp.services;

import gr.hua.dit.petapp.entities.*;
import gr.hua.dit.petapp.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class AdminService {

    @Autowired
    private CitizenRepository citizenRepository;

    @Autowired
    private ShelterRepository shelterRepository;

    @Autowired
    private VetRepository vetRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private PetRepository petRepository;
    @Autowired
    private UserRepository userRepository;

    public boolean emailExists(String email) {
        return citizenRepository.findByEmail(email).isPresent() ||
                shelterRepository.findByEmail(email).isPresent() ||
                vetRepository.findByEmail(email).isPresent();
    }

    @Transactional
    public void approveAccount(String userType, Integer id) {
        switch (userType.toLowerCase()) {
            case "citizen":
                updateStatus(citizenRepository.findById(id), AccountStatus.APPROVED);
                break;
            case "shelter":
                updateStatus(shelterRepository.findById(id), AccountStatus.APPROVED);
                break;
            case "vet":
                updateStatus(vetRepository.findById(id), AccountStatus.APPROVED);
                break;
        }
    }

    @Transactional
    public void rejectAccount(String userType, Integer id, String remarks) {
        switch (userType.toLowerCase()) {
            case "citizen":
                updateStatus(citizenRepository.findById(id), AccountStatus.REJECTED);
                break;
            case "shelter":
                updateStatus(shelterRepository.findById(id), AccountStatus.REJECTED);
                break;
            case "vet":
                updateStatus(vetRepository.findById(id), AccountStatus.REJECTED);
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
    public List<Map<String, Object>> getAllAccounts() {
        List<Map<String, Object>> accounts = new ArrayList<>();

        // Fetch shelters
        shelterRepository.findAll().forEach(shelter -> {
            Map<String, Object> account = new HashMap<>();
            account.put("id", shelter.getId());
            account.put("type", "Shelter");
            account.put("name", shelter.getName());
            account.put("email", shelter.getEmail());
            account.put("region", shelter.getRegion());
            accounts.add(account);
        });

        // Fetch vets
        vetRepository.findAll().forEach(vet -> {
            Map<String, Object> account = new HashMap<>();
            account.put("id", vet.getId());
            account.put("type", "Vet");
            account.put("name", vet.getName());
            account.put("email", vet.getEmail());
            account.put("surname", vet.getSurname());
            accounts.add(account);
        });

        // Fetch citizens
        citizenRepository.findAll().forEach(citizen -> {
            Map<String, Object> account = new HashMap<>();
            account.put("id", citizen.getId());
            account.put("type", "Citizen");
            account.put("name", citizen.getName());
            account.put("email", citizen.getEmail());
            account.put("surname", citizen.getSurname());
            accounts.add(account);
        });

        return accounts;
    }
    @Transactional
    public void deleteAccount(String email) {
        Optional<Shelter> shelter = shelterRepository.findByEmail(email);
        if (shelter.isPresent()) {
            System.out.print("okeyy");
            shelterRepository.delete(shelter.get());
            emailService.sendDeletionNotification(email, "Shelter account deleted successfully.");

            return;
        }

        Optional<User> vet = userRepository.findByEmail(email);
        if (vet.isPresent()) {
            System.out.print("okeyy");
            userRepository.deleteById(vet.get().getId());
            emailService.sendDeletionNotification(email, "Vet account deleted successfully.");
            return;
        }

        Optional<Citizen> citizen = citizenRepository.findByEmail(email);
        if (citizen.isPresent()) {
            citizenRepository.delete(citizen.get());
            emailService.sendDeletionNotification(email, "Citizen account deleted successfully.");
            return;
        }

        throw new IllegalArgumentException("Account not found with email: " + email);
    }
    // Get all pets waiting for approval
    public List<Pet> getPendingPets() {
        return petRepository.findByStatus(ApprovalStatus.PENDING);
    }

    @Transactional
    public void approvePet(Long petId) {
        Pet pet = petRepository.findById(petId)
                .orElseThrow(() -> new IllegalArgumentException("Pet not found with ID: " + petId));

        pet.setStatus(ApprovalStatus.APPROVED);
        petRepository.save(pet);
    }

    @Transactional
    public void rejectPet(Long petId) {
        Pet pet = petRepository.findById(petId)
                .orElseThrow(() -> new IllegalArgumentException("Pet not found with ID: " + petId));

        pet.setStatus(ApprovalStatus.REJECTED);
        petRepository.save(pet);
    }

}
