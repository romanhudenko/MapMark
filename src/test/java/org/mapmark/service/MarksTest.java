package org.mapmark.service;

import com.codeborne.selenide.ClickMethod;
import com.codeborne.selenide.ClickOptions;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Alert;
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
        Thread.sleep(1000);
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

    @Test
    public void createMarkFromMenu() {
        $("#marks_menu_nav_a").click();
        $("#start_mark_creation_button").click();
        Alert alert = switchTo().alert();
        String alert_text = alert.getText();
        Assertions.assertEquals(alert_text, "Дважды щелкните на карте для создания метки по указанным координатам.");
        alert.accept();
    }

    @Test
    public void createMarkWithLongName() throws InterruptedException {
        Thread.sleep(1000);
        WebElement element = $("#map");
        $("#map").doubleClick();
        Thread.sleep(1000);
        $("#mark_name").sendKeys("0123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789");
        $("#create_new_mark_button").click();
        Thread.sleep(1000);
        $("#map").click();
        $("#choose_action_modal_label").shouldBe(visible);
        $("#delete_editing_mark_button").click();
        Thread.sleep(100);
        Selenide.switchTo().alert().accept();
    }

    @Test
    public void createMarkWithSpaces() throws InterruptedException {
        Thread.sleep(1000);
        WebElement element = $("#map");
        $("#map").doubleClick();
        Thread.sleep(1000);
        $("#mark_name").sendKeys("ме т ка");
        $("#create_new_mark_button").click();
        Thread.sleep(1000);
        $("#map").click();
        $("#choose_action_modal_label").shouldBe(visible);
        $("#delete_editing_mark_button").click();
        Thread.sleep(100);
        Selenide.switchTo().alert().accept();
    }

    @Test
    public void createMarkWithCaps() throws InterruptedException {
        Thread.sleep(1000);
        WebElement element = $("#map");
        $("#map").doubleClick();
        Thread.sleep(1000);
        $("#mark_name").sendKeys("МеТкА");
        $("#create_new_mark_button").click();
        Thread.sleep(1000);
        $("#map").click();
        $("#choose_action_modal_label").shouldBe(visible);
        $("#delete_editing_mark_button").click();
        Thread.sleep(100);
        Selenide.switchTo().alert().accept();
    }

    @Test
    public void createMarkWithSameName() throws InterruptedException {
        Thread.sleep(1000);
        WebElement element = $("#map");
        $("#map").doubleClick();
        Thread.sleep(1000);
        $("#mark_name").sendKeys("mark");
        $("#create_new_mark_button").click();
        Thread.sleep(1000);
        $("#map").doubleClick(ClickOptions.withOffset(100, 100));
        Thread.sleep(1000);
        $("#mark_name").sendKeys("mark");
        $("#create_new_mark_button").click();
        Thread.sleep(100);
        open("http://localhost:8080/app.html");
        Thread.sleep(1000);
        $("#map").click(ClickOptions.withOffset(100, 100));
        $("#choose_action_modal_label").shouldBe(visible);
        $("#delete_editing_mark_button").click();
        Thread.sleep(1000);
        Selenide.switchTo().alert().accept();
        Thread.sleep(1000);
        open("http://localhost:8080/app.html");
        Thread.sleep(1000);
        $("#map").click();
        $("#choose_action_modal_label").shouldBe(visible);
        $("#delete_editing_mark_button").click();
        Thread.sleep(1000);
        Selenide.switchTo().alert().accept();
        Thread.sleep(1000);
    }
}
