package gr.hua.dit.petapp.controllers;

import gr.hua.dit.petapp.entities.AdoptionRequest;
import org.springframework.http.ResponseEntity;
import gr.hua.dit.petapp.payload.response.MessageResponse;
import gr.hua.dit.petapp.services.AdoptionRequestService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/adoption-requests")
public class AdoptionRequestController
{
    private final AdoptionRequestService adoptionRequestService;

    public AdoptionRequestController(AdoptionRequestService adoptionRequestService)
    {
        this.adoptionRequestService = adoptionRequestService;
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('SHELTER')")
    @GetMapping
    public ResponseEntity<?> showAdoptionRequests()
    {
        List<AdoptionRequest> adoptionRequests = adoptionRequestService.getAllAdoptionRequests();
        if(adoptionRequests.isEmpty())
        {
            return ResponseEntity.ok(new MessageResponse("No adoption requests"));
        }
        return ResponseEntity.ok(adoptionRequests);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('SHELTER')")
    @GetMapping("/{id}")
    public ResponseEntity<?> showAdoptionRequest(@PathVariable Integer id)
    {
        try {
            AdoptionRequest adoptionRequest = adoptionRequestService.getAdoptionRequest(id);
            return ResponseEntity.ok(adoptionRequest);
        }catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

    @PreAuthorize("hasRole('CITIZEN')")
    @PostMapping
    public void addAdoptionRequest(@RequestBody AdoptionRequest adoptionRequest)
    {
        adoptionRequestService.saveAdoptionRequest(adoptionRequest);
    }

    @PreAuthorize("hasRole('SHELTER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAdoptionRequest(@PathVariable Integer id)
    {
        try {
            AdoptionRequest adoptionRequest = adoptionRequestService.getAdoptionRequest(id);
            adoptionRequestService.deleteAdoptionRequest(id);
            return ResponseEntity.ok(new MessageResponse("Adoption Request deleted"));
        }catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

    @PreAuthorize("hasRole('SHELTER')")
    @PostMapping("/change-status-approve/{id}")
    public ResponseEntity<?> adoptionStatusApprove(@PathVariable Integer id)
    {
        try
        {
            adoptionRequestService.adoptionStatusApprove(id);
            return ResponseEntity.ok(new MessageResponse("Adoption Request approved"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

    @PreAuthorize("hasRole('SHELTER')")
    @PostMapping("/change-status-reject/{id}")
    public ResponseEntity<?> adoptionStatusReject(@PathVariable Integer id)
    {
        try
        {
            adoptionRequestService.adoptionStatusReject(id);
            return ResponseEntity.ok(new MessageResponse("Adoption Request rejected"));
        }catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }
}
