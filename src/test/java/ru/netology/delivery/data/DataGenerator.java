package ru.netology.delivery.data;

import com.github.javafaker.Faker;
import lombok.Value;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import static com.codeborne.selenide.Selenide.$;

public class DataGenerator {
    private DataGenerator() {
    }

    public static String generateDate(int days) {
        String date = new String();
        $("[placeholder=\"Дата встречи\"]").setValue(LocalDate.now().
                plusDays(days).
                format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
        return date;
    }

    public static String generateCity(String locale) {
        Faker faker = new Faker((new Locale("ru")));
        String city = faker.address().city();
        return city;
    }

    public static String generateName(String locale) {
        Faker faker = new Faker((new Locale("ru")));
        String name = faker.name().fullName();
        return name;
    }

    public static String generatePhone(String locale) {
        Faker faker = new Faker((new Locale("ru")));
        String phone = faker.phoneNumber().phoneNumber();
        return phone;
    }

    public static class Registration {
        private Registration() {
        }

        public static UserInfo generateUser(String locale) {
            UserInfo user = new UserInfo("ru","ru","ru");
            $("[placeholder=\"Город\"]").setValue(DataGenerator.generateCity("ru"));
            $("[name=\"name\"]").setValue(DataGenerator.generateName("ru"));
            $("[name=\"phone\"]").setValue(DataGenerator.generatePhone("ru"));
            return user;
        }
    }

    @Value
    public static class UserInfo {
        String city;
        String name;
        String phone;
    }
}
