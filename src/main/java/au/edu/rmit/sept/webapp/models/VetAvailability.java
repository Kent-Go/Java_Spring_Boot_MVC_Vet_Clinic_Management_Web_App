package au.edu.rmit.sept.webapp.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "vet_availability")
public class VetAvailability {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "vet_id")
    private int vetID;

    @Column(name = "availability_id")
    private int availabilityID;

    @ManyToOne
    @JoinColumn(name = "vet_id", insertable = false, updatable = false)
    private Vet vet;

    @ManyToOne
    @JoinColumn(name = "availability_id", insertable = false, updatable = false)
    private Availability availability;

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getVetID() {
        return vetID;
    }

    public void setVetID(int vetID) {
        this.vetID = vetID;
    }

    public int getAvailabilityID() {
        return availabilityID;
    }

    public void setAvailabilityID(int availabilityID) {
        this.availabilityID = availabilityID;
    }

    public Vet getVet() {
        return vet;
    }

    public void setVet(Vet vet) {
        this.vet = vet;
    }

    public Availability getAvailability() {
        return availability;
    }

    public void setAvailability(Availability availability) {
        this.availability = availability;
    }

    // Default no-argument constructor required by JPA
    public VetAvailability() {
    }

    // Constructor without ID because it is auto-generated
    public VetAvailability(int vetID, int availabilityID) {
        this.vetID = vetID;
        this.availabilityID = availabilityID;
    }
}