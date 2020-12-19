package io.swagger;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "classpath:features",
        glue = "io.swagger.api",
        plugin = "pretty",
        strict = true
)
public class CucumberTest {
}