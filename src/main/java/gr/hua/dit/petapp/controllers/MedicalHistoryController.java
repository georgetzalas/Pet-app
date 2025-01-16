package gr.hua.dit.petapp.controllers;

import gr.hua.dit.petapp.entities.MedicalHistory;
import gr.hua.dit.petapp.payload.response.MessageResponse;
import gr.hua.dit.petapp.services.MedicalHistoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/medical-history")
public class MedicalHistoryController
{
    private final MedicalHistoryService medicalHistoryService;

    public MedicalHistoryController(MedicalHistoryService medicalHistoryService)
    {
        this.medicalHistoryService = medicalHistoryService;
    }

    @PreAuthorize("hasRole('VET')")
    @GetMapping
    public ResponseEntity<?> showMedicalHistories()
    {
        List<MedicalHistory> medicalHistoryList = medicalHistoryService.getMedicalHistories();
        if(medicalHistoryList.isEmpty())
        {
            return ResponseEntity.ok(new MessageResponse("No medical history found"));
        }
        return ResponseEntity.ok(medicalHistoryList);
    }

    @PreAuthorize("hasRole('VET')")
    @GetMapping("/{id}")
    public ResponseEntity<?> showMedicalHistory(@PathVariable Long id)
    {
        try
        {
            MedicalHistory medicalHistory = medicalHistoryService.getMedicalHistory(id);
            return ResponseEntity.ok(medicalHistory);
        }catch(IllegalArgumentException e)
        {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

    @PreAuthorize("hasRole('VET')")
    @PostMapping("/{id}")
    public void addMedicalHistory(@PathVariable Long id, @RequestBody MedicalHistory medicalHistory)
    {
        medicalHistoryService.saveMedicalHistory(id, medicalHistory);
    }

    @PreAuthorize("hasRole('VET')")
    @PostMapping
    public void addMedicalHistory(@RequestBody MedicalHistory medicalHistory)
    {
        medicalHistoryService.saveMedicalHistory(medicalHistory);
    }

    @PreAuthorize("hasRole('VET')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMedicalHistory(@PathVariable Long id) {
        try {
            medicalHistoryService.deleteMedicalHistory(id);
            return ResponseEntity.ok(new MessageResponse("Deleted medical history"));
        } catch (IllegalArgumentException e)
        {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }
}
