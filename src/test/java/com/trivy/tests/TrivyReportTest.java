package com.trivy.tests;

import com.trivy.base.BaseTest;
import org.junit.jupiter.api.*;

public class TrivyReportTest extends BaseTest {

    @Test
    void validateTrivyReportDashboard() {
        report.validatePageTitle();
        report.validateMenuList();
        report.validateFilterButtons();
    }

    @Test
    void testCollapseMenuWorks() {
        report.collapseSidebar();
        report.isSideMenuCollapsed();
    }

    @Test
    void testThemeToggleWorks() {
        report.validateThemeSwitchIsVisible();
        report.verifyThemeIs("light");
        report.toggleTheme();
        report.verifyThemeIs("dark");
    }
}