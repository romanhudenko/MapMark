package org.mapmark.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Alert;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class MarksGroupsTest {

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
    public void createGroup() throws InterruptedException {
        String name = "группа";
        $("#marks_groups_menu_nav_a").click();
        Thread.sleep(100);
        $("#start_group_creation_button").click();
        Thread.sleep(100);
        $("#group_name").sendKeys(name);
        $("#group_description").sendKeys("группа");
        $("#create_new_group_button").click();
        Thread.sleep(100);
        String id = "[data-id='" + executeJavaScript("return $(\"td:contains('" + name + "')\").parent().children(\"td.d-flex\")[0].lastChild.lastChild.getAttribute('data-id')") + "']";
        $(id).shouldBe(visible);
        Thread.sleep(100);
        executeJavaScript("$(\"td:contains('" + name + "')\").parent().children(\"td.d-flex\")[0].lastChild.lastChild.click()");
        $(id).shouldNotBe(visible);
    }

    @Test
    public void createGroupLongName() throws InterruptedException {
        String name = "группа012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789";
        $("#marks_groups_menu_nav_a").click();
        Thread.sleep(100);
        $("#start_group_creation_button").click();
        Thread.sleep(100);
        $("#group_name").sendKeys(name);
        $("#group_description").sendKeys("группа");
        $("#create_new_group_button").click();
        Thread.sleep(100);
        String id = "[data-id='" + executeJavaScript("return $(\"td:contains('" + name + "')\").parent().children(\"td.d-flex\")[0].lastChild.lastChild.getAttribute('data-id')") + "']";
        $(id).shouldBe(visible);
        Thread.sleep(100);
        executeJavaScript("$(\"td:contains('" + name + "')\").parent().children(\"td.d-flex\")[0].lastChild.lastChild.click()");
        $(id).shouldNotBe(visible);
    }

    @Test
    public void createGroupLongDescription() throws InterruptedException {
        String name = "группа";
        $("#marks_groups_menu_nav_a").click();
        Thread.sleep(100);
        $("#start_group_creation_button").click();
        Thread.sleep(100);
        $("#group_name").sendKeys(name);
        $("#group_description").sendKeys("группа012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789");
        $("#create_new_group_button").click();
        Thread.sleep(100);
        String id = "[data-id='" + executeJavaScript("return $(\"td:contains('" + name + "')\").parent().children(\"td.d-flex\")[0].lastChild.lastChild.getAttribute('data-id')") + "']";
        $(id).shouldBe(visible);
        Thread.sleep(100);
        executeJavaScript("$(\"td:contains('" + name + "')\").parent().children(\"td.d-flex\")[0].lastChild.lastChild.click()");
        $(id).shouldNotBe(visible);
    }

    @Test
    public void createGroupWithSpaces() throws InterruptedException {
        String name = "гр у ппа";
        $("#marks_groups_menu_nav_a").click();
        Thread.sleep(100);
        $("#start_group_creation_button").click();
        Thread.sleep(100);
        $("#group_name").sendKeys(name);
        $("#group_description").sendKeys("группа");
        $("#create_new_group_button").click();
        Thread.sleep(100);
        String id = "[data-id='" + executeJavaScript("return $(\"td:contains('" + name + "')\").parent().children(\"td.d-flex\")[0].lastChild.lastChild.getAttribute('data-id')") + "']";
        $(id).shouldBe(visible);
        Thread.sleep(100);
        executeJavaScript("$(\"td:contains('" + name + "')\").parent().children(\"td.d-flex\")[0].lastChild.lastChild.click()");
        $(id).shouldNotBe(visible);
    }

    @Test
    public void createGroupWithCaps() throws InterruptedException {
        String name = "груППа";
        $("#marks_groups_menu_nav_a").click();
        Thread.sleep(100);
        $("#start_group_creation_button").click();
        Thread.sleep(100);
        $("#group_name").sendKeys(name);
        $("#group_description").sendKeys("группа");
        $("#create_new_group_button").click();
        Thread.sleep(100);
        String id = "[data-id='" + executeJavaScript("return $(\"td:contains('" + name + "')\").parent().children(\"td.d-flex\")[0].lastChild.lastChild.getAttribute('data-id')") + "']";
        $(id).shouldBe(visible);
        Thread.sleep(100);
        executeJavaScript("$(\"td:contains('" + name + "')\").parent().children(\"td.d-flex\")[0].lastChild.lastChild.click()");
        $(id).shouldNotBe(visible);
    }

    @Test
    public void createGroupWithSameName() throws InterruptedException {
        String name = "группа";
        $("#marks_groups_menu_nav_a").click();
        Thread.sleep(100);
        $("#start_group_creation_button").click();
        Thread.sleep(100);
        $("#group_name").sendKeys(name);
        $("#group_description").sendKeys("группа");
        $("#create_new_group_button").click();
        Thread.sleep(100);
        $("#start_group_creation_button").click();
        Thread.sleep(100);
        $("#group_name").sendKeys(name);
        $("#group_description").sendKeys("группа");
        $("#create_new_group_button").click();
        Thread.sleep(100);
        String id = "[data-id='" + executeJavaScript("return $(\"td:contains('" + name + "')\").parent().children(\"td.d-flex\")[0].lastChild.lastChild.getAttribute('data-id')") + "']";
        $(id).shouldBe(visible);
        Thread.sleep(100);
        executeJavaScript("$(\"td:contains('" + name + "')\").parent().children(\"td.d-flex\")[0].lastChild.lastChild.click()");
        executeJavaScript("$(\"td:contains('" + name + "')\").parent().children(\"td.d-flex\")[0].lastChild.lastChild.click()");
        $(id).shouldNotBe(visible);
    }

    @Test
    public void createGroupWithIllegalSymbols() throws InterruptedException {
        String name = "г-р%у?п&п!а";
        $("#marks_groups_menu_nav_a").click();
        Thread.sleep(100);
        $("#start_group_creation_button").click();
        Thread.sleep(100);
        $("#group_name").sendKeys(name);
        $("#create_new_group_button").click();
        Alert alert = switchTo().alert();
        String alert_text = alert.getText();
        Assertions.assertEquals("Название группы содержит недопустимые символы!", alert_text);
    }

    @Test
    public void createGroupWithEmptyName() throws InterruptedException {
        $("#marks_groups_menu_nav_a").click();
        Thread.sleep(100);
        $("#start_group_creation_button").click();
        Thread.sleep(100);
        $("#create_new_group_button").click();
        Alert alert = switchTo().alert();
        String alert_text = alert.getText();
        Assertions.assertEquals("Название группы не может быть пустым!", alert_text);
    }
}
