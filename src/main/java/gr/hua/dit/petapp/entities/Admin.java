package gr.hua.dit.petapp.entities;

import jakarta.persistence.*;

@Entity
public class Admin extends User {

    /*@OneToMany
    @JoinColumn(name="petid")
    private List<Pet> pet;

    @OneToMany
    @JoinColumn(name="shelterid")
    private List<Shelter> shelters;

    @OneToMany
    @JoinColumn(name = "adoptionrequestid")
    private List<AdoptionRequest> adoptionRequest;*/

    @Column
    private String surname;

    public Admin(String name, String username, String email, String password, String surname)
    {
        super(name, username, email, password);
        this.surname = surname;
    }

    public Admin(){}

    public Admin(String surname)
    {
        this.surname = surname;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    /*public List<Pet> getPet() {
        return pet;
    }

    public void setPet(List<Pet> pet) {
        this.pet = pet;
    }

    public List<AdoptionRequest> getAdoptionRequest() {
        return adoptionRequest;
    }

    public void setAdoptionRequest(List<AdoptionRequest> adoptionRequest) {
        this.adoptionRequest = adoptionRequest;
    }

    public List<Shelter> getShelters() {
        return shelters;
    }

    public void setShelters(List<Shelter> shelters) {
        this.shelters = shelters;
    }*/
}
