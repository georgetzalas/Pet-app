package gr.hua.dit.petapp.payload.request;

import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import java.util.Set;

public class SignupRequestVet extends SignupRequest {

    @NotBlank(message = "Name cannot be blank")
    @Size(min = 3, max = 20)
    private String surname;

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }
}
