package ru.netology.delivery.data;

import com.github.javafaker.Faker;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.format.DateTimeFormatter;
import java.time.LocalDate;
import java.util.Locale;

public class DataGenerator {
    private DataGenerator() {
    }

    @Data
    @RequiredArgsConstructor
    public static class UserInfo {
        private final String city;
        private final String name;
        private final String phone;
        private final String firstDate;
        private final String secondDate;
    }

    public static UserInfo getUserInfo() {
        DateTimeFormatter dt = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        Faker faker = new Faker(new Locale("ru"));
        return new UserInfo(faker.address().city(),
                faker.name().fullName(),
                faker.phoneNumber().phoneNumber(),
                LocalDate.now().plusDays(4).format(dt),
                LocalDate.now().plusDays(7).format(dt));
    }
}