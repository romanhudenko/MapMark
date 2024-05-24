package org.mapmark.service.utils;

import com.codeborne.selenide.Selenide;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.executeJavaScript;

public class MapHelper {
    public static void waitForMap() throws InterruptedException {
        Thread.sleep(1000);
    }

    public static void waitForInterface() throws InterruptedException {
        Thread.sleep(500);
    }

    public static void removeMarkByName(String name) throws InterruptedException {
        $("#marks_menu_nav_a").click();
        waitForInterface();
        executeJavaScript("$(\"td:contains('"  + name + "')\").parent().children(\"td.d-flex\")[0].lastChild.lastChild.click()");
    }

    public static void removeDefaultMark() throws InterruptedException {
        waitForMap();
        $("#map").click();
        $("#choose_action_modal_label").shouldBe(visible);
        $("#delete_editing_mark_button").click();
        waitForInterface();
        Selenide.switchTo().alert().accept();
    }
}
