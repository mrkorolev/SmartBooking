package org.clinic.dto;

import org.clinic.entity.Appointment;

import java.time.LocalDateTime;

public class AppointmentDto {

    private Long appointmentId;
    private String title;
    private String reason;
    private String status;
    private LocalDateTime dtGenerated;

    public AppointmentDto(Appointment appointment) {
        this.appointmentId = appointment.getAppointmentId();
        this.title = appointment.getTitle();
        this.reason = appointment.getReason();
        this.status = appointment.getStatus();
        this.dtGenerated = appointment.getDtGenerated();
    }


    public AppointmentDto() {
    }

    public Long getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(Long appointmentId) {
        this.appointmentId = appointmentId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getDtGenerated() {
        return dtGenerated;
    }

    public void setDtGenerated(LocalDateTime dtGenerated) {
        this.dtGenerated = dtGenerated;
    }
}
