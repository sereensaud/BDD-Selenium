package org.example.runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class) // This tells JUnit to run Cucumber
@CucumberOptions(
        features = "src/test/resources/features",                // here feature files are
        glue = "org.example.stepdefinitions",                    // Where step defs are
        plugin = {"pretty", "html:target/cucumber-reports.html"},
        monochrome = true
)
public class TestRunner {
        // This class should be EMPTY — no methods required
}
