package com.security.hash;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.stream.Collectors;

public class MD5Hash {
        public String hash(String password) {
            MessageDigest digest;
            try {
                digest = MessageDigest.getInstance("md5");
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
                return "";
            }
            digest.update(password.getBytes());
            byte [] hash = digest.digest();
            StringBuilder sb = new StringBuilder();
            for (byte b : hash) {
                sb.append(String.format("%02X", b));
            }
            return sb.toString().toLowerCase();
        }

    public List<String> hash(List<String> passwords) {
        return passwords
                .stream()
                .parallel()
                .map(this::hash)
                .collect(Collectors.toList());
    }
}
