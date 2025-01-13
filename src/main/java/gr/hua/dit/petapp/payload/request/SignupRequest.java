package gr.hua.dit.petapp.payload.request;

//import gr.hua.dit.petapp.validation.RoleBasedField;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import java.util.Set;

public class SignupRequest {

    @NotBlank(message = "Name cannot be blank")
    @Size(min = 3, max = 20)
    private String name;

    @NotBlank(message = "Username cannot be blank")
    @Size(min = 3, max = 20)
    private String username;

    @NotBlank(message = "Email cannot be blank")
    @Size(max = 50)
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Password cannot be blank")
    @Size(min = 6, max = 40)
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

