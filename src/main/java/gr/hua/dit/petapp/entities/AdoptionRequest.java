package gr.hua.dit.petapp.entities;

import jakarta.persistence.*;

@Entity
public class AdoptionRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "petid")
    private Pet pet;

    @ManyToOne
    @JoinColumn(name = "citizenid")
    private Citizen citizen;

    @Column
    private String status;

    public AdoptionRequest() {
    }

    public AdoptionRequest(Pet pet, String status, Citizen citizen) {
        this.pet = pet;
        this.status = status;
        this.citizen = citizen;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Pet getPet() {
        return pet;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }

    public Citizen getCitizen() {
        return citizen;
    }

    public void setCitizen(Citizen citizen) {
        this.citizen = citizen;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
