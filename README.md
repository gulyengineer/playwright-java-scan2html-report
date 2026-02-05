# Playwright Java Trivy Report Tests

UI test suite for the Scan2HTML Trivy report page using Playwright and JUnit 5.

## What is covered
- Report title, menus, and filter buttons
- Vulnerability table visibility and headers
- Side menu collapse behavior
- Theme toggle (light/dark)

## Requirements
- JDK 17
- Maven 3.8+

## Setup
Install Playwright browsers:

```bash
mvn exec:java -Dexec.mainClass="com.microsoft.playwright.CLI" -Dexec.args="install --with-deps"
```

## Configuration
Set the report URL via env var or `.env`:

```bash
TEST_REPORT_URL=https://gulyengineer.github.io/playwright-java-scan2html-report/
```

## Run tests

```bash
mvn test
```

Notes:
- Tests currently launch a headed browser (see `src/test/java/com/trivy/base/BaseTest.java`).
- Trace files are written as `trace-<testName>.zip` in the project root.

## Project layout
- `src/test/java/com/trivy/pages/TrivyReportPage.java` - page object
- `src/test/java/com/trivy/tests` - JUnit tests
- `src/test/java/com/trivy/base/BaseTest.java` - Playwright setup/teardown