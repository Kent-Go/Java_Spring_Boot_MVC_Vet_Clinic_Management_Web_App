package au.edu.rmit.sept.webapp.models;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDate;

@Entity
@Table(name = "surgery_history")
public class SurgeryHistory {
    //Primary key - ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    //Name of surgery
    @Column(name = "name")
    private String name;

    //Date of surgery
    @Column(name = "date")
    private LocalDate date;

    //Extra notes
    @Column(name = "notes")
    private String notes;

    //Foreign key - Pet ID
    @Column(name = "pet_id")
    private int petID;

    // Getters and Setters
    //ID
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

    //Date
    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date){
        this.date = date;
    }

    //Notes
    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    //Pet ID
    public int getPetID() {
        return petID;
    }

    public void setPetID(int petID) {
        this.petID = petID;
    }
}