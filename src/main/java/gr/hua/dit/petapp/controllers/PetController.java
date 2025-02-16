package gr.hua.dit.petapp.controllers;

import gr.hua.dit.petapp.entities.Pet;
import gr.hua.dit.petapp.payload.response.MessageResponse;
import gr.hua.dit.petapp.services.PetServices;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;
import java.util.List;

@RestController
@RequestMapping("/api/pets")
public class PetController {
    private final PetServices petService;


    public PetController(PetServices petService) {
        this.petService = petService;
    }

    @GetMapping
    public ResponseEntity<?> getAllPets() {
        List<Pet> pets = petService.getAllPets();
        if(pets.isEmpty()) {
            return ResponseEntity.ok(new MessageResponse("No pets found"));
        }
        return ResponseEntity.ok(pets);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPetById(@PathVariable Long id) {
        try
        {
            Pet pet = petService.getPetById(id);
            return ResponseEntity.ok(pet);
        }catch (IllegalArgumentException e)
        {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

    @PreAuthorize("hasRole('ROLE_SHELTER')")
    @PostMapping
    public void addPet(@RequestBody Pet pet) {
        byte[] pictureBytes = Base64.getDecoder().decode(pet.getStrPicture());
        pet.setPicture(pictureBytes);
        pet.setStrPicture(null);
        petService.savePet(pet);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('SHELTER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePet(@PathVariable Long id) {
        try
        {
            petService.deletePet(id);
            return ResponseEntity.ok(new MessageResponse("Pet deleted"));
        }catch (IllegalArgumentException e)
        {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    // Approve a specific pet
    @PutMapping("/approve-pet-admin/{petId}")
    public ResponseEntity<MessageResponse> approvePetAdmin(@PathVariable Long petId) {
        try
        {
            petService.approvePetAdmin(petId);
            return ResponseEntity.ok(new MessageResponse("Pet approved successfully"));

        }catch(IllegalArgumentException e)
        {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    // Reject a specific pet
    @PutMapping("/reject-pet-admin/{petId}")
    public ResponseEntity<MessageResponse> rejectPetAdmin(@PathVariable Long petId) {
        try
        {
            petService.rejectPetAdmin(petId);
            return ResponseEntity.ok(new MessageResponse("Pet rejected successfully"));

        }catch(IllegalArgumentException e)
        {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

    @PreAuthorize("hasRole('VET')")
    // Approve a specific pet
    @PutMapping("/approve-pet-vet/{petId}")
    public ResponseEntity<MessageResponse> approvePetVet(@PathVariable Long petId) {
        try
        {
            petService.approvePetVet(petId);
            return ResponseEntity.ok(new MessageResponse("Pet approved successfully"));

        }catch(IllegalArgumentException e)
        {
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

    @PreAuthorize("hasRole('VET')")
    // Reject a specific pet
    @PutMapping("/reject-pet-vet/{petId}")
    public ResponseEntity<MessageResponse> rejectPetVet(@PathVariable Long petId) {
        try {
            petService.rejectPetVet(petId);
            return ResponseEntity.ok(new MessageResponse("Pet rejected successfully"));

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('VET')")
    // Get list of pets waiting for approval
    @GetMapping("/pending-pets")
    public ResponseEntity<?> getPendingPets() {
        List<Pet> pendingPets = petService.getPendingPets();
        if(!pendingPets.isEmpty())
        {
            return ResponseEntity.ok(pendingPets);
        }
        return ResponseEntity.ok(new MessageResponse("No pending pets"));
    }

    /*@PutMapping
    public void ChangeStatus(@PathVariable Integer id,@RequestBody Pet pet){
        petService.ChangeStatusPet(id,pet);
    }*/

    @PreAuthorize("hasRole('CITIZEN')")
    @GetMapping("/search")
    public ResponseEntity<?> searchPets(
            @RequestParam(required = false) String region,
            @RequestParam(required = false) String type) {
        List<Pet> filteredPets = petService.searchPets(region, type);
        if (filteredPets.isEmpty()) {
            return ResponseEntity.ok(new MessageResponse("No pets found matching criteria"));
        }
        return ResponseEntity.ok(filteredPets);
    }

    @GetMapping("/types")
    public ResponseEntity<List<String>> getAllPetTypes() {
        List<String> types = petService.getAllPetTypes();
        System.out.println(types);
        return ResponseEntity.ok(types);
    }

    @GetMapping("/regions")
    public ResponseEntity<List<String>> getAllShelterRegions() {
        List<String> regions = petService.getAllShelterRegions();
        System.out.println(regions);
        return ResponseEntity.ok(regions);
    }

}
