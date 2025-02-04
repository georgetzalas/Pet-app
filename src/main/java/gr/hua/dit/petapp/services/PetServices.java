package gr.hua.dit.petapp.services;

import gr.hua.dit.petapp.entities.*;
import gr.hua.dit.petapp.repositories.CitizenRepository;
import gr.hua.dit.petapp.repositories.PetRepository;
import gr.hua.dit.petapp.repositories.ShelterRepository;
import gr.hua.dit.petapp.repositories.VetRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Service
public class PetServices {
    private PetRepository petRepository;
    private ShelterRepository shelterRepository;
    private CitizenRepository citizenRepository;
    private VetRepository vetRepository;

    public PetServices(PetRepository petRepository, ShelterRepository shelterRepository, CitizenRepository citizenRepository, VetRepository vetRepository) {
        this.petRepository = petRepository;
        this.shelterRepository = shelterRepository;
        this.citizenRepository = citizenRepository;
        this.vetRepository = vetRepository;
    }

    @Transactional
    public List<Pet> getAllPets() {
        return petRepository.findAll();
    }

    @Transactional
    public Pet getPetById(Long id) {
        return petRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Pet not found"));
    }

    @Transactional
    public void savePet(Pet pet) {
        Citizen citizen = null;
        Vet vet = null;
        Shelter shelter = null;

        if(pet.getCitizen() != null)
        {
            citizen = citizenRepository.findById((pet.getCitizen().getId().intValue())).get();
            pet.setCitizen(citizen);
        }

        if(pet.getVet() != null)
        {
            vet = vetRepository.findById((pet.getVet().getId().intValue())).get();
            pet.setVet(vet);
        }
        if(pet.getShelter() != null)
        {
            shelter = shelterRepository.findById(pet.getShelter().getId().intValue()).get();
            pet.setShelter(shelter);
        }


        petRepository.save(pet);
    }

    @Transactional
    public void deletePet(Long id) {
        Pet pet = petRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Pet not found"));
        petRepository.deleteById(id);
    }

    /*@Transactional
    public void ChangeStatusPet(Long id, Pet pet){
        Pet pet1 = getPetById(id);
        pet1.setAge(pet.getAge());
        pet1.setBreed(pet.getBreed());
        pet1.setHeight(pet.getHeight());
        pet1.setPicture(pet.getPicture());
        pet1.setName(pet.getName());
        pet1.setAdoptionStatus(pet.getAdoptionStatus());
        pet1.setShelter(pet.getShelter());
        pet1.setSex(pet.getSex());
        pet1.setType(pet.getType());
        pet1.setHeight(pet.getHeight());
        savePet(pet1);
    }*/

    @Transactional
    public void rejectPetAdmin(Long petId) {
        Pet pet = petRepository.findById(petId)
                .orElseThrow(() -> new IllegalArgumentException("Pet not found with ID: " + petId));

        pet.setAdminApprovalStatus(ApprovalStatus.REJECTED);
        petRepository.save(pet);
    }

    @Transactional
    public void approvePetAdmin(Long petId) {
        Pet pet = petRepository.findById(petId)
                .orElseThrow(() -> new IllegalArgumentException("Pet not found with ID: " + petId));

        pet.setAdminApprovalStatus(ApprovalStatus.APPROVED);
        petRepository.save(pet);
    }

    @Transactional
    public void approvePetVet(Long petId) {
        Pet pet = petRepository.findById(petId)
                .orElseThrow(() -> new IllegalArgumentException("Pet not found with ID: " + petId));

        pet.setVetApprovalStatus(ApprovalStatus.APPROVED);
        petRepository.save(pet);
    }

    @Transactional
    public void rejectPetVet(Long petId) {
        Pet pet = petRepository.findById(petId)
                .orElseThrow(() -> new IllegalArgumentException("Pet not found with ID: " + petId));

        pet.setVetApprovalStatus(ApprovalStatus.REJECTED);
        petRepository.save(pet);
    }

    // Get all pets waiting for approval
    public List<Pet> getPendingPets() {
        List<Pet> pets = petRepository.findAll();
        List<Pet> pendingPets = new ArrayList<>();
        for(Pet pet : pets)
        {
            if(pet.getAdminApprovalStatus().equals(ApprovalStatus.PENDING) || pet.getVetApprovalStatus().equals(ApprovalStatus.PENDING))
            {
                pendingPets.add(pet);
            }
        }
        return pendingPets;
    }
}

