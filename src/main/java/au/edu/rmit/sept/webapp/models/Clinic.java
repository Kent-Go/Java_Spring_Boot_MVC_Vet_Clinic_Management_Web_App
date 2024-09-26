package au.edu.rmit.sept.webapp.models;

import java.time.LocalDate;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "clinic")
public class Clinic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "clinic_address_id")
    private int clinicAddressID;

    @OneToOne
    @JoinColumn(name = "clinic_address_id", insertable = false, updatable = false)
    private ClinicAddress clinicAddress;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getClinicAddressID() {
        return clinicAddressID;
    }

    public void setClinicAddressID(int clinicAddressID) {
        this.clinicAddressID = clinicAddressID;
    }

    public ClinicAddress getClinicAddress() {
        return clinicAddress;
    }

    public void setClinicAddress(ClinicAddress clinicAddress) {
        this.clinicAddress = clinicAddress;
    }

    // Default no-argument constructor required by JPA
    public Clinic() {
    }

    // Constructor without ID because it is auto-generated
    public Clinic(String name, String email, String password, int clinicAddressID) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.clinicAddressID = clinicAddressID;
    }
}