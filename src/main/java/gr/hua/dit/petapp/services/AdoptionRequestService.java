package gr.hua.dit.petapp.services;

import gr.hua.dit.petapp.entities.AdoptionRequest;
import gr.hua.dit.petapp.entities.AdoptionStatus;
import gr.hua.dit.petapp.entities.Citizen;
import gr.hua.dit.petapp.entities.Pet;
import gr.hua.dit.petapp.repositories.AdoptionRequestRepository;
import gr.hua.dit.petapp.repositories.PetRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class AdoptionRequestService {

    private AdoptionRequestRepository adoptionRequestRepository;
    private PetRepository petRepository;
    private EmailService emailService;
    public AdoptionRequestService(AdoptionRequestRepository adoptionRequestRepository, PetRepository petRepository, EmailService emailService)
    {
        this.adoptionRequestRepository = adoptionRequestRepository;
        this.petRepository = petRepository;
        this.emailService = emailService;
    }

    // List of valid statuses
    private final List<String> validStatuses = Arrays.asList("PENDING", "APPROVED", "REJECTED");
    @Transactional
    // Method to filter adoption requests by status
    public List<AdoptionRequest> getAdoptionRequestsByStatus(AdoptionStatus status) {
        // Validate the status
        if (!validStatuses.contains(status)) {
            throw new IllegalArgumentException("Invalid status: " + status);
        }

        // Fetch filtered adoption requests
        return adoptionRequestRepository.findByStatus(status);
    }

    @Transactional
    // Method to get all adoption requests
    public List<AdoptionRequest> getAllAdoptionRequests() {
        return adoptionRequestRepository.findAll();
    }

    @Transactional
    public AdoptionRequest getAdoptionRequest(Integer id)
    {
        AdoptionRequest adoptionRequest = adoptionRequestRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid id: " + id));
        return adoptionRequest;
    }

    @Transactional
    public void saveAdoptionRequest(AdoptionRequest adoptionRequest)
    {
        Pet pet = adoptionRequest.getPet();
        Citizen citizen = adoptionRequest.getCitizen();

        adoptionRequest.setCitizen(citizen);
        adoptionRequest.setPet(pet);

        adoptionRequestRepository.save(adoptionRequest);
    }

    @Transactional
    public void deleteAdoptionRequest(Integer id)
    {
        AdoptionRequest adoptionRequest = adoptionRequestRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid id: " + id));
        adoptionRequestRepository.delete(adoptionRequest);
    }

    @Transactional
    public void adoptionStatusApprove(Integer id)
    {
        AdoptionRequest adoptionRequest = adoptionRequestRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid id: " + id));
        adoptionRequest.setStatus(AdoptionStatus.APPROVED);
        List<AdoptionRequest> allAdoptionRequests = adoptionRequestRepository.findAll();
        List<AdoptionRequest> notApproved = new ArrayList<>();
        for(AdoptionRequest a : allAdoptionRequests) {
            if(a.getPet().equals(adoptionRequest.getPet()) && !a.equals(adoptionRequest))
            {
                notApproved.add(a);
            }
        }
        for(AdoptionRequest a : notApproved) {
            emailService.adoptionRejected(a.getCitizen().getEmail());
        }
        emailService.adoptionApproved(adoptionRequest.getCitizen().getEmail());
    }

    @Transactional
    public void adoptionStatusReject(Integer id)
    {
        AdoptionRequest adoptionRequest = adoptionRequestRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid id: " + id));
        adoptionRequest.setStatus(AdoptionStatus.REJECTED);
        emailService.adoptionRejected(adoptionRequest.getCitizen().getEmail());
        //deleteAdoptionRequest(adoptionRequest.getId().intValue());
    }

}
