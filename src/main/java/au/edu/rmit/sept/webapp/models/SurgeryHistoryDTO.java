package au.edu.rmit.sept.webapp.models;

import java.time.LocalDate;

public class SurgeryHistoryDTO {

    private int id;
    private String name;
    private LocalDate date;
    private String notes;
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