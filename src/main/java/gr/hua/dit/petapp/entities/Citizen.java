package gr.hua.dit.petapp.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import java.util.List;

@Entity
//@JsonIdentityInfo(generator= ObjectIdGenerators.IntSequenceGenerator.class, property="@id")
public class Citizen extends User{
    @Column
    private String surname;

    @OneToMany(mappedBy = "citizen")
    //@JsonIgnoreProperties("citizen")
    private List<AdoptionRequest> adoptionRequestList;

    @OneToMany(mappedBy = "citizen")
    //@JsonManagedReference
    //@JsonIgnoreProperties("citizen")
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
