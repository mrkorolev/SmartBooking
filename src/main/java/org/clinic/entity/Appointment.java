package org.clinic.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "appointments")
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "appointment_id")
    private Long appointmentId;

    private String title;

    // One to many: one user, many appointments
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String reason;

    private String status;

    @Column(name = "dt_requested")
    private LocalDateTime dtRequested;

    @Column(name = "dt_generated")
    private LocalDateTime dtGenerated;

    // Empty constructor:
    public Appointment() {
    }

    // For Hibernate manipulation:
    public Long getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(Long appointmentId) {
        this.appointmentId = appointmentId;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public LocalDateTime getDtRequested() {
        return dtRequested;
    }

    public void setDtRequested(LocalDateTime dtRequested) {
        this.dtRequested = dtRequested;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDateTime getDtGenerated() {
        return dtGenerated;
    }

    public void setDtGenerated(LocalDateTime dtGenerated) {
        this.dtGenerated = dtGenerated;
    }
}
