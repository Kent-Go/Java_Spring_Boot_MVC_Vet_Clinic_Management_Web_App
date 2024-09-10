package au.edu.rmit.sept.webapp.models;

//java sql or util date here?
import java.time.LocalDate;

public class PetDTO {

    private int id;
    private String name;
    private LocalDate birthDate;
    private String species;
    private String breed;
    private String gender;
    private float weight;
    private int petOwnerID;

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
}
