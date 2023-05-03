package org.clinic.service;

import org.clinic.entity.Role;
import org.clinic.entity.User;
import org.clinic.repository.RoleRepository;
import org.clinic.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import javax.persistence.EntityExistsException;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder encoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.encoder = encoder;
    }

    public User findByUsername(String username){
        User user = userRepository.findByUsername(username).orElseThrow();
        user.getAppointments().size();
        return user;
    }

    @Override
    public void create(String fName, String lName, String email, String username, String password) {

        if(userRepository.findByUsername(username).isPresent()){
            throw new EntityExistsException();
        }

        User newUser = new User();
        newUser.setfName(encoder.encode(fName));
        newUser.setlName(encoder.encode(lName));
        newUser.setEmail(email);
        newUser.setUsername(username);
        newUser.setPassword(encoder.encode(password));

        newUser.setRoles(List.of(roleRepository.findByRoleName(Role.PATIENT).orElseThrow(),
                                 roleRepository.findByRoleName(Role.USER).orElseThrow()));
        newUser.setDtCreated(LocalDateTime.now());
        newUser.setIsActive(true);

        userRepository.save(newUser);
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByUsername(username).orElseThrow();
        user.getRoles().size();
        return user;
    }

}
