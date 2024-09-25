package au.edu.rmit.sept.webapp.models;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Entity
@Table(name = "clinic_appointment_type_price")
public class ClinicAppointmentTypePrice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "clinic_id")
    private int clinicID;

    @Column(name = "appointment_type_id")
    private int appointmentTypeID;

    @Column(name = "price")
    private double price;

    @ManyToOne
    @JoinColumn(name = "clinic_id", insertable = false, updatable = false)
    private Clinic clinic;

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

    public int getClinicId() {
        return clinicID;
    }

    public void setClinicId(int clinicID) {
        this.clinicID = clinicID;
    }

    public int getAppointmentTypeID() {
        return appointmentTypeID;
    }

    public void setAppointmentTypeID(int appointmentTypeID) {
        this.appointmentTypeID = appointmentTypeID;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Clinic getClinic() {
        return clinic;
    }

    public void setClinic(Clinic clinic) {
        this.clinic = clinic;
    }

    public AppointmentType getAppointmentType() {
        return appointmentType;
    }

    public void setAppointmentType(AppointmentType appointmentType) {
        this.appointmentType = appointmentType;
    }

    // Default no-argument constructor required by JPA
    public ClinicAppointmentTypePrice() {
    }

    // Constructor without ID because it is auto-generated
    public ClinicAppointmentTypePrice(int clinicID, int appointmentTypeID, double price) {
        this.clinicID = clinicID;
        this.appointmentTypeID = appointmentTypeID;
        this.price = price;
    }
}
