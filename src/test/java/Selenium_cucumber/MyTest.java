package Selenium_cucumber;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

/**
 * Created by Wawer on 2018-01-16.
 */


@RunWith(Cucumber.class)
@CucumberOptions(
        features = "classpath:Test.features",
        glue = "Selenium_cucumber",
        plugin ="html:target/selenium-reports"
)

public class MyTest{

}