package backend.base.utility;

import java.security.SecureRandom;

public class Utils {

    private static final SecureRandom random = new SecureRandom ();

    public static String generateRandomPassword(int len) {
        String pattern = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ!@#$%^&*()?<>,.";

        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            sb.append(pattern.charAt(random.nextInt(pattern.length())));
        }
        return sb.toString();
    }

    public static String toCamelCase(final String input) {
        if (input == null || input.trim().isEmpty()) {
            return "";
        }

        String[] words = input.replaceAll("[-_]", " ").split("\\s+");

        StringBuilder result = new StringBuilder();

        for (int i = 0; i < words.length; i++) {
            String word = words[i].trim();
            if (word.isEmpty()) {
                continue;
            }

            if (i == 0) {
                result.append(word.toLowerCase());
            } else {
                result
                        .append(Character.toUpperCase(word.charAt(0)))
                        .append(word.substring(1).toLowerCase());
            }
        }
        String firstChar = String.valueOf(result.charAt(0));
        return result.toString().replaceFirst(firstChar, firstChar.toLowerCase());
    }

}
