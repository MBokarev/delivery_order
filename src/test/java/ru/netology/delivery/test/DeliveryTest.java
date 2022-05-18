package ru.netology.delivery.test;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import ru.netology.delivery.data.DataGenerator;

import java.time.Duration;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;

class DeliveryTest {

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    public static String deleteString = Keys.chord(Keys.CONTROL, "a") + Keys.DELETE;

    @Test
    @DisplayName("Should successful plan and replan meeting")
    void shouldSuccessfulPlanAndReplanMeeting() {

        Configuration.holdBrowserOpen = true;

        var daysToAddForFirstMeeting = 4;
        var daysToAddForSecondMeeting = 7;

        var validUser = DataGenerator.Registration.generateUser("ru");
        $("[placeholder=\"Дата встречи\"]").sendKeys(deleteString);
        var firstMeetingDate = DataGenerator.generateDate(daysToAddForFirstMeeting);
        $("body").click();
        $("[class=\"checkbox__box\"]").click();
        $x("//*[text()=\"Запланировать\"]").click();
        $(withText("Успешно!")).should(visible, Duration.ofSeconds(15));
        $(".notification__content").shouldHave(Condition.text("Встреча успешно запланирована на " + firstMeetingDate),
                Duration.ofSeconds(15));
        $("[placeholder=\"Дата встречи\"]").sendKeys(deleteString);
        var secondMeetingDate = DataGenerator.generateDate(daysToAddForSecondMeeting);
        $x("//*[text()=\"Запланировать\"]").click();
        $(withText("Необходимо подтверждение")).should(visible, Duration.ofSeconds(15));
        $("[data-test-id=replan-notification]").shouldHave(Condition.text("У вас уже запланирована встреча на другую дату." +
                        " Перепланировать?"),
                Duration.ofSeconds(15));
        $(".button_size_s").click();
        $(".notification__title").shouldHave(Condition.text("Успешно!"));
        $(".notification__content").shouldHave(Condition.text("Встреча успешно запланирована на " + secondMeetingDate),
                Duration.ofSeconds(15));
    }
}