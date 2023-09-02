package guru.qa;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import heplers.Attach;
import io.qameta.allure.Attachment;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import org.openqa.selenium.remote.DesiredCapabilities;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static io.qameta.allure.Allure.step;

import java.util.Map;


@Tag("remote")
public class RemoteRegistrationTest {

    @BeforeAll
    static void beforeAll() {
        Configuration.baseUrl = "https://demoqa.com";
        Configuration.browserSize = "1920x1080";
        Configuration.pageLoadStrategy = "eager";
        Configuration.remote = "https://user1:1234@selenoid.autotests.cloud/wd/hub";

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("selenoid:options", Map.<String, Object>of(
                "enableVNC", true,
                "enableVideo", true
        ));

        Configuration.browserCapabilities = capabilities;
    }

    @BeforeEach
    void addListener(){
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide());
    }

    @AfterEach
    void addAttachments(){
        Attach.screenshotAs("Screenshot");
        Attach.pageSource();
        Attach.browserConsoleLogs();
        Attach.addVideo();

        closeWebDriver();
    }

    @Test
    @DisplayName("Successful registration")
    void registrationFormTest() {
        step("Open form", () -> {
            open("/automation-practice-form");
            executeJavaScript("$('#fixedban').remove()");
            executeJavaScript("$('footer').remove()");
        });

        step("Fill form", () -> {
            $("#firstName").setValue("Mary");
            $("#lastName").setValue("Watson");
            $("#userEmail").setValue("marywatson@gmail.com");
            $("#genterWrapper").$(byText("Female")).click();
            $("#userNumber").setValue("7085600410");
            $("#dateOfBirthInput").click();
            $(".react-datepicker__year-select").click();
            $(".react-datepicker__year-select").selectOption("2005");
            $(".react-datepicker__month-select").click();
            $(".react-datepicker__month-select").selectOption("January");
            $(".react-datepicker__day--001.react-datepicker__day--weekend").click();
            $("#subjectsInput").setValue("History").pressEnter();
            $("#hobbiesWrapper").$(byText("Reading")).click();
            $("#uploadPicture").uploadFromClasspath("shrek.png");
            $("#currentAddress").setValue("40 Lipton Court, Chase Side Sothgate, London, N14");
            $("#state").click();
            $("#stateCity-wrapper").$(byText("Haryana")).click();
            $("#city").click();
            $("#stateCity-wrapper").$(byText("Karnal")).click();
            $("#submit").click();
        });

        step("Verify results", () -> {
            $(".modal-content").should(Condition.appear);
            $(".table-responsive").$(byText("Student Name")).parent()
                    .shouldHave(Condition.text("Mary Watson"));
            $(".table-responsive").$(byText("Student Email")).parent()
                    .shouldHave(Condition.text("marywatson@gmail.com"));
            $(".table-responsive").$(byText("Gender")).parent()
                    .shouldHave(Condition.text("Female"));
            $(".table-responsive").$(byText("Mobile")).parent()
                    .shouldHave(Condition.text("7085600410"));
            $(".table-responsive").$(byText("Date of Birth")).parent()
                    .shouldHave(Condition.text("01 January,2005"));
            $(".table-responsive").$(byText("Subjects")).parent()
                    .shouldHave(Condition.text("History"));
            $(".table-responsive").$(byText("Hobbies")).parent()
                    .shouldHave(Condition.text("Reading"));
            $(".table-responsive").$(byText("Picture")).parent()
                    .shouldHave(Condition.text("shrek.png"));
            $(".table-responsive").$(byText("Address")).parent()
                    .shouldHave(Condition.text("40 Lipton Court, Chase Side Sothgate, London, N14"));
            $(".table-responsive").$(byText("State and City")).parent()
                    .shouldHave(Condition.text("Haryana Karnal"));
        });
    }
}
