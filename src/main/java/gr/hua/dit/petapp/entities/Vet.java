package gr.hua.dit.petapp.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
@Entity
public class Vet extends User {
    /*@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private long id;*/

    @OneToMany(mappedBy = "vett")
    //@JoinColumn(name="petid")
    private List<Pet> pet;

    /*@OneToMany
    @JoinColumn(name="adminid")
    private List<Admin> admin;*/

    @OneToMany(mappedBy = "vet")
    //@JoinColumn(name="historyid")
    private List<MedicalHistory> medicalHistory;

    @OneToMany
    @JoinColumn(name="requestid")
    private List<AdoptionRequest> adoptionRequest;
    @Column
    private String Surname;

    public Vet(){

    }

    public Vet(List<Pet> pet,/*List<Admin> admin,*/List<MedicalHistory> medicalHistory,List<AdoptionRequest> adoptionRequest){
        this.pet=pet;
        this.medicalHistory=medicalHistory;
        this.adoptionRequest=adoptionRequest;
    }

    public Vet(String Surname){
        this.Surname = Surname;
    }

    @Enumerated(EnumType.STRING)
    private AccountStatus status = AccountStatus.PENDING; // Default to PENDING


    public String getSurname() {
        return Surname;
    }

    public void setSurname(String surname) {
        Surname = surname;
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

    public List<MedicalHistory> getMedicalHistory() {
        return medicalHistory;
    }

    public void setMedicalHistory(List<MedicalHistory> medicalHistory) {
        this.medicalHistory = medicalHistory;
    }

    public List<AdoptionRequest> getAdoptionRequest() {
        return adoptionRequest;
    }

    public void setAdoptionRequest(List<AdoptionRequest> adoptionRequest) {
        this.adoptionRequest = adoptionRequest;
    }
}
