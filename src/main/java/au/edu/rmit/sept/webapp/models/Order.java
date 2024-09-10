package au.edu.rmit.sept.webapp.models;

import java.time.LocalDate;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "date") // Format: YYYY-MM-DD
    private LocalDate date;

    @Column(name = "status")
    private String status;

    @Column(name = "payment_details_id")
    private int paymentDetailsID;

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getPaymentDetailsID() {
        return paymentDetailsID;
    }

    public void setPaymentDetailsID(int paymentDetailsID) {
        this.paymentDetailsID = paymentDetailsID;
    }

    // Default no-argument constructor required by JPA
    public Order() {
    }

    // Constructor without ID because it is auto-generated
    public Order(LocalDate date, String status, int paymentDetailsID) {
        this.date = date;
        this.status = status;
        this.paymentDetailsID = paymentDetailsID;
    }
}
