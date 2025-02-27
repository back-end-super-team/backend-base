package backend.backendbase.utility;

import java.util.Random;

public class Utils {

    public static String generateRandomPassword(int len) {
        String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ!@#$%^&*()?<>,.";
        Random rnd = new Random();

        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            sb.append(AB.charAt(rnd.nextInt(AB.length())));
        }
        return sb.toString();
    }

    public static String toCamelCase(String input) {
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
                result.append(Character.toUpperCase(word.charAt(0)))
                        .append(word.substring(1).toLowerCase());
            }
        }

        return result.toString();
    }

}
