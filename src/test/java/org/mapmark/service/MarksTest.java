package org.mapmark.service;

import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebElement;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.$;

public class MarksTest{
    @BeforeEach
    public void openBrowser() {
        open("http://localhost:8080/");
        $("#username").sendKeys("user");
        $("#password").sendKeys("user");
        $("button[type=\"submit\"]").click();
    }

    @AfterEach
    public void closeBrowser() {
        closeWebDriver();
    }

    @Test
    public void createMark() throws InterruptedException {
        WebElement element = $("#map");
        $("#map").doubleClick();
        Thread.sleep(1000);
        $("#mark_name").sendKeys("test");
        $("#create_new_mark_button").click();
        Thread.sleep(1000);
        $("#map").click();
        $("#choose_action_modal_label").shouldBe(visible);
        $("#delete_editing_mark_button").click();
        Thread.sleep(100);
        Selenide.switchTo().alert().accept();
    }
}
