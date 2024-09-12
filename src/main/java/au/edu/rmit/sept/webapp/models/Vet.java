package au.edu.rmit.sept.webapp.models;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "vet")
public class Vet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "title")
    private String title;

    @Column(name = "languages_spoken")
    private String languagesSpoken;

    @Column(name = "self_description")
    private String selfDescription;

    @Column(name = "profile_picture")
    private byte[] profilePicture;

    @Column(name = "user_id")
    private int userID;

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLanguagesSpoken() {
        return languagesSpoken;
    }

    public void setLanguagesSpoken(String languagesSpoken) {
        this.languagesSpoken = languagesSpoken;
    }

    public byte[] getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(byte[] profilePicture) {
        this.profilePicture = profilePicture;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getSelfDescription() {
        return selfDescription;
    }

    public void setSelfDescription(String selfDescription) {
        this.selfDescription = selfDescription;
    }

    // Default no-argument constructor required by JPA
    public Vet() {
    }

    // Constructor without ID because it is auto-generated
    public Vet(String title, String languagesSpoken, String selfDescription, int userID) {
        this.title = title;
        this.languagesSpoken = languagesSpoken;
        this.selfDescription = selfDescription;
        this.userID = userID;
    }
}