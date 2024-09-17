package au.edu.rmit.sept.webapp.models;

import java.time.LocalDate;
import java.time.Period;

import jakarta.persistence.*;

@Entity
@Table(name = "pet")
public class Pet {
    // Primary key - Pet ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    // Pet name
    @Column(name = "name")
    private String name;

    // Birth date
    @Column(name = "birth_date")
    private LocalDate birthDate;

    // Species
    @Column(name = "species")
    private String species;

    // Breed
    @Column(name = "breed")
    private String breed;

    // Gender
    @Column(name = "gender")
    private String gender;

    // Weight
    @Column(name = "weight")
    private float weight;

    // Foreign key - Pet Owner ID
    @Column(name = "pet_owner_id")
    private int petOwnerID;

    @ManyToOne
    @JoinColumn(name = "pet_owner_id", insertable = false, updatable = false)
    private PetOwner petOwner;

    // Calculated field for age
    @Transient
    private int age;

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
        this.age = calculateAge();
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public int getPetOwnerID() {
        return petOwnerID;
    }

    public void setPetOwnerID(int petOwnerID) {
        this.petOwnerID = petOwnerID;
    }

    public PetOwner getPetOwner() {
        return petOwner;
    }

    public void setPetOwner(PetOwner petOwner) {
        this.petOwner = petOwner;
    }

    public int getAge() {
        return age;
    }

    // Constructor
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
        this.age = calculateAge();  // Calculate age at instantiation
    }

    // Helper method to calculate age
    private int calculateAge() {
        if (birthDate != null) {
            return Period.between(birthDate, LocalDate.now()).getYears();
        }
        return 0;
    }
}
