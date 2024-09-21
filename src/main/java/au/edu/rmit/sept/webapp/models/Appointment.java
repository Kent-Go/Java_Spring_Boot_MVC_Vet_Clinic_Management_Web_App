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
@Table(name = "appointment")
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "start_time")
    private LocalTime startTime;

    @Column(name = "end_time")
    private LocalTime endTime;

    @Column(name = "vet_id")
    private int vetID;

    @ManyToOne
    @JoinColumn(name = "vet_id", insertable = false, updatable = false)
    private Vet vet;

    @Column(name = "pet_id")
    private int petID;

    @Column(name = "appointment_type_id")
    private int appointmentTypeID;

    @ManyToOne
    @JoinColumn(name = "pet_id", insertable = false, updatable = false)
    private Pet pet;

    @ManyToOne
    @JoinColumn(name = "appointment_type_id", insertable = false, updatable = false)
    private AppointmentType appointmentType;

    private String dayOfWeek;

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public int getVetID() {
        return vetID;
    }

    public void setVetID(int vetID) {
        this.vetID = vetID;
    }

    public int getPetID() {
        return petID;
    }

    public void setPetID(int petID) {
        this.petID = petID;
    }

    public int getAppointmentTypeID() {
        return appointmentTypeID;
    }

    public void setAppointmentTypeID(int appointmentTypeID) {
        this.appointmentTypeID = appointmentTypeID;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public Vet getVet() {
        return vet;
    }

    public void setVet(Vet vet) {
        this.vet = vet;
    }

    public Pet getPet() {
        return pet;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }

    public AppointmentType getAppointmentType() {
        return appointmentType;
    }

    public void setAppointmentType(AppointmentType appointmentType) {
        this.appointmentType = appointmentType;
    }

    // Default no-argument constructor required by JPA
    public Appointment() {
    }

    // Constructor without ID because it is auto-generated
    public Appointment(LocalDate date, LocalTime startTime, LocalTime endTime, int vetID, int petID, int appointmentTypeID) {
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.vetID = vetID;
        this.petID = petID;
        this.appointmentTypeID = appointmentTypeID;
    }
}
