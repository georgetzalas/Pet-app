package gr.hua.dit.petapp.controllers;

import gr.hua.dit.petapp.exception.EmailAlreadyExistsException;
import gr.hua.dit.petapp.payload.response.MessageResponse;
import gr.hua.dit.petapp.services.EmailService;
import gr.hua.dit.petapp.entities.Citizen;
import gr.hua.dit.petapp.services.CitizenServices;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/citizens")
public class CitizenController {
    private  CitizenServices citizenService;

    public CitizenController(CitizenServices citizenService) {
        this.citizenService = citizenService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<?> getAllCitizens() {
        List<Citizen> citizens = citizenService.getAllCitizens();
        if(citizens.isEmpty()) {
            return ResponseEntity.ok(new MessageResponse("No citizens found"));
        }
        return ResponseEntity.ok(citizens);
    }

    @PreAuthorize("hasRole('VET') or hasRole('ADMIN') or hasRole('SHELTER')")
    @GetMapping("/{id}")
    public ResponseEntity<?> getCitizenById(@PathVariable Integer id) {
        try{
            Citizen citizen = citizenService.getCitizenById(id);
            return ResponseEntity.ok(citizen);
        }catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCitizen(@PathVariable Integer id) {
        try
        {
            citizenService.deleteCitizen(id);
            return ResponseEntity.ok(new MessageResponse("Successfully deleted citizen"));
        }catch (IllegalArgumentException e)
        {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

    @PreAuthorize("hasRole('CITIZEN')")
    @PostMapping
    public ResponseEntity<?> addCitizen(@RequestBody Citizen citizen) {
        try {
            citizenService.saveCitizen(citizen);
            return ResponseEntity.ok(new MessageResponse("Successfully added citizen"));
        } catch (EmailAlreadyExistsException ex) {
            return ResponseEntity.badRequest().body(new MessageResponse(ex.getMessage()));
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update-citizen/{id}")
    public ResponseEntity<?> updateCitizenProfile(@PathVariable Integer id, @RequestBody Citizen citizenDetails) {
        try {
            citizenService.updateCitizen(id, citizenDetails);
            return ResponseEntity.ok(new MessageResponse("Citizen profile updated successfully!"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }
}

