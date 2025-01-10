package gr.hua.dit.petapp.controllers;

import gr.hua.dit.petapp.entities.MedicalHistory;
import gr.hua.dit.petapp.services.MedicalHistoryService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/medical_history")
public class MedicalHistoryController
{
    private final MedicalHistoryService medicalHistoryService;

    public MedicalHistoryController(MedicalHistoryService medicalHistoryService)
    {
        this.medicalHistoryService = medicalHistoryService;
    }

    @PreAuthorize("hasRole('VET')")
    @GetMapping
    public List<MedicalHistory> showMedicalHistories()
    {
        List<MedicalHistory> medicalHistoryList = medicalHistoryService.getMedicalHistories();
        return medicalHistoryList;
    }

    @PreAuthorize("hasRole('VET')")
    @GetMapping("/{id}")
    public MedicalHistory showMedicalHistory(@PathVariable Long id)
    {
        MedicalHistory medicalHistory = medicalHistoryService.getMedicalHistory(id);
        return medicalHistory;
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
}
