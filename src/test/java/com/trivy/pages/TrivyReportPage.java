package com.trivy.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import java.util.List;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static com.microsoft.playwright.options.AriaRole.BUTTON;
import static java.util.Arrays.asList;

public class TrivyReportPage {
    private final Page page;

    private final Locator sideMenu;
    private final Locator collapseButton;
    private final Locator tableHeaders;
    private final Locator vulsTable;
    private final Locator themeSwitch;
    private final Locator htmlTag;
    String[] filters;
    String[] expectedTabledHeaderList;
    List<String> expectedMenus;

    public TrivyReportPage(Page page) {
        this.page = page;
        this.sideMenu = page.locator(".ant-menu");
        this.collapseButton = page.getByRole(BUTTON, new Page.GetByRoleOptions().setName("menu-fold"));

        this.tableHeaders = page.locator(".ant-table-thead th");
        this.vulsTable = page.locator(".ant-table");
        this.expectedTabledHeaderList = new String[]{
                "Target", "Library/Package", "Vulnerability", "NVD V2Score", "NVD V3Score", "EPSS Score %",
                "Severity", "Exploits", "Installed Version", "Fixed Version", "Title"
        };
        this.expectedMenus = asList("Vulnerabilities", "Misconfigurations", "Secrets", "Licenses",
                "Misconfiguration Summary", "K8s Cluster Summary", "Supply Chain SBOM(spdx)", "Load a report");
        this.filters = new String[]{"Critical", "High", "Medium", "Low", "Negligible", "All", "Has Exploit",
                "Has fix", "Has no fix"};
        this.themeSwitch = page.locator("button.ant-switch");
        this.htmlTag = page.locator("html");
    }

    public void validatePageTitle() {
        assertThat(page).hasTitle("Trivy Report");
    }

    public void validateMenuList() {
        for (String menuText : expectedMenus) {
            assertThat(page.locator(".ant-menu-item").filter(new Locator.FilterOptions().setHasText(menuText))).isVisible();
        }
    }

    public void isVulsTableVisible() {
        assertThat(vulsTable).isVisible();
    }

    public void collapseSidebar() {
        if (!isSideMenuCollapsed()) {
            collapseButton.click();
        }
    }

    public boolean isSideMenuCollapsed() {
        return sideMenu.getAttribute("class").contains("ant-menu-inline-collapsed");
    }

    public void validateHeadersMatch() {
        assertThat(tableHeaders).hasText(expectedTabledHeaderList);
    }

    public void validateFilterButtons() {
        for (String filter : filters) {
            Locator filterBtn = page.getByRole(BUTTON, new Page.GetByRoleOptions().setName(filter));
            assertThat(filterBtn).isVisible();
        }
    }

    public void validateThemeSwitchIsVisible() {
        assertThat(themeSwitch).isVisible();
    }

    public void toggleTheme() {
        themeSwitch.click();
    }

    public void verifyThemeIs(String expectedTheme) {
        assertThat(htmlTag).hasAttribute("data-theme", expectedTheme);
    }
}