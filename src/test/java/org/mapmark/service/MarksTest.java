package org.mapmark.service;

import com.codeborne.selenide.ClickOptions;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Alert;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

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
        $("#map").doubleClick();
        Thread.sleep(1000);
        $("#mark_name").sendKeys("test");
        $("#create_new_mark_button").click();
        remove_default_mark();
    }

    @Test
    public void createMarkFromMenu() {
        $("#marks_menu_nav_a").click();
        $("#start_mark_creation_button").click();
        Alert alert = switchTo().alert();
        String alert_text = alert.getText();
        Assertions.assertEquals("Дважды щелкните на карте для создания метки по указанным координатам.", alert_text);
        alert.accept();
    }

    @Test
    public void createMarkWithLongName() throws InterruptedException {
        Thread.sleep(1000);
        $("#map").doubleClick();
        Thread.sleep(1000);
        $("#mark_name").sendKeys("0123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789");
        $("#create_new_mark_button").click();
        remove_default_mark();
    }

    @Test
    public void createMarkWithSpaces() throws InterruptedException {
        Thread.sleep(1000);
        $("#map").doubleClick();
        Thread.sleep(1000);
        $("#mark_name").sendKeys("ме т ка");
        $("#create_new_mark_button").click();
        remove_default_mark();
    }

    @Test
    public void createMarkWithCaps() throws InterruptedException {
        Thread.sleep(1000);
        $("#map").doubleClick();
        Thread.sleep(1000);
        $("#mark_name").sendKeys("МеТкА");
        $("#create_new_mark_button").click();
        remove_default_mark();
    }

    @Test
    public void createMarkWithSameName() throws InterruptedException {
        Thread.sleep(1000);
        int offset = 10;
        $("#map").doubleClick();
        Thread.sleep(1000);
        $("#mark_name").sendKeys("mark");
        $("#create_new_mark_button").click();
        Thread.sleep(1000);
        $("#map").doubleClick(ClickOptions.withOffset(offset, offset));
        Thread.sleep(1000);
        $("#mark_name").sendKeys("mark");
        $("#create_new_mark_button").click();
        Thread.sleep(100);
        open("http://localhost:8080/app.html");
        Thread.sleep(1000);
        $("#map").click(ClickOptions.withOffset(offset, offset));
        $("#choose_action_modal_label").shouldBe(visible);
        $("#delete_editing_mark_button").click();
        Thread.sleep(1000);
        Selenide.switchTo().alert().accept();
        Thread.sleep(1000);
        open("http://localhost:8080/app.html");
        remove_default_mark();
    }

    @Test
    public void createMarkWithDifferentColor() throws InterruptedException {
        Thread.sleep(1000);
        $("#map").doubleClick();
        Thread.sleep(1000);
        $("#mark_name").sendKeys("test");
        Thread.sleep(1000);
        executeJavaScript("document.getElementById(\"mark_color\").setAttribute(\"value\", \"#f66151\");");
        Thread.sleep(1000);
        $("#create_new_mark_button").click();
        Thread.sleep(1000);
        $("#map").click();
        $("#choose_action_modal_label").shouldBe(visible);
        $("#edit_mark_button").click();
        Assertions.assertEquals("#f66151", $("#mark_color").getAttribute("value"));
        Thread.sleep(1000);
        $("#update_mark_button").click();
        $("#delete_editing_mark_button").click();
        Thread.sleep(1000);
        Selenide.switchTo().alert().accept();
    }

    public void remove_default_mark() throws InterruptedException {
        Thread.sleep(1000);
        $("#map").click();
        $("#choose_action_modal_label").shouldBe(visible);
        $("#delete_editing_mark_button").click();
        Thread.sleep(100);
        Selenide.switchTo().alert().accept();
    }

    @Test
    public void createMarkWithForbiddenSymbols() throws InterruptedException {
        Thread.sleep(1000);
        $("#map").doubleClick();
        Thread.sleep(1000);
        $("#mark_name").sendKeys("м-е%т?к&а");
        $("#create_new_mark_button").click();
        Alert alert = switchTo().alert();
        String alert_text = alert.getText();
        Assertions.assertEquals("Название метки содержит недопустимые символы!", alert_text);
        alert.accept();
    }

    @Test
    public void createMarkWithEmptyName() throws InterruptedException {
        Thread.sleep(1000);
        $("#map").doubleClick();
        Thread.sleep(1000);
        $("#create_new_mark_button").click();
        Alert alert = switchTo().alert();
        String alert_text = alert.getText();
        Assertions.assertEquals("Название метки не может быть пустым!", alert_text);
        alert.accept();
    }

    @Test
    public void moveMark() throws InterruptedException {
        Thread.sleep(1000);
        int offset = 100;
        $("#map").doubleClick();
        Thread.sleep(1000);
        $("#mark_name").sendKeys("mark");
        $("#create_new_mark_button").click();
        Thread.sleep(1000);
        $("#map").click();
        Thread.sleep(100);
        $("#move_mark_request_button").click();
        Thread.sleep(100);
        Selenide.switchTo().alert().accept();
        Thread.sleep(100);
        $("#map").doubleClick(ClickOptions.withOffset(offset, offset));
        Thread.sleep(1000);
        open("http://localhost:8080/app.html");
        Thread.sleep(1000);
        $("#map").click();
        $("#map").click(ClickOptions.withOffset(offset, offset));
        $("#choose_action_modal_label").shouldBe(visible);
        $("#delete_editing_mark_button").click();
        Thread.sleep(1000);
        Selenide.switchTo().alert().accept();
    }

    @Test
    public void moveMarkClose() throws InterruptedException {
        Thread.sleep(1000);
        int offset = 100;
        $("#map").doubleClick();
        Thread.sleep(1000);
        $("#mark_name").sendKeys("mark");
        $("#create_new_mark_button").click();
        Thread.sleep(1000);
        $("#map").click();
        Thread.sleep(100);
        $("#move_mark_request_button").click();
        Thread.sleep(100);
        Selenide.switchTo().alert().accept();
        Thread.sleep(100);
        $("#map").click(ClickOptions.withOffset(offset, offset));
        $("#map").click(ClickOptions.withOffset(offset, offset));
        Thread.sleep(1000);
        open("http://localhost:8080/app.html");
        Thread.sleep(1000);
        $("#map").click(ClickOptions.withOffset(offset, offset));
        $("#choose_action_modal_label").shouldBe(visible);
        $("#delete_editing_mark_button").click();
        Thread.sleep(1000);
        Selenide.switchTo().alert().accept();
    }

    @Test
    public void removeMarkFromMenu() throws InterruptedException {
        Thread.sleep(1000);
        $("#map").doubleClick();
        Thread.sleep(1000);
        $("#mark_name").sendKeys("test");
        $("#create_new_mark_button").click();
        $("#marks_menu_nav_a").click();
        Thread.sleep(1000);
        String id = "[data-id='" + executeJavaScript("return $(\"td:contains('test')\").parent().children(\"td.d-flex\")[0].lastChild.lastChild.getAttribute('data-id')") + "']";
        $(id).shouldBe(visible);
        Thread.sleep(1000);
        executeJavaScript("$(\"td:contains('test')\").parent().children(\"td.d-flex\")[0].lastChild.lastChild.click()");
        $(id).shouldNotBe(visible);
    }

    @Test
    public void renameMark() throws InterruptedException {
        Thread.sleep(1000);
        $("#map").doubleClick();
        Thread.sleep(1000);
        $("#mark_name").sendKeys("test");
        $("#create_new_mark_button").click();
        Thread.sleep(1000);
        $("#map").click();
        Thread.sleep(1000);
        $("#edit_mark_button").click();
        Thread.sleep(1000);
        $("#edit_mark_name").clear();
        $("#edit_mark_name").sendKeys("test1");
        $("#update_mark_button").click();
        Thread.sleep(1000);
        $("#map").click();
        Thread.sleep(1000);
        $("#edit_mark_button").click();
        Thread.sleep(1000);
        Assertions.assertEquals("test1", $("#edit_mark_name").getValue());
        $("#update_mark_button").click();
        Thread.sleep(1000);
        remove_default_mark();
    }

    @Test
    public void renameMarkLongName() throws InterruptedException {
        String name = "0123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789";
        Thread.sleep(1000);
        $("#map").doubleClick();
        Thread.sleep(1000);
        $("#mark_name").sendKeys("test");
        $("#create_new_mark_button").click();
        Thread.sleep(1000);
        $("#map").click();
        Thread.sleep(1000);
        $("#edit_mark_button").click();
        Thread.sleep(1000);
        $("#edit_mark_name").clear();
        $("#edit_mark_name").sendKeys(name);
        $("#update_mark_button").click();
        Thread.sleep(1000);
        $("#map").click();
        Thread.sleep(1000);
        $("#edit_mark_button").click();
        Thread.sleep(1000);
        Assertions.assertEquals(name, $("#edit_mark_name").getValue());
        $("#update_mark_button").click();
        Thread.sleep(1000);
        remove_default_mark();
    }

    @Test
    public void renameMarkSpacesInName() throws InterruptedException {
        String name = "ме т ка";
        Thread.sleep(1000);
        $("#map").doubleClick();
        Thread.sleep(1000);
        $("#mark_name").sendKeys("test");
        $("#create_new_mark_button").click();
        Thread.sleep(1000);
        $("#map").click();
        Thread.sleep(1000);
        $("#edit_mark_button").click();
        Thread.sleep(1000);
        $("#edit_mark_name").clear();
        $("#edit_mark_name").sendKeys(name);
        $("#update_mark_button").click();
        Thread.sleep(1000);
        $("#map").click();
        Thread.sleep(1000);
        $("#edit_mark_button").click();
        Thread.sleep(1000);
        Assertions.assertEquals(name, $("#edit_mark_name").getValue());
        $("#update_mark_button").click();
        Thread.sleep(1000);
        remove_default_mark();
    }

    @Test
    public void renameMarkCapsInName() throws InterruptedException {
        String name = "МеТкА";
        Thread.sleep(1000);
        $("#map").doubleClick();
        Thread.sleep(1000);
        $("#mark_name").sendKeys("test");
        $("#create_new_mark_button").click();
        Thread.sleep(1000);
        $("#map").click();
        Thread.sleep(1000);
        $("#edit_mark_button").click();
        Thread.sleep(1000);
        $("#edit_mark_name").clear();
        $("#edit_mark_name").sendKeys(name);
        $("#update_mark_button").click();
        Thread.sleep(1000);
        $("#map").click();
        Thread.sleep(1000);
        $("#edit_mark_button").click();
        Thread.sleep(1000);
        Assertions.assertEquals(name, $("#edit_mark_name").getValue());
        $("#update_mark_button").click();
        Thread.sleep(1000);
        remove_default_mark();
    }

    @Test
    public void renameMarkSymbolsInName() throws InterruptedException {
        String name = "м-е%т?к&а";
        Thread.sleep(1000);
        $("#map").doubleClick();
        Thread.sleep(1000);
        $("#mark_name").sendKeys("test");
        $("#create_new_mark_button").click();
        Thread.sleep(1000);
        $("#map").click();
        Thread.sleep(1000);
        $("#edit_mark_button").click();
        Thread.sleep(1000);
        $("#edit_mark_name").clear();
        $("#edit_mark_name").sendKeys(name);
        $("#update_mark_button").click();
        Alert alert = switchTo().alert();
        String alert_text = alert.getText();
        Assertions.assertEquals("Название метки содержит недопустимые символы!", alert_text);
        $("#edit_mark_name").clear();
        $("#edit_mark_name").sendKeys("test");
        $("#update_mark_button").click();
        remove_default_mark();
    }

    @Test
    public void renameMarkEmptyName() throws InterruptedException {
        Thread.sleep(1000);
        $("#map").doubleClick();
        Thread.sleep(1000);
        $("#mark_name").sendKeys("test");
        $("#create_new_mark_button").click();
        Thread.sleep(1000);
        $("#map").click();
        Thread.sleep(1000);
        $("#edit_mark_button").click();
        Thread.sleep(1000);
        $("#edit_mark_name").clear();
        $("#update_mark_button").click();
        Alert alert = switchTo().alert();
        String alert_text = alert.getText();
        Assertions.assertEquals("Название метки не может быть пустым!", alert_text);
        $("#edit_mark_name").sendKeys("test");
        $("#update_mark_button").click();
        remove_default_mark();
    }

    @Test
    public void findMarkInMenu() throws InterruptedException {
        Thread.sleep(1000);
        $("#map").doubleClick();
        Thread.sleep(1000);
        $("#mark_name").sendKeys("test");
        $("#create_new_mark_button").click();
        Thread.sleep(100);
        $("#marks_menu_nav_a").click();
        $("#mark_filter_input").sendKeys("est");
        String id = "[data-id='" + executeJavaScript("return $(\"td:contains('test')\").parent().children(\"td.d-flex\")[0].lastChild.lastChild.getAttribute('data-id')") + "']";
        $(id).shouldBe(visible);
        executeJavaScript("$(\"td:contains('test')\").parent().children(\"td.d-flex\")[0].lastChild.lastChild.click()");
    }

    @Test
    public void findNotExistentMarkInMenu() throws InterruptedException {
        Thread.sleep(1000);
        $("#map").doubleClick();
        Thread.sleep(1000);
        $("#mark_name").sendKeys("test");
        $("#create_new_mark_button").click();
        Thread.sleep(100);
        $("#marks_menu_nav_a").click();
        $("#mark_filter_input").sendKeys("pew");
        String id = "[data-id='" + executeJavaScript("return $(\"td:contains('test')\").parent().children(\"td.d-flex\")[0].lastChild.lastChild.getAttribute('data-id')") + "']";
        $(id).shouldNotBe(visible);
        $("#mark_filter_input").clear();
        executeJavaScript("$(\"td:contains('test')\").parent().children(\"td.d-flex\")[0].lastChild.lastChild.click()");
    }
}
