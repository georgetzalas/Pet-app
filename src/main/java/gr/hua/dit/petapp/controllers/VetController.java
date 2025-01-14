package gr.hua.dit.petapp.controllers;

import gr.hua.dit.petapp.entities.Vet;
import gr.hua.dit.petapp.exception.EmailAlreadyExistsException;
import gr.hua.dit.petapp.payload.response.MessageResponse;
import gr.hua.dit.petapp.services.VetServices;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vets")
public class VetController {
    private final VetServices vetService;
    private final VetServices vetServices;

    public VetController(VetServices vetService, VetServices vetServices) {
        this.vetService = vetService;
        this.vetServices = vetServices;
    }

    @PreAuthorize("hasRole('ADMIN')or hasRole('SHELTER')")
    @GetMapping
    public ResponseEntity<?> getAllVets() {
        List<Vet> vets = vetService.getAllVets();
        if(vets.isEmpty()) {
            return ResponseEntity.ok(new MessageResponse("Vets not found"));
        }
        return ResponseEntity.ok(vets);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('SHELTER')")
    @GetMapping("/{id}")
    public ResponseEntity<?> getVetById(@PathVariable Integer id) {
        try {
            Vet vet =vetService.getVetById(id);
            return ResponseEntity.ok(vet);
        }catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteVet(@PathVariable Integer id) {
        try{
            vetService.deleteVet(id);
            return ResponseEntity.ok(new MessageResponse("Vet deleted"));
        }catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

    @PreAuthorize("hasRole('VET')")
    @PostMapping
    public ResponseEntity<?> addVet(@RequestBody Vet vet) {
        try {
            vetServices.saveVet(vet);
            return ResponseEntity.ok(new MessageResponse("Vet account created successfylly!"));
        } catch (EmailAlreadyExistsException ex) {
            return ResponseEntity.badRequest().body(new MessageResponse(ex.getMessage()));
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update-vet/{id}")
    public ResponseEntity<?> updateVetProfile(@PathVariable Integer id, @RequestBody Vet vetDetails) {
        try {
            vetService.updateVet(id, vetDetails);
            return ResponseEntity.ok(new MessageResponse("Vet profile updated successfully!"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

}
