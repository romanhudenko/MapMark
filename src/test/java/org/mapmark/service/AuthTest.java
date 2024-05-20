package org.mapmark.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;

import static com.codeborne.selenide.Selenide.open;


public class AuthTest {
    @BeforeEach
    public void openBrowser() {
        open("http://localhost:8080/");
    }

    @AfterEach
    public void closeBrowser() {
        closeWebDriver();
    }
    @Test
    public void login() {
        $("#username").sendKeys("user");
        $("#password").sendKeys("user");
        $("button[type=\"submit\"]").click();
        $x("//*[contains(text(),'Вы вошли как user')]").shouldBe(visible);
    }

    @Test
    public void login_not_existent() {
        $("#username").sendKeys("test");
        $("#password").sendKeys("test");
        $("button[type=\"submit\"]").click();
        $("#error_block").shouldBe(visible);
    }

    @Test
    public void login_caps() {
        $("#username").sendKeys("USER");
        $("#password").sendKeys("USER");
        $("button[type=\"submit\"]").click();
        $("#error_block").shouldBe(visible);
    }

    @Test
    public void empty_login() {
        $("#password").sendKeys("user");
        $("button[type=\"submit\"]").click();
        $("#error_block").shouldBe(visible);
    }

    @Test
    public void empty_password() {
        $("#username").sendKeys("user");
        $("button[type=\"submit\"]").click();
        $("#error_block").shouldBe(visible);
    }

    @Test
    public void spacebars_at_sides() {
        $("#username").sendKeys(" user ");
        $("#password").sendKeys(" user ");
        $("button[type=\"submit\"]").click();
        $("#error_block").shouldBe(visible);
    }

    @Test
    public void sql_injection() {
        $("#username").sendKeys("a' or 1=1");
        $("button[type=\"submit\"]").click();
        $("#error_block").shouldBe(visible);
    }

    @Test
    public void long_texts() {
        $("#username").sendKeys("0123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789");
        $("#password").sendKeys("0123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789");
        $("button[type=\"submit\"]").click();
        $("#error_block").shouldBe(visible);
    }
}