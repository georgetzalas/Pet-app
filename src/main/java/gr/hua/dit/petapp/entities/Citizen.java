package gr.hua.dit.petapp.entities;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import java.util.List;

@Entity
public class Citizen extends User{
    @Column
    private String surname;

    @OneToMany(mappedBy = "citizen")
    //@JsonIgnoreProperties("citizen")
    @JsonIgnore
    private List<AdoptionRequest> adoptionRequestList;

    @OneToMany(mappedBy = "citizen")
    //@JsonIgnoreProperties("citizen")
    @JsonIgnore
    private List<Pet> pet;

    @Enumerated(EnumType.STRING)
    private AccountStatus status = AccountStatus.PENDING;


    public Citizen(String name, String username, String email, String password, String surname)
    {
        super(name, username, email, password);
        this.surname = surname;
    }

    public Citizen(){}

    public Citizen(String surname,List<AdoptionRequest> adoptionRequestList,List<Pet> pet)
    {

        this.surname = surname;
        this.adoptionRequestList=adoptionRequestList;
        this.pet=pet;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public AccountStatus getStatus() {
        return status;
    }

    public void setStatus(AccountStatus status) {
        this.status = status;
    }

    public List<AdoptionRequest> getAdoptionRequestList() {
        return adoptionRequestList;
    }

    public void setAdoptionRequestList(List<AdoptionRequest> adoptionRequestList) {
        this.adoptionRequestList = adoptionRequestList;
    }

    public List<Pet> getPet() {
        return pet;
    }

    public void setPet(List<Pet> pet) {
        this.pet = pet;
    }
}