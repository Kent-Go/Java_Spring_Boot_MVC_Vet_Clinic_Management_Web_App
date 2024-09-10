package au.edu.rmit.sept.webapp.models;

import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Entity
@Table(name = "payment_details")
public class PaymentDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "card_number")
    private String cardNumber;

    @Column(name = "expiration_date") // Format: MM/YY
    private String expiryDate;

    @Column(name = "hashed_cvv")
    private String cvv;

    @Column(name = "pet_owner_id")
    private int petOwnerID;

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

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public int getPetOwnerID() {
        return petOwnerID;
    }

    public void setPetOwnerID(int petOwnerID) {
        this.petOwnerID = petOwnerID;
    }

    // Default no-argument constructor required by JPA
    public PaymentDetails() {
    }

    // Constructor for creating a new PaymentDetails object with no ID because it is auto-generated
    public PaymentDetails(String name, String cardNumber, String expiryDate, String cvv, int petOwnerID) {
        this.name = name;
        this.cardNumber = cardNumber;
        this.expiryDate = expiryDate;
        this.cvv = cvv;
        this.petOwnerID = petOwnerID;
    }
}