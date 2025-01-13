package gr.hua.dit.petapp.entities;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;

@Entity
@JsonIdentityInfo(generator= ObjectIdGenerators.IntSequenceGenerator.class, property="@id")
public class Shelter extends User{
    @Enumerated(EnumType.STRING)
    private AccountStatus status = AccountStatus.PENDING;

    @OneToMany(mappedBy = "shelter")
    private List<Pet> pet;

    @Column
    private String Region;

    public Shelter(String name, String username, String email, String password, String region)
    {
        super(name, username, email, password);
        this.Region = region;
    }

    public Shelter(){

    }

    public Shelter(List<Pet> pet, String Region,AccountStatus status) {
        this.pet = pet;
        this.Region = Region;
        this.status = status;
    }

    public Shelter(String Region)
    {
        this.Region = Region;
    }

    public String getRegion() {
        return Region;
    }

    public void setRegion(String region) {
        Region = region;
    }

    public AccountStatus getStatus() {
        return status;
    }

    public void setStatus(AccountStatus status) {
        this.status = status;
    }

    public List<Pet> getPet() {
        return pet;
    }

    public void setPet(List<Pet> pet) {
        this.pet = pet;
    }
}
