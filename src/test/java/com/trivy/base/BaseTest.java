package com.trivy.base;

import com.trivy.config.ConfigReader;
import com.microsoft.playwright.*;
import com.trivy.pages.TrivyReportPage;
import org.junit.jupiter.api.*;

import java.nio.file.Paths;

public class BaseTest {
    protected Playwright playwright;
    protected Browser browser;
    protected BrowserContext context;
    protected Page page;
    protected TrivyReportPage report;

    @BeforeEach
    void setup() {
        playwright = Playwright.create();
        boolean headless = ConfigReader.getBoolean("headless", true);
        browser = playwright.chromium().launch(
                new BrowserType.LaunchOptions().setHeadless(headless)
        );
        context = browser.newContext();
        page = context.newPage();
        context.tracing().start(
                new Tracing.StartOptions()
                        .setScreenshots(true)
                        .setSnapshots(true)
        );
        String reportUrl = ConfigReader.getRequired("TEST_REPORT_URL");
        page.navigate(reportUrl);
        report = new TrivyReportPage(page);
    }

    @AfterEach
    void tearDown(TestInfo testInfo) {
        String testName = testInfo.getDisplayName().replaceAll("[^a-zA-Z0-9.-]", "_");
        try {
            if (context != null) {
                context.tracing().stop(new Tracing.StopOptions()
                        .setPath(Paths.get("trace-" + testName + ".zip")));
            }
        } finally {
            if (context != null) context.close();
            if (browser != null) browser.close();
            if (playwright != null) playwright.close();
        }
    }
}