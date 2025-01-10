package gr.hua.dit.petapp.controllers;

import gr.hua.dit.petapp.entities.Vet;
import gr.hua.dit.petapp.exception.EmailAlreadyExistsException;
import gr.hua.dit.petapp.services.VetServices;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    @PreAuthorize("hasRole('VET') or hasRole('ADMIN')")
    @GetMapping
    public List<Vet> getAllVets() {
        return vetService.getAllVets();
    }
    @PreAuthorize("hasRole('ADMIN') or hasRole('SHELTER') or hasRole('VET') or hasRole('CITIZEN')")
    @GetMapping("/{id}")
    public Vet getVetById(@PathVariable Integer id) {
        return vetService.getVetById(id);
    }
    @PreAuthorize("hasRole('VET') or hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public void deleteVet(@PathVariable Integer id) {
        vetService.deleteVet(id);
    }
    @PreAuthorize("hasRole('VET')")
    @PostMapping
    public ResponseEntity<String> addVet(@RequestBody Vet vet) {
        try {
            vetServices.saveVet(vet);
            return new ResponseEntity<>("Vet account created successfully!", HttpStatus.CREATED);
        } catch (EmailAlreadyExistsException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
