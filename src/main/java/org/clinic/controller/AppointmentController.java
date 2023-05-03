package org.clinic.controller;

import org.clinic.dto.AppointmentDto;
import org.clinic.entity.Appointment;
import org.clinic.entity.User;
import org.clinic.repository.AppointmentRepository;
import org.clinic.repository.UserRepository;
import org.clinic.util.AppointmentStatus;
import org.clinic.util.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import javax.servlet.ServletContext;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/")
@PreAuthorize("isAuthenticated()")
public class AppointmentController {

    private final UserRepository userRepository;
    private final AppointmentRepository appointmentRepository;
    private final ServletContext servletContext;

    @Autowired
    public AppointmentController(UserRepository userRepository, AppointmentRepository appointmentRepository, ServletContext servletContext) {
        this.userRepository = userRepository;
        this.appointmentRepository = appointmentRepository;
        this.servletContext = servletContext;
    }

    public void setCommonParams(ModelMap model){
        model.put("users", userRepository.findAll());
        model.put("contextPath", servletContext.getContextPath());
    }

    @GetMapping("/main")
    public String appointments(ModelMap model){

        setCommonParams(model);
        List<Appointment> data = null;

        if(SecurityUtils.hasRole("ADMIN")){
            data = appointmentRepository.findAppointmentsForAdminsSorted();
        }else{
            data = appointmentRepository.findAppointmentsForUsersSorted(
                    SecurityUtils.getCurrentDetails()
                    .getUsername());
        }
        model.put("appointments", data);
        return "main";
    }

    @GetMapping("/appointment/book/{appointmentId}")
    public String bookAppointment(@PathVariable Long appointmentId, ModelMap model){
        Appointment appointment = appointmentRepository.findById(appointmentId).get();
        model.put("appointment", new AppointmentDto(appointment));
        setCommonParams(model);
        return "appointment-new";
    }

    @GetMapping("/available")
    @PreAuthorize("hasRole('PATIENT')")
    public String getAvailableAppointments(ModelMap model){
        setCommonParams(model);
        model.put("appointments", appointmentRepository.findAllSorted());
        return "available";
    }

    @GetMapping("/appointment/new")
    @PreAuthorize("hasRole('PATIENT')")
    public String appointmentNew(ModelMap model){
        setCommonParams(model);
        return "appointment-new";
    }

    @PostMapping("/appointment/book")
    public String bookAppointment(AppointmentDto dto, ModelMap model){
        setCommonParams(model);
        Appointment appointment = appointmentRepository.findById(dto.getAppointmentId()).orElseThrow();
        appointment.setTitle(dto.getTitle());
        appointment.setReason(dto.getReason());
        appointment.setStatus(AppointmentStatus.PENDING_APPROVAL.toString());
        appointment.setUser((User)SecurityUtils.getCurrentDetails());
        appointment.setDtRequested(LocalDateTime.now());

        appointmentRepository.save(appointment);
        return "redirect:/main";
    }

    @PostMapping("/appointment/{appointmentId}/approve")
    @PreAuthorize("hasRole('ADMIN')")                           // in case if request is mocked with JavaScript
    @ResponseStatus(HttpStatus.OK)
    public String approveAppointment(@PathVariable Long appointmentId){
        Appointment appointment = appointmentRepository.findById(appointmentId).orElseThrow();
        if(appointment.getStatus().equals("PENDING_APPROVAL")){
            appointment.setStatus(AppointmentStatus.APPROVED.toString());
        }else{
            throw new IllegalStateException("Illegal state of appointment");
        }
        appointmentRepository.save(appointment);
        return "redirect:/main";
    }

    // Cancellation requests (GET & POST):
    @GetMapping("/appointment/{appointmentId}/requestCancel")
    @PreAuthorize("hasRole('PATIENT')")
    public String requestCancellation(@PathVariable Long appointmentId){
        Appointment appointment = appointmentRepository.findById(appointmentId).orElseThrow();
        appointment.setStatus(AppointmentStatus.PENDING_CANCELLATION.toString());
        appointmentRepository.save(appointment);
        return "redirect:/main";
    }

    @PostMapping("/appointment/{appointmentId}/cancel")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public String cancelAppointment(@PathVariable Long appointmentId){

        Appointment old = appointmentRepository.findById(appointmentId).orElseThrow();
        Appointment appointment = new Appointment();
        appointment.setTitle("No title");
        appointment.setReason("No reason");
        appointment.setDtGenerated(old.getDtGenerated());
        appointment.setStatus(AppointmentStatus.UNUSED.toString());

        old.setStatus(AppointmentStatus.CANCELLED.toString());
        appointmentRepository.save(appointment);
        appointmentRepository.save(old);
        return "redirect:/main";
    }
}
