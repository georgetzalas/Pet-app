package gr.hua.dit.petapp.services;
import gr.hua.dit.petapp.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.mail.SimpleMailMessage;
import gr.hua.dit.petapp.repositories.EmailRepository;
@Service
public class EmailService {
    @Autowired
    UserRepository userRepository;
    private  JavaMailSender mailSender;

    @Autowired
    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Transactional
    public void sendEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);
    }

    @Transactional
    public void sendVisitRequestEmail(String citizenName, String shelterEmail) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(shelterEmail); // Email καταφυγίου
        message.setSubject("Αίτημα Επίσκεψης από Πολίτη");

        // Προκαθορισμένο μήνυμα
        String text = "Ο πολίτης " + citizenName + " εκφράζει ενδιαφέρον για επίσκεψη στο χώρο σας. " +
                "\nΠαρακαλώ επικοινωνήστε μαζί του για να κανονιστεί η επίσκεψη.\n" +
                "\nΜε εκτίμηση,\nΗ Ομάδα PetApp";

        message.setText(text);
        mailSender.send(message);
    }

    @Transactional
    public void sendDeletionNotification(String email, String message) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(email);
        mailMessage.setSubject("Account Deletion Notification");
        mailMessage.setText(message);
        mailSender.send(mailMessage);
    }

    @Transactional
    public void sendRequestVisit(long id ){
        String name = userRepository.findById(id).get().getName();
        String email = userRepository.findById(id).get().getEmail();
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(email);
        mailMessage.setSubject("Request visit");
        mailMessage.setText("The Citizen with name: "+ name +"has requested to pay you a visit !");
        mailSender.send(mailMessage);
    }
    @Transactional
    public void ShelterProgram(long id ){
        String name = userRepository.findById(id).get().getName();
        String email = userRepository.findById(id).get().getEmail();
        String day="Mondey";
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(email);
        mailMessage.setSubject("Visiting Program");
        mailMessage.setText("The Shelter with name: "+ name +"can welcome you at this "+day+"!");
        mailSender.send(mailMessage);
    }
}
