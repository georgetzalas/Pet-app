package gr.hua.dit.petapp.entities;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
//@JsonIdentityInfo(generator= ObjectIdGenerators.IntSequenceGenerator.class, property="@id")
public class MedicalHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "vetid")
    @JsonIgnoreProperties({"vet", "medicalHistory"})
    private Vet vet;

    @OneToOne
    @JoinColumn(name = "petid")
    @JsonIgnoreProperties({"pet", "medicalHistory"})
    private Pet pet;

    @Enumerated(EnumType.STRING)
    private HealthStatus healthStatus = HealthStatus.NOT_SUMBITED;

    @Column
    private String treatment;

    @Column
    private String vetNotes;

    @Column
    private LocalDate date;

    public MedicalHistory() {
    }

    public MedicalHistory(Vet vet, Pet pet) {
        this.vet = vet;
        this.pet = pet;
    }

    public Long getID() {
        return id;
    }

    public void setID(Long id) {
        this.id = id;
    }

    public Vet getVet() {
        return vet;
    }

    public void setVet(Vet vet) {
        this.vet = vet;
    }

    public Pet getPet() {
        return pet;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }

    public HealthStatus getHealthStatus() {
        return healthStatus;
    }

    public void setHealthStatus(HealthStatus healthStatus) {
        this.healthStatus = healthStatus;
    }

    public String getTreatment() {
        return treatment;
    }

    public void setTreatment(String treatment) {
        this.treatment = treatment;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getVetNotes() {
        return vetNotes;
    }

    public void setVetNotes(String vetNotes) {
        this.vetNotes = vetNotes;
    }

    @Override
    public String toString() {
        return "MedicalHistory{" +
                "id=" + id +
                ", vet=" + vet +
                ", pet=" + pet +
                '}';
    }
}
