package org.clinic.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "users")
public class User implements UserDetails {

    public static final String ROLE = "ROLE_";                  // for the purpose of getAuthorities(...)

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "f_name")
    private String fName;

    @Column(name = "l_name")
    private String lName;

    private String email;

    private String username;

    private String password;

    @Column(name = "dt_created")
    private LocalDateTime dtCreated;

    @Column(name = "is_active")
    private Boolean isActive;

    @OneToMany(mappedBy = "user")
    private List<Appointment> appointments;

    @ManyToMany(mappedBy = "users")
    private List<Role> roles;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }



    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDateTime getDtCreated() {
        return dtCreated;
    }

    public void setDtCreated(LocalDateTime dtCreated) {
        this.dtCreated = dtCreated;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean active) {
        isActive = active;
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    // UserDetails methods:
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles()
                .stream().map(role -> new SimpleGrantedAuthority(ROLE + role.getRoleName()))
                .collect(Collectors.toList());
    }

    @Override
    public boolean isAccountNonExpired() {
        return getIsActive();
    }

    @Override
    public boolean isAccountNonLocked() {
        return getIsActive();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return getIsActive();
    }

    @Override
    public boolean isEnabled() {
        return getIsActive();
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }


}
