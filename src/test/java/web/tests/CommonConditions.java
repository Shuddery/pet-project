package web.tests;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.WebDriver;
import utils.PropertyReader;
import web.driver.DriverFactory;

public class CommonConditions {

    protected static WebDriver driver;

    @BeforeAll()
    static void setUp() {
        driver = DriverFactory.getDriver(PropertyReader.getBrowser());
    }

    @AfterAll()
    static void closeBrowser() {
        DriverFactory.closeDriver();
    }
}