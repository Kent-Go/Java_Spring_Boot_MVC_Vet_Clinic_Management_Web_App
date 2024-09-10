package au.edu.rmit.sept.webapp.models;

public class PrescribedMedicationDTO {
    //Primary key - ID
    private int id;

    //Dosage for medication (e.g. how many tablets)
    private int dosage;

    //Daily frequency
    private int dailyFrequency;

    //Duration of taking the medication - in days
    private int duration;

    //Instructions for medication
    private String instruction;

    //Foreign key - Medicine ID
    private int medicineID;

    //Foreign key - Order ID
    private int orderID;

    //Foreign key - Appointment ID
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