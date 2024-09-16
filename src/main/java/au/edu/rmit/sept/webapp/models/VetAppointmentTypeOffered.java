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
@Table(name = "vet_appointment_type_offered")
public class VetAppointmentTypeOffered {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "vet_id")
    private int vetID;

    @Column(name = "appointment_type_id")
    private int appointmentTypeID;

    @ManyToOne
    @JoinColumn(name = "vet_id", insertable = false, updatable = false)
    private Vet vet;

    @ManyToOne
    @JoinColumn(name = "appointment_type_id", insertable = false, updatable = false)
    private AppointmentType appointmentType;

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

    public int getAppointmentTypeID() {
        return appointmentTypeID;
    }

    public void setAppointmentTypeID(int appointmentTypeID) {
        this.appointmentTypeID = appointmentTypeID;
    }

    public AppointmentType getAppointmentType() {
        return appointmentType;
    }

    public void setAppointmentType(AppointmentType appointmentType) {
        this.appointmentType = appointmentType;
    }

    // Default no-argument constructor required by JPA
    public VetAppointmentTypeOffered() {
    }

    // Constructor without ID because it is auto-generated
    public VetAppointmentTypeOffered(int vetID, int appointmentTypeID) {
        this.vetID = vetID;
        this.appointmentTypeID = appointmentTypeID;
    }
}
