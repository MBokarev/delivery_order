package ru.netology.delivery.test;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import org.openqa.selenium.Keys;
import ru.netology.delivery.data.DataGenerator;

import java.time.Duration;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;
import static ru.netology.delivery.data.DataGenerator.getUserInfo;

class DeliveryTest {

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    public static String deleteString = Keys.chord(Keys.CONTROL, "a") + Keys.DELETE;

    @Test
    @DisplayName("Should successful plan and replan meeting")
    void shouldSuccessfulPlanAndReplanMeeting() {
        DataGenerator.UserInfo user = getUserInfo();

        Configuration.holdBrowserOpen = true;

        $("[placeholder=\"Город\"]").setValue(user.getCity());
        $("[placeholder=\"Дата встречи\"]").sendKeys(deleteString);
        $("[placeholder=\"Дата встречи\"]").setValue(user.getFirstDate());
        $("[name=\"name\"]").setValue(user.getName());
        $("[name=\"phone\"]").setValue(user.getPhone());
        $("[class=\"checkbox__box\"]").click();
        $x("//*[text()=\"Запланировать\"]").click();
        $(withText("Успешно!")).should(visible, Duration.ofSeconds(15));
        $(".notification__content").shouldHave(Condition.text("Встреча успешно запланирована на " + user.getFirstDate()
        ), Duration.ofSeconds(15));
        $("[placeholder=\"Дата встречи\"]").sendKeys(deleteString);
        $("[placeholder=\"Дата встречи\"]").setValue(user.getSecondDate());
        $x("//*[text()=\"Запланировать\"]").click();
        $(withText("Необходимо подтверждение")).should(visible, Duration.ofSeconds(15));
        $("[data-test-id=replan-notification]").shouldHave(Condition.text("У вас уже запланирована встреча на другую дату." +
                        " Перепланировать?"),
                Duration.ofSeconds(15));
        $(".button_size_s").click();
        $(".notification__title").shouldHave(Condition.text("Успешно!"));
        $(".notification__content").shouldHave(Condition.text("Встреча успешно запланирована на " +
                user.getSecondDate()), Duration.ofSeconds(15));
    }
}
