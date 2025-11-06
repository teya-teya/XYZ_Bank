package utils;

import net.datafaker.Faker;

import java.util.Locale;

public class RandomUtils {
    private static final Faker faker = new Faker(new Locale("en"));

    public static String lastName() {
        return faker.name().lastName();
    }
    public static String tenDigits() {
        return faker.number().digits(10);
    }

    public static String generateFirstNameFromDigits(String code) {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < code.length(); i += 2) {
            String pair = code.substring(i, i + 2);
            int num = Integer.parseInt(pair);

            char letter = (char) ('a' + (num % 26));
            result.append(letter);
        }

        return result.toString();
    }

}