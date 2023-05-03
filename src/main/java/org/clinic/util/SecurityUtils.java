package org.clinic.util;

import org.clinic.entity.Appointment;
import org.clinic.entity.Role;
import org.clinic.entity.User;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.stream.Collectors;

public class SecurityUtils {

    private static final String ACCESS_DENIED = "Access denied: No user logged id!";

    public static UserDetails getCurrentDetails(){
        Object principal = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
        if(!(principal instanceof UserDetails)){
            throw new AccessDeniedException(ACCESS_DENIED);
        }

        return (UserDetails) principal;
    }

    public static void checkAuthorityOnAppointment(Appointment appointment) {
        if(!hasAuthorityOnAppointment(appointment)){
            throw new AccessDeniedException(ACCESS_DENIED);
        }
    }

    public static boolean hasAuthorityOnAppointment(Appointment appointment){
        String username = SecurityUtils.getCurrentDetails().getUsername();
        return appointment.getUser().getUsername().equals(username);
    }


    public static boolean hasRole(String roleName){
        return getCurrentDetails().getAuthorities()
                .stream().map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet())
                .contains(User.ROLE + roleName);
    }
}
