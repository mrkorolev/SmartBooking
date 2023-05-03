package org.clinic.config;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Scanner;


// A helper class, used to generate encrypted data for the database
public class StringEncoder {

    public static void main(String[] args) {

        // Helps to encrypt our passwords (from database schema):
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        try(Scanner sc = new Scanner(System.in)){

            String input = "";
            do{
                input = encoder.encode(sc.nextLine());
                System.out.println(input);
            }while(input != "");
        }
    }
}
