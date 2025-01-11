package gr.hua.dit.petapp.controllers;

import gr.hua.dit.petapp.entities.Pet;
import gr.hua.dit.petapp.services.PetServices;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pets")
public class PetController {
    private final PetServices petService;

    public PetController(PetServices petService) {
        this.petService = petService;
    }

    @GetMapping
    public List<Pet> getAllPets() {
        return petService.getAllPets();
    }

    @GetMapping("/{id}")
    public Pet getPetById(@PathVariable Long id) {
        return petService.getPetById(id);
    }

    @PreAuthorize("hasRole('SHELTER')")
    @PostMapping
    public void addPet(@RequestBody Pet pet) {
        System.out.println(pet.getVet());

        petService.savePet(pet);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('SHELTER')")
    @DeleteMapping("/{id}")
    public void deletePet(@PathVariable Long id) {
        petService.deletePet(id);
    }

    /*@PutMapping
    public void ChangeStatus(@PathVariable Integer id,@RequestBody Pet pet){
        petService.ChangeStatusPet(id,pet);
    }

    @PutMapping
    public void ApprovePetA(@PathVariable Integer id, @RequestBody Pet pet){
        petService.ApprovePetAdmin(id,pet);
    }

    @PutMapping
    public void ApprovedPetV(@PathVariable Integer id, @RequestBody Pet pet){
        petService.ApprovePetVet(id,pet);
    }*/
}

