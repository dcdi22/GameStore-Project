package com.company.GameStore.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordUtility {

    public static void main(String[] args) {
        PasswordEncoder enc = new BCryptPasswordEncoder();

        String staffPassword = "password";
        String managerPassword = "password";
        String adminPassword = "password";

        String encodedPasswordS = enc.encode(staffPassword);
        String encodedPasswordM = enc.encode(managerPassword);
        String encodedPasswordA = enc.encode(adminPassword);

        System.out.println("This is the STAFF password " + encodedPasswordS);
        System.out.println("This is the MANAGERS Password " + encodedPasswordM);
        System.out.println("This is ADMINS Persons Password " + encodedPasswordA);
    }

}
