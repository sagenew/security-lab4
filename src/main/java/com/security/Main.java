package com.security;

import com.security.generator.PasswordGenerator;

public class Main {
    public static void main(String[] args) {
        PasswordGenerator generator = new PasswordGenerator();
        generator.generate();
    }
}
