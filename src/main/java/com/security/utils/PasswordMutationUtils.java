package com.security.utils;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PasswordMutationUtils {
    private static final Random random = new Random();

    public static StringBuilder mutatePassword(StringBuilder sb) {
        int mutationOption = random.nextInt(100);
        if (mutationOption % 10 == 0) {
            sb = replaceLettersWithNumbers(sb);
            replace1337(sb);
            return sb;
        }
        if (mutationOption % 8 == 0) {
            appendNumbers(sb);
            replace1337(sb);
            return sb;
        }
        if (mutationOption % 4 == 0) {
            appendNumbers(sb);
            sb = replaceLettersWithNumbers(sb);
            return sb;
        }
        if (mutationOption % 3 == 0) {
            appendNumbers(sb);
            replaceOneLetterWithNumber(sb);
            return sb;
        }
        if (mutationOption % 2 == 0) {
            appendNumbers(sb);
            return sb;
        }
        return sb;
    }

    private static void appendNumbers(StringBuilder sb) {
        int numbersCount = random.nextInt(4) + 1;
        for (int i = 0; i < numbersCount; i++) {
            sb.append(random.nextInt(9));
        }
    }

    private static void replaceOneLetterWithNumber(StringBuilder sb) {
        int replaceOption = random.nextInt(7);
        switch (replaceOption) {
            case 1:
                if (replaceFirst(sb, "o", "0")) return;
            case 2:
                if (replaceFirst(sb, "a", "@")) return;
            case 3:
                if (replaceFirst(sb, "f", "4")) return;
            case 4:
                if (replaceFirst(sb, "e", "3")) return;
            case 5:
                if (replaceFirst(sb, "i", "1")) return;
            case 6:
                replaceFirst(sb, "g", "9");
        }
    }

    private static StringBuilder replaceLettersWithNumbers(StringBuilder sb) {
        sb = replaceAll(sb, "o", "0");
        sb = replaceAll(sb, "a", "@");
        sb = replaceAll(sb, "f", "4");
        sb = replaceAll(sb, "e", "3");
        sb = replaceAll(sb, "i", "1");
        return replaceAll(sb, "g", "9");
    }

    private static void replace1337(StringBuilder sb) {
        int replaceOption = random.nextInt(7);
        switch (replaceOption) {
            case 1:
                if (replaceFirst(sb, "b", "|3")) return;
            case 2:
                if (replaceFirst(sb, "c", "(")) return;
            case 3:
                if (replaceFirst(sb, "h", "|-|")) return;
            case 4:
                if (replaceFirst(sb, "s", "5")) return;
            case 5:
                if (replaceFirst(sb, "k", "|<")) return;
            case 6:
                replaceFirst(sb, "x", "}{");
        }
    }

    private static StringBuilder replaceAll(StringBuilder sb, String find, String replace){
        return new StringBuilder(Pattern.compile(find).matcher(sb).replaceAll(replace));
    }

    private static boolean replaceFirst(StringBuilder sb, String find, String replace){
        Pattern p = Pattern.compile(find);
        Matcher m = p.matcher(sb);
        if (m.find()) {
            sb.replace(m.start(), m.end(), replace);
            return true;
        }
        return false;
    }
}
