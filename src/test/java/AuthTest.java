import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;

import static com.codeborne.selenide.Selenide.open;


public class AuthTest {
    @Test
    public void login() {
        open("http://localhost:8080/");
        $("#username").sendKeys("user");
        $("#password").sendKeys("user");
        $("button[type=\"submit\"]").click();
        $x("//*[contains(text(),'Вы вошли как user')]").shouldBe(visible);
        closeWebDriver();
    }

    @Test
    public void login_not_existent() {
        open("http://localhost:8080/");
        $("#username").sendKeys("test");
        $("#password").sendKeys("test");
        $("button[type=\"submit\"]").click();
        $("#error_block").shouldBe(visible);
        closeWebDriver();
    }

    @Test
    public void login_caps() {
        open("http://localhost:8080/");
        $("#username").sendKeys("USER");
        $("#password").sendKeys("USER");
        $("button[type=\"submit\"]").click();
        $("#error_block").shouldBe(visible);
        closeWebDriver();
    }

    @Test
    public void empty_login() {
        open("http://localhost:8080/");
        $("#password").sendKeys("user");
        $("button[type=\"submit\"]").click();
        $("#error_block").shouldBe(visible);
        closeWebDriver();
    }

    @Test
    public void empty_password() {
        open("http://localhost:8080/");
        $("#username").sendKeys("user");
        $("button[type=\"submit\"]").click();
        $("#error_block").shouldBe(visible);
        closeWebDriver();
    }

    @Test
    public void spacebars_at_sides() {
        open("http://localhost:8080/");
        $("#username").sendKeys(" user ");
        $("#password").sendKeys(" user ");
        $("button[type=\"submit\"]").click();
        $("#error_block").shouldBe(visible);
        closeWebDriver();
    }

    @Test
    public void sql_injection() {
        open("http://localhost:8080/");
        $("#username").sendKeys("a' or 1=1");
        $("button[type=\"submit\"]").click();
        $("#error_block").shouldBe(visible);
        closeWebDriver();
    }

    @Test
    public void long_texts() {
        open("http://localhost:8080/");
        $("#username").sendKeys("0123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789");
        $("#password").sendKeys("0123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789");
        $("button[type=\"submit\"]").click();
        $("#error_block").shouldBe(visible);
        closeWebDriver();
    }
}