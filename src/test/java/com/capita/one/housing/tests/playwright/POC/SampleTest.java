package com.capita.one.housing.tests.playwright.POC;

import com.microsoft.playwright.*;

public class SampleTest {
    public static void main(String[] args) {
    	boolean Headless = Boolean.parseBoolean("False");
        try (Playwright playwright = Playwright.create()) {
            Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(Headless));
            Page page = browser.newPage();
            page.navigate("https://onehousingqa.capita-software.co.uk/#/login");
            System.out.println(page.title());
        }
    }
}