package ru.netology.delivery.data;

import lombok.Value;

import static com.codeborne.selenide.Selenide.$;

public class DataHelper {
    private DataHelper() {
    }

    @Value
    public static class UserInfo {
        String city;
        String name;
        String phone;
    }

    public static class Registration {
        private Registration() {
        }

        public static UserInfo generateUser(String locale) {
            UserInfo user = new UserInfo(DataGenerator.generateCity("ru"), DataGenerator.generateName("ru"), DataGenerator.generatePhone("ru"));
            $("[placeholder=\"Город\"]").setValue(user.getCity());
            $("[name=\"name\"]").setValue(user.getName());
            $("[name=\"phone\"]").setValue(user.getPhone());
            return user;
        }
    }
}
