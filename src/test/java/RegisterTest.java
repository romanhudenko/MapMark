import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Alert;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.closeWebDriver;

public class RegisterTest {
    @Test
    public void existing_login_and_email() throws InterruptedException {
        open("http://localhost:8080/");
        $("#register_modal_open").click();
        $("#reg_username").sendKeys("user");
        $("#reg_email").sendKeys("user@mail.random");
        $("#reg_password").sendKeys("user");
        $("#reg_password_check").sendKeys("user");
        $("#register_button").click();
        Alert alert = switchTo().alert();
        String alert_text = alert.getText();
        Thread.sleep(1000);
        Assertions.assertEquals(alert_text, "Ошибка регистрации");
        Thread.sleep(1000);
        alert.accept();
        closeWebDriver();
    }
    @Test
    public void existing_login() throws InterruptedException {
        open("http://localhost:8080/");
        $("#register_modal_open").click();
        $("#reg_username").sendKeys("user");
        $("#reg_email").sendKeys("user1@mail.random");
        $("#reg_password").sendKeys("user");
        $("#reg_password_check").sendKeys("user");
        $("#register_button").click();
        Alert alert = switchTo().alert();
        String alert_text = alert.getText();
        Thread.sleep(1000);
        Assertions.assertEquals(alert_text, "Ошибка регистрации");
        Thread.sleep(1000);
        alert.accept();
        closeWebDriver();
    }
    @Test
    public void existing_email() throws InterruptedException {
        open("http://localhost:8080/");
        $("#register_modal_open").click();
        $("#reg_username").sendKeys("user1");
        $("#reg_email").sendKeys("user@mail.random");
        $("#reg_password").sendKeys("user");
        $("#reg_password_check").sendKeys("user");
        $("#register_button").click();
        Alert alert = switchTo().alert();
        String alert_text = alert.getText();
        Thread.sleep(1000);
        Assertions.assertEquals(alert_text, "Ошибка регистрации");
        Thread.sleep(1000);
        alert.accept();
        closeWebDriver();
    }
    @Test
    public void empty_login() throws InterruptedException {
        open("http://localhost:8080/");
        $("#register_modal_open").click();
        $("#reg_email").sendKeys("user1@mail.random");
        $("#reg_password").sendKeys("user");
        $("#reg_password_check").sendKeys("user");
        $("#register_button").click();
        Alert alert = switchTo().alert();
        String alert_text = alert.getText();
        Thread.sleep(1000);
        Assertions.assertEquals(alert_text, "Неправильный логин!\n");
        Thread.sleep(1000);
        alert.accept();
        closeWebDriver();
    }
    @Test
    public void empty_email() throws InterruptedException {
        open("http://localhost:8080/");
        $("#register_modal_open").click();
        $("#reg_username").sendKeys("user1");
        $("#reg_password").sendKeys("user");
        $("#reg_password_check").sendKeys("user");
        $("#register_button").click();
        Alert alert = switchTo().alert();
        String alert_text = alert.getText();
        Thread.sleep(1000);
        Assertions.assertEquals(alert_text, "Неправильный адрес почты!\n");
        Thread.sleep(1000);
        alert.accept();
        closeWebDriver();
    }
    @Test
    public void empty_password() throws InterruptedException {
        open("http://localhost:8080/");
        $("#register_modal_open").click();
        $("#reg_username").sendKeys("user1");
        $("#reg_email").sendKeys("user1@mail.random");
        $("#register_button").click();
        Alert alert = switchTo().alert();
        String alert_text = alert.getText();
        Thread.sleep(1000);
        Assertions.assertEquals(alert_text, "Неправильный пароль или пароли не совпадают!\n");
        Thread.sleep(1000);
        alert.accept();
        closeWebDriver();
    }
    @Test
    public void different_passwords() throws InterruptedException {
        open("http://localhost:8080/");
        $("#register_modal_open").click();
        $("#reg_username").sendKeys("user1");
        $("#reg_email").sendKeys("user1@mail.random");
        $("#reg_password").sendKeys("user");
        $("#reg_password_check").sendKeys("user1");
        $("#register_button").click();
        Alert alert = switchTo().alert();
        String alert_text = alert.getText();
        Thread.sleep(1000);
        Assertions.assertEquals(alert_text, "Неправильный пароль или пароли не совпадают!\n");
        Thread.sleep(1000);
        alert.accept();
        closeWebDriver();
    }
    @Test
    public void short_login() throws InterruptedException {
        open("http://localhost:8080/");
        $("#register_modal_open").click();
        $("#reg_username").sendKeys("us");
        $("#reg_email").sendKeys("user1@mail.random");
        $("#reg_password").sendKeys("user");
        $("#reg_password_check").sendKeys("user");
        $("#register_button").click();
        Alert alert = switchTo().alert();
        String alert_text = alert.getText();
        Thread.sleep(1000);
        Assertions.assertEquals(alert_text, "Неправильный логин!\n");
        Thread.sleep(1000);
        alert.accept();
        closeWebDriver();
    }
    @Test
    public void long_login() throws InterruptedException {
        open("http://localhost:8080/");
        $("#register_modal_open").click();
        $("#reg_username").sendKeys("u123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890");
        $("#reg_email").sendKeys("user1@mail.random");
        $("#reg_password").sendKeys("user");
        $("#reg_password_check").sendKeys("user");
        $("#register_button").click();
        Alert alert = switchTo().alert();
        String alert_text = alert.getText();
        Thread.sleep(1000);
        Assertions.assertEquals(alert_text, "Неправильный логин!\n");
        Thread.sleep(1000);
        alert.accept();
        closeWebDriver();
    }
    @Test
    public void short_password() throws InterruptedException {
        open("http://localhost:8080/");
        $("#register_modal_open").click();
        $("#reg_username").sendKeys("user1");
        $("#reg_email").sendKeys("user1@mail.random");
        $("#reg_password").sendKeys("us");
        $("#reg_password_check").sendKeys("us");
        $("#register_button").click();
        Alert alert = switchTo().alert();
        String alert_text = alert.getText();
        Thread.sleep(1000);
        Assertions.assertEquals(alert_text, "Неправильный пароль или пароли не совпадают!\n");
        Thread.sleep(1000);
        alert.accept();
        closeWebDriver();
    }
    @Test
    public void long_password() throws InterruptedException {
        open("http://localhost:8080/");
        $("#register_modal_open").click();
        $("#reg_username").sendKeys("user1");
        $("#reg_email").sendKeys("user1@mail.random");
        $("#reg_password").sendKeys("u123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890");
        $("#reg_password_check").sendKeys("u123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890");
        $("#register_button").click();
        Alert alert = switchTo().alert();
        String alert_text = alert.getText();
        Thread.sleep(1000);
        Assertions.assertEquals(alert_text, "Неправильный пароль или пароли не совпадают!\n");
        Thread.sleep(1000);
        alert.accept();
        closeWebDriver();
    }
    @Test
    public void spacebars_passwords() throws InterruptedException {
        open("http://localhost:8080/");
        $("#register_modal_open").click();
        $("#reg_username").sendKeys("user1");
        $("#reg_email").sendKeys("user1@mail.random");
        $("#reg_password").sendKeys("user");
        $("#reg_password_check").sendKeys(" user ");
        $("#register_button").click();
        Alert alert = switchTo().alert();
        String alert_text = alert.getText();
        Thread.sleep(1000);
        Assertions.assertEquals(alert_text, "Неправильный пароль или пароли не совпадают!\n");
        Thread.sleep(1000);
        alert.accept();
        closeWebDriver();
    }
}