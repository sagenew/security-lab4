package com.security.generator;

import com.security.hash.BCryptHash;
import com.security.hash.MD5Hash;
import com.security.hash.Sha1Hash;
import com.security.io.PasswordReader;
import com.security.io.PasswordWriter;
import com.security.utils.PasswordMutationUtils;
import org.apache.commons.lang3.RandomStringUtils;

import java.security.SecureRandom;
import java.util.*;

public class PasswordGenerator {
    private static final int AMOUNT_OF_PASSWORDS = 100000;
    private static final double TOP_100_QUOTA = 0.05;
    private static final double TOP_100K_QUOTA = 0.8;
    private static final double RANDOM_QUOTA = 0.05;
    private static final double DICT_QUOTA = 0.10;
    private static final int PASSWORD_MIN_LENGTH = 8;
    private static final int PASSWORD_MAX_LENGTH = 16;
    private static final Random random = new SecureRandom();

    private final List<String> TOP_100_PASSWORDS;
    private final List<String> TOP_100K_PASSWORDS;
    private final List<String> WORD_DICTIONARY;

    public PasswordGenerator() {
        TOP_100_PASSWORDS =
                PasswordReader.readPasswordsFromFile("top100.csv");
        TOP_100K_PASSWORDS =
                PasswordReader.readPasswordsFromFile("top100k.csv");
        WORD_DICTIONARY =
                PasswordReader.readPasswordsFromFile("dictionary.csv");
    }

    public void generate() {
        List<String> passwords = generatePasswords();
        Collections.shuffle(passwords);
        writePasswordsToFiles(passwords);
    }

    private void writePasswordsToFiles (List<String> passwords) {
        PasswordWriter writer = new PasswordWriter();
        writer.writePasswordsToFile(passwords, "plaintext.csv");
        writer.writePasswordsToFile(new MD5Hash().hash(passwords), "md5.csv");
        writer.writePasswordsToFile(new Sha1Hash().hash(passwords), "sha1.csv");
        writer.writePasswordsToFile(new BCryptHash().hash(passwords), "bcrypt.csv");
    }

    private List<String> generatePasswords() {
        List<String> list = new ArrayList<>();
        list.addAll(generateNPasswordsFromTopList(TOP_100_PASSWORDS, (int) (AMOUNT_OF_PASSWORDS * TOP_100_QUOTA)));
        list.addAll(generateNPasswordsFromTopList(TOP_100K_PASSWORDS, (int) (AMOUNT_OF_PASSWORDS * TOP_100K_QUOTA)));
        list.addAll(generateRandomPasswords((int) (AMOUNT_OF_PASSWORDS * RANDOM_QUOTA)));
        list.addAll(generateNPasswordsFromDictionary((int)(AMOUNT_OF_PASSWORDS * DICT_QUOTA)));
        return list;
    }

    private List<String> generateNPasswordsFromTopList(List<String> passwords, int n) {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            list.add(getRandomStringFromList(passwords));
        }
        return list;
    }

    private List<String> generateRandomPasswords(int n) {
        char [] symbols = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz)!@#$%^&*(-_.;:?*=,0123456789".toCharArray();
        List<String> list = new ArrayList<>();
        for (int i = 0; i < n; i ++) {
            int randPasswordLength = random.nextInt(PASSWORD_MAX_LENGTH - PASSWORD_MIN_LENGTH) + PASSWORD_MIN_LENGTH;
            list.add(RandomStringUtils.random(randPasswordLength, 0, symbols.length - 1, false,false, symbols, new SecureRandom()));
        }
        return list;
    }

    private List<String> generateNPasswordsFromDictionary(int n) {
        List<String> list = new ArrayList<>();
        StringBuilder sb;
        for(int i = 0; i < n; i++) {
            sb = new StringBuilder(getRandomStringFromList(WORD_DICTIONARY));
            while (sb.length() < PASSWORD_MIN_LENGTH) {
                sb.append(getRandomStringFromList(WORD_DICTIONARY));
            }
            sb = PasswordMutationUtils.mutatePassword(sb);
            if(sb.length() > PASSWORD_MAX_LENGTH) sb.setLength(PASSWORD_MAX_LENGTH);
            list.add(sb.toString());
        }
        return list;
    }

    private String getRandomStringFromList(List<String> list) {
        Random rand = new Random();
        return list.get(rand.nextInt(list.size()));
    }
}
