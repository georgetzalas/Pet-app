package gr.hua.dit.petapp.controllers;

import gr.hua.dit.petapp.entities.Shelter;
import gr.hua.dit.petapp.services.ShelterService;
import gr.hua.dit.petapp.exception.EmailAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shelters")
public class ShelterController {
    private ShelterService shelterService;

    public ShelterController(ShelterService shelterService) {
        this.shelterService = shelterService;
    }

    @PreAuthorize("hasRole('SHELTER')")
    @PostMapping
    public ResponseEntity<String> addShelter(@RequestBody Shelter shelter) {
        try {
            shelterService.saveShelter(shelter);
            return new ResponseEntity<>("Shelter account created successfully!", HttpStatus.CREATED);
        } catch (EmailAlreadyExistsException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @PreAuthorize("hasRole('ADMIN') or hasRole('CITIZEN')")
    @GetMapping
    public List<Shelter> getShelters()
    {
        return shelterService.getShelters();
    }

}
