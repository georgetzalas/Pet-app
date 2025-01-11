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

    private Set<String> role;

    @NotBlank(message = "Password cannot be blank")
    @Size(min = 6, max = 40)
    private String password;

    //@RoleBasedField(roleField = "role", targetField = "surname", validRoles = {"user", "vet","admin"}, message = "Surname is required for user,vet and admin roles")
    //private String surname;

    //@RoleBasedField(roleField = "role", targetField = "region", validRoles = {"shelter"}, message = "Region is required for shelter role")
    //private String region;

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

    public Set<String> getRole() {
        return this.role;
    }

    public void setRole(Set<String> role) {
        this.role = role;
    }

    /*public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }*/

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /*public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }*/
}

