package gr.hua.dit.petapp.controllers;

import gr.hua.dit.petapp.entities.AdoptionRequest;
import gr.hua.dit.petapp.services.AdoptionRequestService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/adoption_requests")
public class AdoptionRequestController
{
    private final AdoptionRequestService adoptionRequestService;

    public AdoptionRequestController(AdoptionRequestService adoptionRequestService)
    {
        this.adoptionRequestService = adoptionRequestService;
    }
    @PreAuthorize("hasRole('ADMIN') or hasRole('SHELTER')")
    @GetMapping
    public List<AdoptionRequest> showAdoptionRequests()
    {
        List<AdoptionRequest> adoptionRequests = adoptionRequestService.getAllAdoptionRequests();
        return adoptionRequests;
    }
    @PreAuthorize("hasRole('ADMIN') or hasRole('SHELTER')")
    @GetMapping("/{id}")
    public AdoptionRequest showAdoptionRequest(@PathVariable Integer id)
    {
        AdoptionRequest adoptionRequest = adoptionRequestService.getAdoptionRequest(id);
        return adoptionRequest;
    }
    @PreAuthorize("hasRole('CITIZEN')")
    @PostMapping
    public void addAdoptionRequest(@RequestBody AdoptionRequest adoptionRequest)
    {
        adoptionRequestService.saveAdoptionRequest(adoptionRequest);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public void deleteAdoptionRequest(@PathVariable Integer id)
    {
        adoptionRequestService.deleteAdoptionRequest(id);
    }
}
