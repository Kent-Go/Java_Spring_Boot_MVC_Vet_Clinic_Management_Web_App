package au.edu.rmit.sept.webapp.models;

import java.time.LocalDate;

import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Entity
@Table(name = "immunisation_history")
public class ImmunisationHistory {
    // Primary key - ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    // Name of immunisation
    @Column(name = "name")
    private String name;

    // Date of immunisation
    @Column(name = "date")
    private LocalDate date;

    // Extra notes
    @Column(name = "notes")
    private String notes;

    // Foreign key - Pet ID
    @Column(name = "pet_id")
    private int petID;

    @ManyToOne
    @JoinColumn(name = "pet_id", insertable = false, updatable = false)
    private Pet pet;

    // Getters and Setters
    // ID
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // Name
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Date
    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    // Notes
    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    // Pet ID
    public int getPetID() {
        return petID;
    }

    public void setPetID(int petID) {
        this.petID = petID;
    }

    // Pet
    public Pet getPet() {
        return pet;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }
}