package org.mapmark.service.utils;

import com.github.javafaker.Faker;

import java.util.Locale;

public class DataGenerator {
    private DataGenerator() {

    }

    public static String generateEmail(String locale) {
        Faker faker = new Faker(new Locale(locale));
        return faker.internet().emailAddress();
    }

    public static String generateUsername(String locale) {
        Faker faker = new Faker(new Locale(locale));
        return faker.name().username();
    }
}
