package com.cdd.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PasswordUtils {
    @Autowired
    PasswordUtils(PasswordEncoder passwordEncoder){
        this.passwordEncoder = passwordEncoder;
    }

    private static PasswordEncoder passwordEncoder;

    public static String getEncryptedPassword(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }
}
