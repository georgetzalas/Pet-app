package gr.hua.dit.petapp.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
    private String adoptionStatus;
    @Column
    private float age;
    /*@Lob
    @Basic(fetch = FetchType.LAZY)
    @JsonIgnore
    private byte[] picture;*/
    @Column
    private String strPicture;
    @Column
    private float weight;
    @Column
    private float height;
    @Column
    private String breed;
    @Column
    private String sex;
    //@Column
    //private String Shelter;
    @Enumerated(EnumType.STRING)
    private ApprovalStatus adminApprovalStatus = ApprovalStatus.PENDING;

    @Enumerated(EnumType.STRING)
    private ApprovalStatus vetApprovalStatus = ApprovalStatus.PENDING;

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

    public Pet(){

    }

    public Pet(String name, String type, String adoptionStatus, float age, float weight, /*byte[] picture,*/ float height, String breed, String sex, ApprovalStatus adminApprovalStatus, ApprovalStatus vetApprovalStatus, MedicalHistory medicalHistory, Shelter shelter, Vet vet, Citizen citizen) {
        this.name = name;
        this.type = type;
        this.adoptionStatus = adoptionStatus;
        this.age = age;
        this.weight = weight;
        //this.picture = picture;
        this.height = height;
        this.breed = breed;
        this.sex = sex;
        this.adminApprovalStatus = adminApprovalStatus;
        this.vetApprovalStatus = vetApprovalStatus;
        this.medicalHistory = medicalHistory;
        this.shelter = shelter;
        this.vet = vet;
        this.citizen = citizen;
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

    public String getAdoptionStatus() {
        return adoptionStatus;
    }

    public void setAdoptionStatus(String adoptionStatus) {
        this.adoptionStatus = adoptionStatus;
    }

    public float getAge() {
        return age;
    }

    public void setAge(float age) {
        this.age = age;
    }

    /*public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }*/

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

    public ApprovalStatus getAdminApprovalStatus() {
        return adminApprovalStatus;
    }

    public ApprovalStatus getVetApprovalStatus() {
        return vetApprovalStatus;
    }

    public void setAdminApprovalStatus(ApprovalStatus adminApprovalStatus) {
        this.adminApprovalStatus = adminApprovalStatus;
    }

    public void setVetApprovalStatus(ApprovalStatus vetApprovalStatus) {
        this.vetApprovalStatus = vetApprovalStatus;
    }

    public String getStrPicture() {
        return strPicture;
    }

    public void setStrPicture(String strPicture) {
        this.strPicture = strPicture;
    }

    @Override
    public String toString() {
        return "Pet{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", adoptionStatus='" + adoptionStatus + '\'' +
                ", age=" + age +
                //", picture='" + picture + '\'' +
                ", weight=" + weight +
                ", height=" + height +
                ", breed='" + breed + '\'' +
                ", sex='" + sex + '\'' +
                ", adminApprovalStatus=" + adminApprovalStatus +
                ", vetApprovalStatus=" + vetApprovalStatus +
                ", citizen=" + citizen +
                ", vet=" + vet +
                ", shelter=" + shelter +
                ", medicalHistory=" + medicalHistory +
                '}';
    }
}
