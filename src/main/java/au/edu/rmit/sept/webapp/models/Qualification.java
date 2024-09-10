package au.edu.rmit.sept.webapp.models;

import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Entity
@Table(name = "qualification")
public class Qualification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "university")
    private String university;

    @Column(name = "country")
    private String country;

    @Column(name = "year")
    private int year;

    @Column(name = "vet_id")
    private int vetID;

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

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getVetID() {
        return vetID;
    }

    public void setVetID(int vetID) {
        this.vetID = vetID;
    }

    // Default no-argument constructor required by JPA
    public Qualification() {
    }

    // Constructor without ID because it is auto-generated
    public Qualification(String name, String university, String country, int year, int vetID) {
        this.name = name;
        this.university = university;
        this.country = country;
        this.year = year;
        this.vetID = vetID;
    }
}
