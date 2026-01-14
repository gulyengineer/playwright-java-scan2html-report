package com.example.tests;

import com.example.base.BaseTest;
import com.microsoft.playwright.*;
import com.microsoft.playwright.Locator.FilterOptions;
import org.junit.jupiter.api.*;

import java.util.List;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static com.microsoft.playwright.options.AriaRole.BUTTON;
import static java.util.Arrays.asList;

public class TrivyReportTest extends BaseTest {

    @Test
    void validateTrivyReportDashboard() {
        assertThat(page).hasTitle("Trivy Report");
        List<String> expectedMenus = asList("Vulnerabilities", "Misconfigurations", "Secrets", "Licenses", "Misconfiguration Summary", "K8s Cluster Summary", "Supply Chain SBOM(spdx)", "Load a report");
        for (String menuText : expectedMenus) {
            assertThat(page.locator(".ant-menu-item").filter(new FilterOptions().setHasText(menuText))).isVisible();
        }

        Locator themeSwitch = page.locator("button.ant-switch");
        assertThat(themeSwitch).isVisible();

        assertThat(page.locator("html")).hasAttribute("data-theme", "light");

        themeSwitch.click();
        assertThat(page.locator("html")).hasAttribute("data-theme", "dark");

        String[] filters = {"Critical", "High", "Medium", "Low", "Negligible", "All", "Has Exploit", "Has fix", "Has no fix"};
        for (String filter : filters) {
            Locator filterBtn = page.getByRole(BUTTON, new Page.GetByRoleOptions().setName(filter));
            assertThat(filterBtn).isVisible();
        }
    }

    @Test
    void validateVulnerabilityTable() {
        // Ensure the results table is loaded
        Locator vulsTable = page.locator(".ant-table");
        assertThat(vulsTable).isVisible();

        Locator tableHeaders = page.locator(".ant-table-thead th");

        String[] expectedHeaders = {
                "Target", "Library/Package", "Vulnerability", "NVD V2Score", "NVD V3Score", "EPSS Score %",
                "Severity", "Exploits", "Installed Version", "Fixed Version", "Title"
        };
        assertThat(tableHeaders).hasText(expectedHeaders);
    }
}