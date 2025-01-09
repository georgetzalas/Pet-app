package gr.hua.dit.petapp.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;

import java.util.List;

@Entity
@JsonIdentityInfo(generator= ObjectIdGenerators.IntSequenceGenerator.class, property="@id")
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String name;
    @Column
    private String type;
    @Column
    private int healthStatus;
    @Column
    private String adoptionStatus;
    @Column
    private int age;
    @Column
    private String picture;
    @Column
    private float weight;
    @Column
    private float height ;
    @Column
    private String breed;
    @Column
    private String sex;
    //@Column
    //private String Shelter;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "citizenid")
    private Citizen citizen;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name="vetid")
    private Vet vet;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "shelterid")
    private Shelter shelter;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="historyid")
    private MedicalHistory medicalHistory;

    /*@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="requestid")
    private AdoptionRequest adoptionRequest;*/

    @Enumerated(EnumType.STRING)
    private ApprovalStatus status;

    public Pet(){

    }

    public Pet(Citizen citizen,Vet vet,MedicalHistory medicalHistory, /*AdoptionRequest adoptionRequest,*/ApprovalStatus status){
        this.citizen = citizen;
        this.vet = vet;
        this.medicalHistory = medicalHistory;
        //this.adoptionRequest = adoptionRequest;
        this.status = status;
    }

    public Long getID() {
        return id;
    }

    public void setID(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getHealthStatus() {
        return healthStatus;
    }

    public void setHealthStatus(int healthStatus) {
        this.healthStatus = healthStatus;
    }

    public String getAdoptionStatus() {
        return adoptionStatus;
    }

    public void setAdoptionStatus(String adoptionStatus) {
        this.adoptionStatus = adoptionStatus;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    /*public String getShelter() {
        return Shelter;
    }

    public void setShelter(String shelter) {
        Shelter = shelter;
    }*/

    public ApprovalStatus getStatus() {
        return status;
    }

    public void setStatus(ApprovalStatus status) {
        this.status = status;
    }

    public Citizen getCitizen() {
        return citizen;
    }

    public void setCitizen(Citizen citizen) {
        this.citizen = citizen;
    }

    public Vet getVet() {
        return vet;
    }

    public void setVet(Vet vet) {
        this.vet = vet;
    }

    public void setShelter(Shelter shelter) {
        this.shelter = shelter;
    }

    public Shelter getShelter() {
        return shelter;
    }

    public MedicalHistory getMedicalHistory() {
        return medicalHistory;
    }

    public void setMedicalHistory(MedicalHistory medicalHistory) {
        this.medicalHistory = medicalHistory;
    }

    /*public AdoptionRequest getAdoptionRequest() {
        return adoptionRequest;
    }

    public void setAdoptionRequest(AdoptionRequest adoptionRequest) {
        this.adoptionRequest = adoptionRequest;
    }*/

    @Override
    public String toString() {
        return "Pet{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", healthStatus=" + healthStatus +
                ", adoptionStatus='" + adoptionStatus + '\'' +
                ", age=" + age +
                ", picture='" + picture + '\'' +
                ", weight=" + weight +
                ", height=" + height +
                ", breed='" + breed + '\'' +
                ", sex='" + sex + '\'' +
                //", Shelter='" + Shelter + '\'' +
                ", citizen=" + citizen +
                ", vet=" + vet +
                ", shelter=" + shelter +
                ", medicalHistory=" + medicalHistory +
                ", status=" + status +
                '}';
    }
}
