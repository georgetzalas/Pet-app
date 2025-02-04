package gr.hua.dit.petapp.controllers;


import gr.hua.dit.petapp.entities.AdoptionRequest;
import gr.hua.dit.petapp.entities.User;
import gr.hua.dit.petapp.payload.response.MessageResponse;
import gr.hua.dit.petapp.services.AdoptionRequestService;
import gr.hua.dit.petapp.services.UserService;
import org.hibernate.dialect.unique.CreateTableUniqueDelegate;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService, AdoptionRequestService adoptionRequestService)
    {
        this.userService = userService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/{userType}/{id}/approve")
    public ResponseEntity<MessageResponse> approveAccount(@PathVariable String userType, @PathVariable Integer id) {
        try
        {
            userService.approveAccount(userType, id);
            return ResponseEntity.ok(new MessageResponse("Account approved successfully."));
        }catch (IllegalArgumentException e)
        {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/{userType}/{id}/reject")
    public ResponseEntity<MessageResponse> rejectAccount(@PathVariable String userType, @PathVariable Integer id,
                                                @RequestParam(required = false) String remarks) {
        try{
            userService.rejectAccount(userType, id, remarks);
            return ResponseEntity.ok(new MessageResponse("Account rejected successfully."));
        }catch (IllegalArgumentException e)
        {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    // Endpoint to fetch all accounts
    @GetMapping("/accounts")
    public ResponseEntity<?> getAllAccounts() {
        List<User> accounts = userService.getAllAccounts();
        if(accounts.isEmpty())
        {
            return ResponseEntity.ok(new MessageResponse("No accounts found."));
        }
        return ResponseEntity.ok(accounts);
    }

    @PreAuthorize("hasRole('ADMIN')")
    // Endpoint to fetch all accounts
    @GetMapping("/accounts/{id}")
    public ResponseEntity<?> getAllAccounts(@PathVariable Integer id) {
        User user = userService.getAccount(id);
        if(user == null)
        {
            return ResponseEntity.ok(new MessageResponse("No user found."));
        }
        return ResponseEntity.ok(user);
    }

    @PreAuthorize("hasRole('ADMIN')")
    // Endpoint to delete an account by email
    @DeleteMapping("/account/delete/{id}")
    public ResponseEntity<MessageResponse> deleteAccount(@PathVariable Integer id) {
        try {
            userService.deleteAccount(id);
            return ResponseEntity.ok(new MessageResponse("Account deleted successfully"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

    /*@PreAuthorize("hasRole('ADMIN')")
    // Endpoint to filter adoption requests by status
    @GetMapping("/adoption-requests")
    public ResponseEntity<List<AdoptionRequest>> getAdoptionRequests(@RequestParam(required = false) String status) {
        List<AdoptionRequest> adoptionRequests;

        if (status != null && !status.isEmpty()) {
            adoptionRequests = adoptionRequestService.getAdoptionRequestsByStatus(status);
        } else {
            adoptionRequests = adoptionRequestService.getAllAdoptionRequests();
        }

        return ResponseEntity.ok(adoptionRequests);
    }*/
}
