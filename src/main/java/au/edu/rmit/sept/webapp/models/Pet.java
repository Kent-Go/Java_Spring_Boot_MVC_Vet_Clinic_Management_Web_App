package au.edu.rmit.sept.webapp.models;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "pet")
public class Pet {
    //Primary key - Pet ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    //Pet name
    @Column(name = "name")
    private String name;

    //Birth date
    @Column(name = "birth_date")
    private LocalDate birthDate;

    //Species
    @Column(name = "species")
    private String species;

    //Breed
    @Column(name = "breed")
    private String breed;

    //Gender
    @Column(name = "gender")
    private String gender;

    //Weight
    @Column(name = "weight")
    private float weight;

    //Foreign key - Pet Owner ID
    @Column(name = "pet_owner_id")
    private int petOwnerID;

    @ManyToOne
    @JoinColumn(name = "pet_owner_id", insertable = false, updatable = false)
    private PetOwner petOwner;

    //Getters and Setters
    //Pet ID
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    //Name
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    //Birth Date
    public LocalDate getBirthDate(){
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate){
        this.birthDate = birthDate;
    }

    //Species
    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    //Breed
    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    //Gender
    public String getGender(){
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    //Weight
    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    //Pet Owner ID
    public int getPetOwnerID() {
        return petOwnerID;
    }

    public void setPetOwnerID(int petOwnerID) {
        this.petOwnerID = petOwnerID;
    }

    // Set Pet Owner entity
    public PetOwner getPetOwner() {
        return petOwner;
    }

    // Get Pet Owner entity
    public void setPetOwner(PetOwner petOwner) {
        this.petOwner = petOwner;
    }

    // Constructors
    public Pet() {
    }

    public Pet(String name, LocalDate birthDate, String species, String breed, String gender, float weight, int petOwnerID) {
        this.name = name;
        this.birthDate = birthDate;
        this.species = species;
        this.breed = breed;
        this.gender = gender;
        this.weight = weight;
        this.petOwnerID = petOwnerID;
    }
}
