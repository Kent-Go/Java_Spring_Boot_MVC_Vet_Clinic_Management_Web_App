package au.edu.rmit.sept.webapp.models;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "prescribed_medication")
public class PrescribedMedication {
    //Primary key - ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    //Dosage for medication (e.g. how many tablets)
    @Column(name = "dosage")
    private int dosage;

    //Daily frequency
    @Column(name = "daily_frequency")
    private int dailyFrequency;

    //Duration of taking the medication - in days
    @Column(name = "duration")
    private int duration;

    //Instructions for medication
    @Column(name = "instruction")
    private String instruction;

    //Foreign key - Medicine ID
    @Column(name = "medicine_id")
    private int medicineID;

    //Foreign key - Order ID
    @Column(name = "order_id")
    private int orderID;

    //Foreign key - Appointment ID
    @Column(name = "appointment_id")
    private int appointmentID;

    // Getters and Setters
    //ID(primary key)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    //Dosage
    public int getDosage() {
        return dosage;
    }

    public void setDosage(int dosage) {
        this.dosage = dosage;
    }

    //Daily frequency
    public int getDailyFrequency() {
        return dailyFrequency;
    }

    public void setDailyFrequency(int dailyFrequency) {
        this.dailyFrequency = dailyFrequency;
    }

    //Duration
    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    //Instruction
    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    //Medicine ID
    public int getMedicineID() {
        return medicineID;
    }

    public void setMedicineID(int medicineID) {
        this.medicineID = medicineID;
    }

    //Order ID
    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    //Appointment ID
    public int getAppointmentID() {
        return appointmentID;
    }

    public void setAppointmentID(int appointmentID) {
        this.appointmentID = appointmentID;
    }

}