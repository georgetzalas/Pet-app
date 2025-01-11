package gr.hua.dit.petapp.payload.request;

import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import java.util.Set;

public class SignupRequestShelter extends SignupRequest {
    @NotBlank(message = "Name cannot be blank")
    @Size(min = 3, max = 20)
    private String region;

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }
}
