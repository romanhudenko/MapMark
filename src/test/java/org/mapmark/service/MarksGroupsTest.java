package org.mapmark.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapmark.service.utils.MapHelper;
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
        $("#start_group_creation_button").click();
        $("#group_name").sendKeys(name);
        $("#group_description").sendKeys("группа");
        $("#create_new_group_button").click();
        Thread.sleep(100);
        String id = "[data-id='" + executeJavaScript("return $(\"td:contains('" + name + "')\").parent().children(\"td.d-flex\")[0].lastChild.lastChild.getAttribute('data-id')") + "']";
        $(id).shouldBe(visible);
        executeJavaScript("$(\"td:contains('" + name + "')\").parent().children(\"td.d-flex\")[0].lastChild.lastChild.click()");
        $(id).shouldNotBe(visible);
    }

    @Test
    public void createGroupLongName() throws InterruptedException {
        String name = "группа012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789";
        $("#marks_groups_menu_nav_a").click();
        $("#start_group_creation_button").click();
        $("#group_name").sendKeys(name);
        $("#group_description").sendKeys("группа");
        $("#create_new_group_button").click();
        Thread.sleep(100);
        String id = "[data-id='" + executeJavaScript("return $(\"td:contains('" + name + "')\").parent().children(\"td.d-flex\")[0].lastChild.lastChild.getAttribute('data-id')") + "']";
        $(id).shouldBe(visible);
        executeJavaScript("$(\"td:contains('" + name + "')\").parent().children(\"td.d-flex\")[0].lastChild.lastChild.click()");
        $(id).shouldNotBe(visible);
    }

    @Test
    public void createGroupLongDescription() throws InterruptedException {
        String name = "группа";
        $("#marks_groups_menu_nav_a").click();
        $("#start_group_creation_button").click();
        $("#group_name").sendKeys(name);
        $("#group_description").sendKeys("группа012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789");
        $("#create_new_group_button").click();
        Thread.sleep(100);
        String id = "[data-id='" + executeJavaScript("return $(\"td:contains('" + name + "')\").parent().children(\"td.d-flex\")[0].lastChild.lastChild.getAttribute('data-id')") + "']";
        $(id).shouldBe(visible);
        executeJavaScript("$(\"td:contains('" + name + "')\").parent().children(\"td.d-flex\")[0].lastChild.lastChild.click()");
        $(id).shouldNotBe(visible);
    }

    @Test
    public void createGroupWithSpaces() throws InterruptedException {
        String name = "гр у ппа";
        $("#marks_groups_menu_nav_a").click();
        $("#start_group_creation_button").click();
        $("#group_name").sendKeys(name);
        $("#group_description").sendKeys("группа");
        $("#create_new_group_button").click();
        Thread.sleep(100);
        String id = "[data-id='" + executeJavaScript("return $(\"td:contains('" + name + "')\").parent().children(\"td.d-flex\")[0].lastChild.lastChild.getAttribute('data-id')") + "']";
        $(id).shouldBe(visible);
        executeJavaScript("$(\"td:contains('" + name + "')\").parent().children(\"td.d-flex\")[0].lastChild.lastChild.click()");
        $(id).shouldNotBe(visible);
    }

    @Test
    public void createGroupWithCaps() throws InterruptedException {
        String name = "груППа";
        $("#marks_groups_menu_nav_a").click();
        $("#start_group_creation_button").click();
        $("#group_name").sendKeys(name);
        $("#group_description").sendKeys("группа");
        $("#create_new_group_button").click();
        Thread.sleep(100);
        String id = "[data-id='" + executeJavaScript("return $(\"td:contains('" + name + "')\").parent().children(\"td.d-flex\")[0].lastChild.lastChild.getAttribute('data-id')") + "']";
        $(id).shouldBe(visible);
        executeJavaScript("$(\"td:contains('" + name + "')\").parent().children(\"td.d-flex\")[0].lastChild.lastChild.click()");
        $(id).shouldNotBe(visible);
    }

    @Test
    public void createGroupWithSameName() throws InterruptedException {
        String name = "группа";
        $("#marks_groups_menu_nav_a").click();
        $("#start_group_creation_button").click();
        $("#group_name").sendKeys(name);
        $("#group_description").sendKeys("группа");
        $("#create_new_group_button").click();
        $("#start_group_creation_button").click();
        $("#group_name").sendKeys(name);
        $("#group_description").sendKeys("группа");
        $("#create_new_group_button").click();
        Thread.sleep(100);
        String id = "[data-id='" + executeJavaScript("return $(\"td:contains('" + name + "')\").parent().children(\"td.d-flex\")[0].lastChild.lastChild.getAttribute('data-id')") + "']";
        $(id).shouldBe(visible);
        executeJavaScript("$(\"td:contains('" + name + "')\").parent().children(\"td.d-flex\")[0].lastChild.lastChild.click()");
        executeJavaScript("$(\"td:contains('" + name + "')\").parent().children(\"td.d-flex\")[0].lastChild.lastChild.click()");
        $(id).shouldNotBe(visible);
    }

    @Test
    public void createGroupWithIllegalSymbols() {
        String name = "г-р%у?п&п!а";
        $("#marks_groups_menu_nav_a").click();
        $("#start_group_creation_button").click();
        $("#group_name").sendKeys(name);
        $("#create_new_group_button").click();
        Alert alert = switchTo().alert();
        String alert_text = alert.getText();
        Assertions.assertEquals("Название группы содержит недопустимые символы!", alert_text);
    }

    @Test
    public void createGroupWithEmptyName() {
        $("#marks_groups_menu_nav_a").click();
        $("#start_group_creation_button").click();
        $("#create_new_group_button").click();
        Alert alert = switchTo().alert();
        String alert_text = alert.getText();
        Assertions.assertEquals("Название группы не может быть пустым!", alert_text);
    }

    @Test
    public void renameGroup() throws InterruptedException {
        String name = "группа";
        String new_name = "группа1";
        $("#marks_groups_menu_nav_a").click();
        $("#start_group_creation_button").click();
        $("#group_name").sendKeys(name);
        $("#group_description").sendKeys("группа");
        $("#create_new_group_button").click();
        Thread.sleep(100);
        String id = "[data-id='" + executeJavaScript("return $(\"td:contains('" + name + "')\").parent().children(\"td.d-flex\")[0].lastChild.lastChild.getAttribute('data-id')") + "']";
        $(id).shouldBe(visible);
        executeJavaScript("$(\"td:contains('" + name + "')\").parent().children(\"td.d-flex\")[0].children.item(0).children.item(2).click()");
        $("#edit_group_name").clear();
        $("#edit_group_name").sendKeys(new_name);
        $("#edit_group_description").clear();
        $("#edit_group_description").sendKeys("группа1");
        $("#update_group_button").click();
        Thread.sleep(100);
        id = "[data-id='" + executeJavaScript("return $(\"td:contains('" + new_name + "')\").parent().children(\"td.d-flex\")[0].lastChild.lastChild.getAttribute('data-id')") + "']";
        $(id).shouldBe(visible);
        executeJavaScript("$(\"td:contains('" + new_name + "')\").parent().children(\"td.d-flex\")[0].lastChild.lastChild.click()");
    }

    @Test
    public void renameGroupLongName() throws InterruptedException {
        String name = "группа";
        String new_name = "группа012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789";
        $("#marks_groups_menu_nav_a").click();
        $("#start_group_creation_button").click();
        $("#group_name").sendKeys(name);
        $("#group_description").sendKeys("группа");
        $("#create_new_group_button").click();
        Thread.sleep(100);
        String id = "[data-id='" + executeJavaScript("return $(\"td:contains('" + name + "')\").parent().children(\"td.d-flex\")[0].lastChild.lastChild.getAttribute('data-id')") + "']";
        $(id).shouldBe(visible);
        executeJavaScript("$(\"td:contains('" + name + "')\").parent().children(\"td.d-flex\")[0].children.item(0).children.item(2).click()");
        $("#edit_group_name").clear();
        $("#edit_group_name").sendKeys(new_name);
        $("#edit_group_description").clear();
        $("#edit_group_description").sendKeys("группа1");
        $("#update_group_button").click();
        Thread.sleep(100);
        id = "[data-id='" + executeJavaScript("return $(\"td:contains('" + new_name + "')\").parent().children(\"td.d-flex\")[0].lastChild.lastChild.getAttribute('data-id')") + "']";
        $(id).shouldBe(visible);
        executeJavaScript("$(\"td:contains('" + new_name + "')\").parent().children(\"td.d-flex\")[0].lastChild.lastChild.click()");
    }

    @Test
    public void renameGroupLongDescription() throws InterruptedException {
        String name = "группа";
        $("#marks_groups_menu_nav_a").click();
        $("#start_group_creation_button").click();
        $("#group_name").sendKeys(name);
        $("#group_description").sendKeys("группа");
        $("#create_new_group_button").click();
        Thread.sleep(100);
        String id = "[data-id='" + executeJavaScript("return $(\"td:contains('" + name + "')\").parent().children(\"td.d-flex\")[0].lastChild.lastChild.getAttribute('data-id')") + "']";
        $(id).shouldBe(visible);
        executeJavaScript("$(\"td:contains('" + name + "')\").parent().children(\"td.d-flex\")[0].children.item(0).children.item(2).click()");
        $("#edit_group_name").clear();
        $("#edit_group_name").sendKeys(name);
        $("#edit_group_description").clear();
        $("#edit_group_description").sendKeys("группа012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789");
        $("#update_group_button").click();
        Thread.sleep(100);
        id = "[data-id='" + executeJavaScript("return $(\"td:contains('" + name + "')\").parent().children(\"td.d-flex\")[0].lastChild.lastChild.getAttribute('data-id')") + "']";
        $(id).shouldBe(visible);
        executeJavaScript("$(\"td:contains('" + name + "')\").parent().children(\"td.d-flex\")[0].lastChild.lastChild.click()");
    }

    @Test
    public void renameGroupSpaces() throws InterruptedException {
        String name = "группа";
        String new_name = "гр у ппа";
        $("#marks_groups_menu_nav_a").click();
        $("#start_group_creation_button").click();
        $("#group_name").sendKeys(name);
        $("#group_description").sendKeys("группа");
        $("#create_new_group_button").click();
        Thread.sleep(100);
        String id = "[data-id='" + executeJavaScript("return $(\"td:contains('" + name + "')\").parent().children(\"td.d-flex\")[0].lastChild.lastChild.getAttribute('data-id')") + "']";
        $(id).shouldBe(visible);
        executeJavaScript("$(\"td:contains('" + name + "')\").parent().children(\"td.d-flex\")[0].children.item(0).children.item(2).click()");
        $("#edit_group_name").clear();
        $("#edit_group_name").sendKeys(new_name);
        $("#edit_group_description").clear();
        $("#edit_group_description").sendKeys("группа1");
        $("#update_group_button").click();
        Thread.sleep(100);
        id = "[data-id='" + executeJavaScript("return $(\"td:contains('" + new_name + "')\").parent().children(\"td.d-flex\")[0].lastChild.lastChild.getAttribute('data-id')") + "']";
        $(id).shouldBe(visible);
        executeJavaScript("$(\"td:contains('" + new_name + "')\").parent().children(\"td.d-flex\")[0].lastChild.lastChild.click()");
    }

    @Test
    public void renameGroupCaps() throws InterruptedException {
        String name = "группа";
        String new_name = "ГРУППА";
        $("#marks_groups_menu_nav_a").click();
        $("#start_group_creation_button").click();
        $("#group_name").sendKeys(name);
        $("#group_description").sendKeys("группа");
        $("#create_new_group_button").click();
        Thread.sleep(100);
        String id = "[data-id='" + executeJavaScript("return $(\"td:contains('" + name + "')\").parent().children(\"td.d-flex\")[0].lastChild.lastChild.getAttribute('data-id')") + "']";
        $(id).shouldBe(visible);
        executeJavaScript("$(\"td:contains('" + name + "')\").parent().children(\"td.d-flex\")[0].children.item(0).children.item(2).click()");
        $("#edit_group_name").clear();
        $("#edit_group_name").sendKeys(new_name);
        $("#edit_group_description").clear();
        $("#edit_group_description").sendKeys("группа1");
        $("#update_group_button").click();
        Thread.sleep(100);
        id = "[data-id='" + executeJavaScript("return $(\"td:contains('" + new_name + "')\").parent().children(\"td.d-flex\")[0].lastChild.lastChild.getAttribute('data-id')") + "']";
        $(id).shouldBe(visible);
        executeJavaScript("$(\"td:contains('" + new_name + "')\").parent().children(\"td.d-flex\")[0].lastChild.lastChild.click()");
    }

    @Test
    public void renameGroupSameGroup() throws InterruptedException {
        String name_1 = "группа1";
        String name_2 = "группа2";
        $("#marks_groups_menu_nav_a").click();
        $("#start_group_creation_button").click();
        $("#group_name").sendKeys(name_1);
        $("#group_description").sendKeys("группа");
        $("#create_new_group_button").click();
        $("#start_group_creation_button").click();
        $("#group_name").sendKeys(name_2);
        $("#group_description").sendKeys("группа");
        $("#create_new_group_button").click();
        Thread.sleep(1000);
        String id = "[data-id='" + executeJavaScript("return $(\"td:contains('" + name_2 + "')\").parent().children(\"td.d-flex\")[0].lastChild.lastChild.getAttribute('data-id')") + "']";
        $(id).shouldBe(visible);
        executeJavaScript("$(\"td:contains('" + name_2 + "')\").parent().children(\"td.d-flex\")[0].children.item(0).children.item(2).click()");
        $("#edit_group_name").clear();
        $("#edit_group_name").sendKeys(name_1);
        $("#update_group_button").click();
        Thread.sleep(100);
        id = "[data-id='" + executeJavaScript("return $(\"td:contains('" + name_1 + "')\").parent().children(\"td.d-flex\")[0].lastChild.lastChild.getAttribute('data-id')") + "']";
        $(id).shouldBe(visible);
        executeJavaScript("$(\"td:contains('" + name_1 + "')\").parent().children(\"td.d-flex\")[0].lastChild.lastChild.click()");
        executeJavaScript("$(\"td:contains('" + name_1 + "')\").parent().children(\"td.d-flex\")[0].lastChild.lastChild.click()");
    }

    @Test
    public void renameGroupSymbols() throws InterruptedException {
        String name = "группа";
        String new_name = "г-р%у?п&п!а";
        $("#marks_groups_menu_nav_a").click();
        $("#start_group_creation_button").click();
        $("#group_name").sendKeys(name);
        $("#group_description").sendKeys("группа");
        $("#create_new_group_button").click();
        Thread.sleep(100);
        String id = "[data-id='" + executeJavaScript("return $(\"td:contains('" + name + "')\").parent().children(\"td.d-flex\")[0].lastChild.lastChild.getAttribute('data-id')") + "']";
        $(id).shouldBe(visible);
        executeJavaScript("$(\"td:contains('" + name + "')\").parent().children(\"td.d-flex\")[0].children.item(0).children.item(2).click()");
        $("#edit_group_name").clear();
        $("#edit_group_name").sendKeys(new_name);
        $("#update_group_button").click();
        Alert alert = switchTo().alert();
        String alert_text = alert.getText();
        Assertions.assertEquals("Название группы содержит недопустимые символы!", alert_text);
        alert.accept();
        $("#cancel_update_group_button").click();
        Thread.sleep(100);
        id = "[data-id='" + executeJavaScript("return $(\"td:contains('" + name + "')\").parent().children(\"td.d-flex\")[0].lastChild.lastChild.getAttribute('data-id')") + "']";
        $(id).shouldBe(visible);
        executeJavaScript("$(\"td:contains('" + name + "')\").parent().children(\"td.d-flex\")[0].lastChild.lastChild.click()");
    }

    @Test
    public void renameGroupEmptyName() throws InterruptedException {
        String name = "группа";
        $("#marks_groups_menu_nav_a").click();
        $("#start_group_creation_button").click();
        $("#group_name").sendKeys(name);
        $("#group_description").sendKeys("группа");
        $("#create_new_group_button").click();
        Thread.sleep(100);
        String id = "[data-id='" + executeJavaScript("return $(\"td:contains('" + name + "')\").parent().children(\"td.d-flex\")[0].lastChild.lastChild.getAttribute('data-id')") + "']";
        $(id).shouldBe(visible);
        executeJavaScript("$(\"td:contains('" + name + "')\").parent().children(\"td.d-flex\")[0].children.item(0).children.item(2).click()");
        $("#edit_group_name").clear();
        $("#edit_group_description").clear();
        $("#update_group_button").click();
        Alert alert = switchTo().alert();
        String alert_text = alert.getText();
        Assertions.assertEquals("Название группы не может быть пустым!", alert_text);
        alert.accept();
        $("#cancel_update_group_button").click();
        Thread.sleep(100);
        id = "[data-id='" + executeJavaScript("return $(\"td:contains('" + name + "')\").parent().children(\"td.d-flex\")[0].lastChild.lastChild.getAttribute('data-id')") + "']";
        $(id).shouldBe(visible);
        executeJavaScript("$(\"td:contains('" + name + "')\").parent().children(\"td.d-flex\")[0].lastChild.lastChild.click()");
    }

    @Test
    public void addMarkToGroup() throws InterruptedException {
        MapHelper.waitForMap();
        String group_name = "группа";
        String mark_name = "метка";
        $("#map").doubleClick();
        MapHelper.waitForInterface();
        $("#mark_name").sendKeys(mark_name);
        $("#create_new_mark_button").click();
        $("#marks_groups_menu_nav_a").click();
        $("#start_group_creation_button").click();
        $("#group_name").sendKeys(group_name);
        $("#group_description").sendKeys("группа");
        $("#create_new_group_button").click();
        MapHelper.waitForInterface();
        executeJavaScript("$(\"td:contains('" + group_name + "')\").parent().children(\"td.d-flex\")[0].children.item(0).children.item(1).click()");
        MapHelper.waitForInterface();
        $("#add_mark_to_group_button").click();
        $("[data-type='delete_mark_from_group']").shouldBe(visible);
        MapHelper.waitForInterface();
        $("#group_marks_modal_close_button").click();
        MapHelper.waitForInterface();
        executeJavaScript("$(\"td:contains('" + group_name + "')\").parent().children(\"td.d-flex\")[0].lastChild.lastChild.click()");
        $("#groups_modal_close_button").click();
        MapHelper.removeDefaultMark();
    }

    @Test
    public void removeMarkFromGroup() throws InterruptedException {
        MapHelper.waitForMap();
        String group_name = "группа";
        String mark_name = "метка";
        $("#map").doubleClick();
        MapHelper.waitForInterface();
        $("#mark_name").sendKeys(mark_name);
        $("#create_new_mark_button").click();
        $("#marks_groups_menu_nav_a").click();
        $("#start_group_creation_button").click();
        $("#group_name").sendKeys(group_name);
        $("#group_description").sendKeys("группа");
        $("#create_new_group_button").click();
        MapHelper.waitForInterface();
        executeJavaScript("$(\"td:contains('" + group_name + "')\").parent().children(\"td.d-flex\")[0].children.item(0).children.item(1).click()");
        MapHelper.waitForInterface();
        $("#add_mark_to_group_button").click();
        $("[data-type='delete_mark_from_group']").click();
        $("[data-type='delete_mark_from_group']").shouldNotBe(visible);
        MapHelper.waitForInterface();
        $("#group_marks_modal_close_button").click();
        MapHelper.waitForInterface();
        executeJavaScript("$(\"td:contains('" + group_name + "')\").parent().children(\"td.d-flex\")[0].lastChild.lastChild.click()");
        $("#groups_modal_close_button").click();
        MapHelper.removeDefaultMark();
    }

    @Test
    public void addNotExistingMarkToGroup() throws InterruptedException {
        String group_name = "группа";
        MapHelper.waitForInterface();
        $("#marks_groups_menu_nav_a").click();
        $("#start_group_creation_button").click();
        $("#group_name").sendKeys(group_name);
        $("#group_description").sendKeys("группа");
        $("#create_new_group_button").click();
        MapHelper.waitForInterface();
        executeJavaScript("$(\"td:contains('" + group_name + "')\").parent().children(\"td.d-flex\")[0].children.item(0).children.item(1).click()");
        MapHelper.waitForInterface();
        $("#add_mark_to_group_button").click();
        $("[data-type='delete_mark_from_group']").shouldNotBe(visible);
        MapHelper.waitForInterface();
        $("#group_marks_modal_close_button").click();
        MapHelper.waitForInterface();
        executeJavaScript("$(\"td:contains('" + group_name + "')\").parent().children(\"td.d-flex\")[0].lastChild.lastChild.click()");
    }
}
