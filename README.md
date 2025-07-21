Selenium-Cucumber Automation Framework
This project is a robust, modular, and scalable test automation framework built using Java, Selenium WebDriver, and Cucumber for Behavior-Driven Development (BDD). It follows best practices like the Page Object Model (POM), dynamic configuration handling, and powerful logging/reporting utilities.

Tools & Technologies
Java

Selenium WebDriver

Cucumber (BDD)

Log4j 1.2.17 for logging

ExtentReports for rich test reporting

Maven for build and dependency management

JUnit for test execution

WebDriverManager (optional)

Features Implemented
1.  Page Object Model (POM)
POM is used to keep test logic separate from UI structure.

DOM elements are not hardcoded in step definitions. Instead, they're retrieved dynamically using locators from DOMElements.json.

Actions are abstracted in reusable page/action classes.

This allows easy updates if UI elements change, without touching step logic.

2.  Step Definitions (Updated)
Login and Signup steps are now merged into a single CommonSteps class.

This is because many actions like browser launch, navigation, and element interaction were redundantly repeated across these flows.

Merging enhances reusability and maintainability while reducing duplicate code.

Example: Steps like "User clicks on X" or "User enters Y in Z" are generic, and used by both login and signup. These are now handled once in CommonSteps.

3.  Common Actions
All generic WebDriver interactions are abstracted inside CommonActions.java. This includes:

click, type, waitForElement, scrollToElement, etc.

Advanced interactions like:

Switching into iframes

Handling JavaScript-based shadow DOMs

Alert and popup handling

Includes centralized exception handling and wait strategies.

4.  Locator Strategy
All locators are stored in a centralized JSON file: DOMElements.json.

This JSON structure maps element names to locator types and values.

LocatorHelper.java reads the JSON and returns Selenium By objects at runtime.

No locator duplication across codebase; change it once and it reflects everywhere.

5.  Hooks
Hooks.java manages @Before and @After methods for setup and teardown.

Ensures browser is launched/closed for each scenario.

Captures screenshots on failure and attaches them to ExtentReports.

6.  Reporting
Integrated ExtentReports for detailed HTML reports.

Reports include step-level logs, screenshots, and exception messages.

7.  Retry Mechanism
Failing tests are auto-retired with a configurable retry logic.

Helps reduce false negatives due to flakiness.

8.  Environment-Based Execution
Use Maven profiles (-Pdev, -Pqa, -Pstaging) to run tests in different environments.

config.properties is dynamically loaded based on the selected profile.

9.  Logging (Log4j 1.2.17)
Logs at all significant actions (browser start, element actions, failures).

Logs are stored in test.log.

How to Run Tests
# Run tests with Maven and specify the environment
mvn clean verify -Pqa

**Notes:**
 
New step definitions should be added to CommonSteps.java if reusable, or create a separate class only if they are specific and not overlapping.

Keep DOMElements.json updated if any UI locator changes.

Use LocatorHelper to access locators inside actions or steps.

Maintain consistency in naming conventions across locators and feature steps.
