package listener;

import io.qameta.allure.Allure;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import utils.PropertyReader;
import web.driver.DriverFactory;

public class TestListener implements TestWatcher{
    @Override
    public void testFailed(ExtensionContext context, Throwable cause) {
        Allure.getLifecycle().addAttachment(
                "screenshot", "image/png", "png"
                , ((TakesScreenshot) DriverFactory
                        .getDriver(PropertyReader.getBrowser()))
                        .getScreenshotAs(OutputType.BYTES)
        );
    }
}