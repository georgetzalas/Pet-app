package gr.hua.dit.petapp.entities;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;

@Entity
@JsonIdentityInfo(generator= ObjectIdGenerators.IntSequenceGenerator.class, property="@id")
public class MedicalHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "vetid")
    private Vet vet;

    @OneToOne
    @JoinColumn(name = "petid")
    private Pet pet;

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

    @Override
    public String toString() {
        return "MedicalHistory{" +
                "id=" + id +
                ", vet=" + vet +
                ", pet=" + pet +
                '}';
    }
}
