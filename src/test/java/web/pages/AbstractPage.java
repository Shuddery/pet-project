package web.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class AbstractPage {
    public static WebElement getWebElement(WebDriver driver, By by) {
        return driver.findElement(by);
    }
}