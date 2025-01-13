package gr.hua.dit.petapp.controllers;

import gr.hua.dit.petapp.services.EmailService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/email")
public class EmailController {
    EmailService emailService;

    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/send-request")
    public void sendRequestVisit(@RequestParam long id){
        emailService.sendRequestVisit(id);
    }
    @PostMapping("/send-program")
    public void ShelterProgram(@RequestParam long id){
        emailService.ShelterProgram(id);
    }

}
