package com.security.hash;

import at.favre.lib.crypto.bcrypt.BCrypt;

import java.util.List;
import java.util.stream.Collectors;

public class BCryptHash {

        protected String hash(String password) {
            return BCrypt.withDefaults().hashToString(4, password.toCharArray());
        }

        public List<String> hash(List<String> passwords) {
            return passwords
                .stream()
                .parallel()
                .map(this::hash)
                .collect(Collectors.toList());
        }
}
