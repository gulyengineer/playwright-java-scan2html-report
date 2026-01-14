package com.example;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;
import org.junit.jupiter.api.*;

import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class TrivyReportTest {
    // Shared across all tests in this class
    static Playwright playwright;
    static Browser browser;

    // New for every test
    BrowserContext context;
    Page page;

    @BeforeAll
    static void launchBrowser() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
    }

    @BeforeEach
    void setup() {
        context = browser.newContext();
        page = context.newPage();
        // Updated path to your specific file
        String path = Paths.get("C:\\Users\\dell\\IdeaProjects\\playwright-java-scan2html-report\\TrivyReport.html").toUri().toString();
        page.navigate(path);
    }

    @Test
    void validateTrivyReportDashboard() {
        // 1. Page Title Validation
        assertThat(page).hasTitle("Trivy Report");
        // 2. Side Menu Validation
        List<String> expectedMenus = Arrays.asList("Vulnerabilities", "Misconfigurations", "Secrets", "Licenses", "Misconfiguration Summary", "K8s Cluster Summary", "Supply Chain SBOM(spdx)", "Load a report");
        for (String menuText : expectedMenus) {
            // Using filter to ensure we match the specific text within the menu items
            assertThat(page.locator(".ant-menu-item").filter(new Locator.FilterOptions().setHasText(menuText))).isVisible();
        }

        // 3. Light/Dark Theme Switch
        Locator themeSwitch = page.locator("button.ant-switch");
        assertThat(themeSwitch).isVisible();

        // Initial state is 'light'
        assertThat(page.locator("html")).hasAttribute("data-theme", "light");

        // Toggle theme and verify the attribute change
        themeSwitch.click();
        assertThat(page.locator("html")).hasAttribute("data-theme", "dark");

        // 4. Severity Filter Buttons (Vulnerability Table)
        String[] filters = {"Critical", "High", "Medium", "Low", "Negligible", "All", "Has Exploit", "Has fix", "Has no fix"};
        for (String filter : filters) {
            // Targets buttons containing the severity text (case-insensitive)
            Locator filterBtn = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName(filter));
            assertThat(filterBtn).isVisible();
        }
    }

    @Test
    void validateVulnerabilityTable() {
        // Ensure the results table is loaded
        Locator vulsTable = page.locator(".ant-table");
        assertThat(vulsTable).isVisible();

        // Verify that data columns like 'Severity' , "Target" or 'Library/Package' exist in the header
        Locator tableHeaders = page.locator(".ant-table-thead th");

        List<String> expectedHeaders = Arrays.asList(
                "Target", "Library/Package", "Vulnerability", "NVD V2Score", "NVD V3Score", "EPSS Score %",
                "Severity", "Exploits", "Installed Version", "Fixed Version", "Title"
        );
        assertThat(tableHeaders).hasText(expectedHeaders.toArray(new String[0]));
    }

    @AfterEach
    void closeContext() {
        context.close();
    }

    @AfterAll
    static void closeBrowser() {
        browser.close();
        playwright.close();
    }
}