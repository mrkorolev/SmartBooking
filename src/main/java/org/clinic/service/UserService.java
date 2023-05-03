package org.clinic.service;


import org.clinic.entity.User;

public interface UserService {

    User findByUsername(String username);
    void create(String fName, String lName, String email, String username, String password);
}
