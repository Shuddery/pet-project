package web.pages.saucedemo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import web.pages.AbstractPage;
import web.waits.Waits;

public class SaucedemoLoginPage extends AbstractPage {
    private static final By titleOfLoginPage = By.xpath("//div[@class='login_logo']");
    private static final By usernameField = By.xpath("#user-name");

    public static WebElement getTitleOfLoginPage(WebDriver driver) {
        return Waits.waitVisibilityOfElementLocated(driver, titleOfLoginPage);
    }

    public static WebElement getUsernameField(WebDriver driver) {
        return Waits.waitVisibilityOfElementLocated(driver, usernameField);
    }


}