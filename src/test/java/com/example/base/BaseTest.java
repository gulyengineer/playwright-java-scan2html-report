package com.example.base;

import com.example.config.ConfigReader;
import com.microsoft.playwright.*;
import org.junit.jupiter.api.*;

public class BaseTest {
    protected Playwright playwright;
    protected Browser browser;
    protected BrowserContext context;
    protected Page page;

    @BeforeEach
    void setup() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));

        context = browser.newContext();
        page = context.newPage();
        page.navigate(ConfigReader.get("TEST_URL"));
    }

    @AfterEach
    void tearDown() {
        if (browser != null) browser.close();
        if (playwright != null) playwright.close();
    }
}