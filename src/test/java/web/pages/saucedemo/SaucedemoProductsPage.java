package web.pages.saucedemo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import web.pages.AbstractPage;
import web.waits.Waits;

public class SaucedemoProductsPage extends AbstractPage {

    private static final By title = By.xpath("//span[@class='title']");

    public static WebElement getTitle(WebDriver driver) {
        return Waits.waitVisibilityOfElementLocated(driver, title);
    }
}
