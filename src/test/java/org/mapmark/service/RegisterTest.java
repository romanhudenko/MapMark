package org.mapmark.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapmark.service.utils.DataGenerator;
import org.openqa.selenium.Alert;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.closeWebDriver;

public class RegisterTest {
    @BeforeEach
    public void openBrowser() {
        open("http://localhost:8080/");
    }

    @AfterEach
    public void closeBrowser() {
        closeWebDriver();
    }

    @Test
    public void registration() throws InterruptedException {
        String username = DataGenerator.generateUsername("en");
        $("#register_modal_open").click();
        $("#reg_username").sendKeys(username);
        $("#reg_email").sendKeys(DataGenerator.generateEmail("en"));
        $("#reg_password").sendKeys("user");
        $("#reg_password_check").sendKeys("user");
        $("#register_button").click();
        $("#username").sendKeys(username);
        $("#password").sendKeys("user");
        $("button[type=\"submit\"]").click();
        $("#logo").shouldBe(visible);
    }
    @Test
    public void existing_login_and_email() throws InterruptedException {
        $("#register_modal_open").click();
        $("#reg_username").sendKeys("user");
        $("#reg_email").sendKeys("user@mail.random");
        $("#reg_password").sendKeys("user");
        $("#reg_password_check").sendKeys("user");
        $("#register_button").click();
        Alert alert = switchTo().alert();
        String alert_text = alert.getText();
        Assertions.assertEquals(alert_text, "Ошибка регистрации");
        alert.accept();
    }
    @Test
    public void existing_login() throws InterruptedException {
        $("#register_modal_open").click();
        $("#reg_username").sendKeys("user");
        $("#reg_email").sendKeys("user1@mail.random");
        $("#reg_password").sendKeys("user");
        $("#reg_password_check").sendKeys("user");
        $("#register_button").click();
        Alert alert = switchTo().alert();
        String alert_text = alert.getText();
        Assertions.assertEquals(alert_text, "Ошибка регистрации");
        alert.accept();
    }
    @Test
    public void existing_email() throws InterruptedException {
        $("#register_modal_open").click();
        $("#reg_username").sendKeys("user1");
        $("#reg_email").sendKeys("user@mail.random");
        $("#reg_password").sendKeys("user");
        $("#reg_password_check").sendKeys("user");
        $("#register_button").click();
        Alert alert = switchTo().alert();
        String alert_text = alert.getText();
        Assertions.assertEquals(alert_text, "Ошибка регистрации");
        alert.accept();
    }
    @Test
    public void empty_login() throws InterruptedException {
        $("#register_modal_open").click();
        $("#reg_email").sendKeys("user1@mail.random");
        $("#reg_password").sendKeys("user");
        $("#reg_password_check").sendKeys("user");
        $("#register_button").click();
        Alert alert = switchTo().alert();
        String alert_text = alert.getText();
        Assertions.assertEquals(alert_text, "Неправильный логин!\n");
        alert.accept();
    }
    @Test
    public void empty_email() throws InterruptedException {
        $("#register_modal_open").click();
        $("#reg_username").sendKeys("user1");
        $("#reg_password").sendKeys("user");
        $("#reg_password_check").sendKeys("user");
        $("#register_button").click();
        Alert alert = switchTo().alert();
        String alert_text = alert.getText();
        Assertions.assertEquals(alert_text, "Неправильный адрес почты!\n");
        alert.accept();
    }
    @Test
    public void empty_password() throws InterruptedException {
        $("#register_modal_open").click();
        $("#reg_username").sendKeys("user1");
        $("#reg_email").sendKeys("user1@mail.random");
        $("#register_button").click();
        Alert alert = switchTo().alert();
        String alert_text = alert.getText();
        Assertions.assertEquals(alert_text, "Неправильный пароль или пароли не совпадают!\n");
        alert.accept();
    }
    @Test
    public void different_passwords() throws InterruptedException {
        $("#register_modal_open").click();
        $("#reg_username").sendKeys("user1");
        $("#reg_email").sendKeys("user1@mail.random");
        $("#reg_password").sendKeys("user");
        $("#reg_password_check").sendKeys("user1");
        $("#register_button").click();
        Alert alert = switchTo().alert();
        String alert_text = alert.getText();
        Assertions.assertEquals(alert_text, "Неправильный пароль или пароли не совпадают!\n");
        alert.accept();
    }
    @Test
    public void short_login() throws InterruptedException {
        $("#register_modal_open").click();
        $("#reg_username").sendKeys("us");
        $("#reg_email").sendKeys("user1@mail.random");
        $("#reg_password").sendKeys("user");
        $("#reg_password_check").sendKeys("user");
        $("#register_button").click();
        Alert alert = switchTo().alert();
        String alert_text = alert.getText();
        Assertions.assertEquals(alert_text, "Неправильный логин!\n");
        alert.accept();
    }
    @Test
    public void long_login() throws InterruptedException {
        $("#register_modal_open").click();
        $("#reg_username").sendKeys("u123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890");
        $("#reg_email").sendKeys("user1@mail.random");
        $("#reg_password").sendKeys("user");
        $("#reg_password_check").sendKeys("user");
        $("#register_button").click();
        Alert alert = switchTo().alert();
        String alert_text = alert.getText();
        Assertions.assertEquals(alert_text, "Неправильный логин!\n");
        alert.accept();
    }
    @Test
    public void short_password() throws InterruptedException {
        $("#register_modal_open").click();
        $("#reg_username").sendKeys("user1");
        $("#reg_email").sendKeys("user1@mail.random");
        $("#reg_password").sendKeys("us");
        $("#reg_password_check").sendKeys("us");
        $("#register_button").click();
        Alert alert = switchTo().alert();
        String alert_text = alert.getText();
        Assertions.assertEquals(alert_text, "Неправильный пароль или пароли не совпадают!\n");
        alert.accept();
    }
    @Test
    public void long_password() throws InterruptedException {
        $("#register_modal_open").click();
        $("#reg_username").sendKeys("user1");
        $("#reg_email").sendKeys("user1@mail.random");
        $("#reg_password").sendKeys("u123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890");
        $("#reg_password_check").sendKeys("u123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890");
        $("#register_button").click();
        Alert alert = switchTo().alert();
        String alert_text = alert.getText();
        Assertions.assertEquals(alert_text, "Неправильный пароль или пароли не совпадают!\n");
        alert.accept();
    }
    @Test
    public void spacebars_passwords() throws InterruptedException {
        $("#register_modal_open").click();
        $("#reg_username").sendKeys("user1");
        $("#reg_email").sendKeys("user1@mail.random");
        $("#reg_password").sendKeys("user");
        $("#reg_password_check").sendKeys(" user ");
        $("#register_button").click();
        Alert alert = switchTo().alert();
        String alert_text = alert.getText();
        Assertions.assertEquals(alert_text, "Неправильный пароль или пароли не совпадают!\n");
        alert.accept();
    }
}