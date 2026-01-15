package com.trivy.base;

import com.trivy.config.ConfigReader;
import com.microsoft.playwright.*;
import com.trivy.pages.TrivyReportPage;
import org.junit.jupiter.api.*;

public class BaseTest {
    protected Playwright playwright;
    protected Browser browser;
    protected BrowserContext context;
    protected Page page;
    // Page Object instance accessible to all tests
    protected TrivyReportPage report;

    @BeforeEach
    void setup() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));

        context = browser.newContext();
        page = context.newPage();
        page.navigate(ConfigReader.get("TEST_REPORT_URL"));
        report = new TrivyReportPage(page);
    }

    @AfterEach
    void tearDown() {
        if (browser != null) browser.close();
        if (playwright != null) playwright.close();
    }
}