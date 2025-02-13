package gr.hua.dit.petapp.controllers;

import gr.hua.dit.petapp.payload.response.MessageResponse;
import gr.hua.dit.petapp.services.EmailService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/email")
public class EmailController {
    EmailService emailService;

    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PreAuthorize("hasRole('CITIZEN')")
    @PostMapping("/send-request/{id}/{email}")
    public void sendRequestVisit(@PathVariable Long id, @PathVariable String email) {
        emailService.sendVisitRequestEmail(id, email);
    }

    @PreAuthorize("hasRole('SHELTER')")
    @PostMapping("/send-program/{email}")
    public void ShelterProgram(@PathVariable String email){
        emailService.ShelterProgram(email);
    }

    /*@PreAuthorize("hasRole('CITIZEN')")
    @PostMapping("/sendVisitRequest")
    public ResponseEntity<?> sendVisitRequest(@RequestParam String citizenName, @RequestParam String shelterEmail) {
        emailService.sendVisitRequestEmail(citizenName, shelterEmail);
        return ResponseEntity.ok(new MessageResponse("Το email εστάλη επιτυχώς στο καταφύγιο: " + shelterEmail));
    }*/
}
