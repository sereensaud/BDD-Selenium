# Selenium-Cucumber Automation Framework

This project is a modular and scalable test automation framework built using **Selenium**, **Cucumber**, and **Java**, following best practices like **Page Object Model (POM)**, **Maven profiles**, and reusable utilities for robust testing.

##  Tools & Technologies

- **Java**
- **Selenium WebDriver**
- **Cucumber (BDD)**
- **Log4j 1.2.17** for logging
- **Maven** for build and dependency management
- **ExtentReports** for reporting
- **JUnit** for test execution
- **WebDriverManager** (if used)
  
##  Features Implemented

### 1. **Page Object Model (POM)**
- Clean separation of locators and actions.
- Page classes are reusable and modular.

### 2. **Step Definitions**
- Mapped directly to feature steps.
- Calls common actions and POM methods.
- Clean and readable step logic.

### 3. **Common Actions**
- All browser interactions like `click`, `sendKeys`, `scroll`, `dragAndDrop`, etc., are abstracted.
- Includes wait strategies using `WebDriverWait`.
- Alert handling, mouse hover, and utility functions.
- Centralized exception handling with logs and fallbacks.

### 4. **Logging (Log4j 1.2.17)**
- Logs added at key execution points.
- Helps in debugging and test tracing.

### 5. **Reporting**
- Integrated ExtentReports.
- Detailed HTML reports for test outcomes with steps, screenshots, and logs.

### 6. **Retry Mechanism**
- Failed tests are automatically retried based on configurable logic.

### 7. **Environment-Based Execution**
- Maven profiles allow switching between environments (e.g., `dev`, `qa`, `staging`) without modifying code.
- Environment-specific properties are loaded dynamically.

### 8. **Exception Handling**
- Robust try-catch blocks across utilities to prevent crashes.
- Fail-safe methods log errors without halting test suites.

### 9. **Wait Strategies**
- `Explicit Waits` added for element visibility, alert presence, etc., improving test stability.

##  How to Run Tests

```bash
# Run tests with Maven and specify the environment
mvn clean verify -Pqa
