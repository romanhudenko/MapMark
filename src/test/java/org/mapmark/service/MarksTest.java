package org.mapmark.service;

import com.codeborne.selenide.ClickOptions;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapmark.service.utils.MapHelper;
import org.openqa.selenium.Alert;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class MarksTest{

    @BeforeEach
    public void openBrowser() throws InterruptedException {
        open("http://localhost:8080/");
        $("#username").sendKeys("user");
        $("#password").sendKeys("user");
        $("button[type=\"submit\"]").click();
        MapHelper.waitForMap();
    }

    @AfterEach
    public void closeBrowser() {
        closeWebDriver();
    }

    @Test
    public void createMark() throws InterruptedException {
        $("#map").doubleClick();
        MapHelper.waitForInterface();
        $("#mark_name").sendKeys("test");
        $("#create_new_mark_button").click();
        MapHelper.removeDefaultMark();
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
        $("#map").doubleClick();
        MapHelper.waitForInterface();
        $("#mark_name").sendKeys("0123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789");
        $("#create_new_mark_button").click();
        MapHelper.removeDefaultMark();
    }

    @Test
    public void createMarkWithSpaces() throws InterruptedException {
        $("#map").doubleClick();
        MapHelper.waitForInterface();
        $("#mark_name").sendKeys("ме т ка");
        $("#create_new_mark_button").click();
        MapHelper.removeDefaultMark();
    }

    @Test
    public void createMarkWithCaps() throws InterruptedException {
        $("#map").doubleClick();
        MapHelper.waitForInterface();
        $("#mark_name").sendKeys("МеТкА");
        $("#create_new_mark_button").click();
        MapHelper.removeDefaultMark();
    }

    @Test
    public void createMarkWithSameName() throws InterruptedException {
        int offset = 10;
        $("#map").doubleClick();
        MapHelper.waitForInterface();
        $("#mark_name").sendKeys("mark");
        $("#create_new_mark_button").click();
        MapHelper.waitForInterface();
        $("#map").doubleClick(ClickOptions.withOffset(offset, offset));
        MapHelper.waitForInterface();
        $("#mark_name").sendKeys("mark");
        $("#create_new_mark_button").click();
        MapHelper.waitForInterface();
        $("#marks_menu_nav_a").click();
        MapHelper.waitForInterface();
        String id = "[data-id='" + executeJavaScript("return $(\"td:contains('mark')\").parent().children(\"td.d-flex\")[0].lastChild.lastChild.getAttribute('data-id')") + "']";
        $(id).shouldBe(visible);
        executeJavaScript("$(\"td:contains('mark')\").parent().children(\"td.d-flex\")[0].lastChild.lastChild.click()");
        MapHelper.waitForInterface();
        id = "[data-id='" + executeJavaScript("return $(\"td:contains('mark')\").parent().children(\"td.d-flex\")[0].lastChild.lastChild.getAttribute('data-id')") + "']";
        $(id).shouldBe(visible);
        executeJavaScript("$(\"td:contains('mark')\").parent().children(\"td.d-flex\")[0].lastChild.lastChild.click()");
    }

    @Test
    public void createMarkWithDifferentColor() throws InterruptedException {
        $("#map").doubleClick();
        MapHelper.waitForInterface();
        $("#mark_name").sendKeys("test");
        executeJavaScript("document.getElementById(\"mark_color\").setAttribute(\"value\", \"#f66151\");");
        $("#create_new_mark_button").click();
        MapHelper.waitForInterface();
        $("#map").click();
        MapHelper.waitForInterface();
        $("#choose_action_modal_label").shouldBe(visible);
        $("#edit_mark_button").click();
        MapHelper.waitForInterface();
        Assertions.assertEquals("#f66151", $("#mark_color").getAttribute("value"));
        $("#update_mark_button").click();
        MapHelper.waitForInterface();
        MapHelper.removeMarkByName("test");
    }

    @Test
    public void createMarkWithForbiddenSymbols() throws InterruptedException {
        $("#map").doubleClick();
        MapHelper.waitForInterface();
        $("#mark_name").sendKeys("м-е%т?к&а");
        $("#create_new_mark_button").click();
        Alert alert = switchTo().alert();
        String alert_text = alert.getText();
        Assertions.assertEquals("Название метки содержит недопустимые символы!", alert_text);
        alert.accept();
    }

    @Test
    public void createMarkWithEmptyName() throws InterruptedException {
        $("#map").doubleClick();
        MapHelper.waitForInterface();
        $("#create_new_mark_button").click();
        Alert alert = switchTo().alert();
        String alert_text = alert.getText();
        Assertions.assertEquals("Название метки не может быть пустым!", alert_text);
        alert.accept();
    }

    @Test
    public void moveMark() throws InterruptedException {
        int offset = 100;
        $("#map").doubleClick();
        MapHelper.waitForMap();
        $("#mark_name").sendKeys("mark");
        $("#create_new_mark_button").click();
        MapHelper.waitForInterface();
        $("#map").click();
        MapHelper.waitForInterface();
        $("#move_mark_request_button").click();
        MapHelper.waitForInterface();
        Selenide.switchTo().alert().accept();
        MapHelper.waitForInterface();
        $("#map").doubleClick(ClickOptions.withOffset(offset, offset));
        MapHelper.waitForMap();
        open("http://localhost:8080/app.html");
        MapHelper.waitForMap();
        $("#map").click(ClickOptions.withOffset(offset, offset));
        $("#choose_action_modal_label").shouldBe(visible);
        $("#delete_editing_mark_button").click();
        MapHelper.waitForInterface();
        Selenide.switchTo().alert().accept();
    }

    @Test
    public void moveMarkClose() throws InterruptedException {
        int offset = 20;
        $("#map").doubleClick();
        MapHelper.waitForMap();
        $("#mark_name").sendKeys("mark");
        $("#create_new_mark_button").click();
        MapHelper.waitForInterface();
        $("#map").click();
        MapHelper.waitForInterface();
        $("#move_mark_request_button").click();
        MapHelper.waitForInterface();
        Selenide.switchTo().alert().accept();
        MapHelper.waitForInterface();
        $("#map").click(ClickOptions.withOffset(offset, offset));
        $("#map").click(ClickOptions.withOffset(offset, offset));
        MapHelper.waitForMap();
        MapHelper.removeMarkByName("mark");
    }

    @Test
    public void removeMarkFromMenu() throws InterruptedException {
        $("#map").doubleClick();
        MapHelper.waitForInterface();
        $("#mark_name").sendKeys("test");
        $("#create_new_mark_button").click();
        $("#marks_menu_nav_a").click();
        MapHelper.waitForInterface();
        String id = "[data-id='" + executeJavaScript("return $(\"td:contains('test')\").parent().children(\"td.d-flex\")[0].lastChild.lastChild.getAttribute('data-id')") + "']";
        $(id).shouldBe(visible);
        MapHelper.waitForInterface();
        executeJavaScript("$(\"td:contains('test')\").parent().children(\"td.d-flex\")[0].lastChild.lastChild.click()");
        $(id).shouldNotBe(visible);
    }

    @Test
    public void renameMark() throws InterruptedException {
        $("#map").doubleClick();
        MapHelper.waitForInterface();
        $("#mark_name").sendKeys("test");
        $("#create_new_mark_button").click();
        MapHelper.waitForInterface();
        $("#map").click();
        MapHelper.waitForInterface();
        $("#edit_mark_button").click();
        MapHelper.waitForInterface();
        $("#edit_mark_name").clear();
        $("#edit_mark_name").sendKeys("test1");
        $("#update_mark_button").click();
        MapHelper.waitForInterface();
        $("#map").click();
        MapHelper.waitForInterface();
        $("#edit_mark_button").click();
        MapHelper.waitForInterface();
        Assertions.assertEquals("test1", $("#edit_mark_name").getValue());
        $("#update_mark_button").click();
        MapHelper.waitForInterface();
        MapHelper.removeDefaultMark();
    }

    @Test
    public void renameMarkLongName() throws InterruptedException {
        String name = "0123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789";
        $("#map").doubleClick();
        MapHelper.waitForInterface();
        $("#mark_name").sendKeys("test");
        $("#create_new_mark_button").click();
        MapHelper.waitForInterface();
        $("#map").click();
        MapHelper.waitForInterface();
        $("#edit_mark_button").click();
        MapHelper.waitForInterface();
        $("#edit_mark_name").clear();
        $("#edit_mark_name").sendKeys(name);
        $("#update_mark_button").click();
        MapHelper.waitForInterface();
        $("#map").click();
        MapHelper.waitForInterface();
        $("#edit_mark_button").click();
        MapHelper.waitForInterface();
        Assertions.assertEquals(name, $("#edit_mark_name").getValue());
        $("#update_mark_button").click();
        MapHelper.waitForInterface();
        MapHelper.removeDefaultMark();
    }

    @Test
    public void renameMarkSpacesInName() throws InterruptedException {
        String name = "ме т ка";
        MapHelper.waitForInterface();
        $("#map").doubleClick();
        MapHelper.waitForInterface();
        $("#mark_name").sendKeys("test");
        $("#create_new_mark_button").click();
        MapHelper.waitForInterface();
        $("#map").click();
        MapHelper.waitForInterface();
        $("#edit_mark_button").click();
        MapHelper.waitForInterface();
        $("#edit_mark_name").clear();
        $("#edit_mark_name").sendKeys(name);
        $("#update_mark_button").click();
        MapHelper.waitForInterface();
        $("#map").click();
        MapHelper.waitForInterface();
        $("#edit_mark_button").click();
        MapHelper.waitForInterface();
        Assertions.assertEquals(name, $("#edit_mark_name").getValue());
        $("#update_mark_button").click();
        MapHelper.waitForInterface();
        MapHelper.removeDefaultMark();
    }

    @Test
    public void renameMarkCapsInName() throws InterruptedException {
        String name = "МеТкА";
        MapHelper.waitForInterface();
        $("#map").doubleClick();
        MapHelper.waitForInterface();
        $("#mark_name").sendKeys("test");
        $("#create_new_mark_button").click();
        MapHelper.waitForInterface();
        $("#map").click();
        MapHelper.waitForInterface();
        $("#edit_mark_button").click();
        MapHelper.waitForInterface();
        $("#edit_mark_name").clear();
        $("#edit_mark_name").sendKeys(name);
        $("#update_mark_button").click();
        MapHelper.waitForInterface();
        $("#map").click();
        MapHelper.waitForInterface();
        $("#edit_mark_button").click();
        MapHelper.waitForInterface();
        Assertions.assertEquals(name, $("#edit_mark_name").getValue());
        $("#update_mark_button").click();
        MapHelper.waitForInterface();
        MapHelper.removeDefaultMark();
    }

    @Test
    public void renameMarkSymbolsInName() throws InterruptedException {
        String name = "м-е%т?к&а";
        MapHelper.waitForInterface();
        $("#map").doubleClick();
        MapHelper.waitForInterface();
        $("#mark_name").sendKeys("test");
        $("#create_new_mark_button").click();
        MapHelper.waitForInterface();
        $("#map").click();
        MapHelper.waitForInterface();
        $("#edit_mark_button").click();
        MapHelper.waitForInterface();
        $("#edit_mark_name").clear();
        $("#edit_mark_name").sendKeys(name);
        $("#update_mark_button").click();
        Alert alert = switchTo().alert();
        String alert_text = alert.getText();
        Assertions.assertEquals("Название метки содержит недопустимые символы!", alert_text);
        $("#edit_mark_name").clear();
        $("#edit_mark_name").sendKeys("test");
        $("#update_mark_button").click();
        MapHelper.removeDefaultMark();
    }

    @Test
    public void renameMarkEmptyName() throws InterruptedException {
        $("#map").doubleClick();
        MapHelper.waitForInterface();
        $("#mark_name").sendKeys("test");
        $("#create_new_mark_button").click();
        MapHelper.waitForInterface();
        $("#map").click();
        MapHelper.waitForInterface();
        $("#edit_mark_button").click();
        MapHelper.waitForInterface();
        $("#edit_mark_name").clear();
        $("#update_mark_button").click();
        Alert alert = switchTo().alert();
        String alert_text = alert.getText();
        Assertions.assertEquals("Название метки не может быть пустым!", alert_text);
        $("#edit_mark_name").sendKeys("test");
        $("#update_mark_button").click();
        MapHelper.removeDefaultMark();
    }

    @Test
    public void findMarkInMenu() throws InterruptedException {
        $("#map").doubleClick();
        MapHelper.waitForInterface();
        $("#mark_name").sendKeys("test");
        $("#create_new_mark_button").click();
        MapHelper.waitForInterface();
        $("#marks_menu_nav_a").click();
        $("#mark_filter_input").sendKeys("est");
        String id = "[data-id='" + executeJavaScript("return $(\"td:contains('test')\").parent().children(\"td.d-flex\")[0].lastChild.lastChild.getAttribute('data-id')") + "']";
        $(id).shouldBe(visible);
        executeJavaScript("$(\"td:contains('test')\").parent().children(\"td.d-flex\")[0].lastChild.lastChild.click()");
    }

    @Test
    public void findNotExistentMarkInMenu() throws InterruptedException {
        $("#map").doubleClick();
        MapHelper.waitForInterface();
        $("#mark_name").sendKeys("test");
        $("#create_new_mark_button").click();
        MapHelper.waitForInterface();
        $("#marks_menu_nav_a").click();
        $("#mark_filter_input").sendKeys("pew");
        String id = "[data-id='" + executeJavaScript("return $(\"td:contains('test')\").parent().children(\"td.d-flex\")[0].lastChild.lastChild.getAttribute('data-id')") + "']";
        $(id).shouldNotBe(visible);
        $("#mark_filter_input").clear();
        executeJavaScript("$(\"td:contains('test')\").parent().children(\"td.d-flex\")[0].lastChild.lastChild.click()");
    }
}
