package web.pages.saucedemo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import web.pages.AbstractPage;
import web.waits.Waits;

public class SaucedemoLoginPage extends AbstractPage {
    private static final By title = By.xpath("//div[@class='login_logo']");
    private static final By usernameField = By.cssSelector("#user-name");
    private static final By passwordField = By.cssSelector("#password");
    private static final By loginButton = By.cssSelector("#login-button");
    private static final By loginErrorMessage = By.xpath("//h3");
    private static final By errorButton = By.xpath("//button[@class='error-button']");

    public static WebElement getTitle(WebDriver driver) {
        return Waits.waitVisibilityOfElementLocated(driver, title);
    }

    public static WebElement getUsernameField(WebDriver driver) {
        return Waits.waitVisibilityOfElementLocated(driver, usernameField);
    }

    public static WebElement getPasswordField(WebDriver driver) {
        return Waits.waitVisibilityOfElementLocated(driver, passwordField);
    }

    public static WebElement getLoginButton(WebDriver driver) {
        return Waits.waitElementToBeClickable(driver, loginButton);
    }

    public static WebElement getErrorButton(WebDriver driver) {
        return Waits.waitElementToBeClickable(driver, errorButton);
    }

    public static WebElement getLoginErrorMessage(WebDriver driver) {
        return Waits.waitVisibilityOfElementLocated(driver, loginErrorMessage);
    }

    public static boolean isLoginErrorMessageHidden(WebDriver driver) {
        return Waits.waitInvisibilityOfElementLocated(driver, loginErrorMessage);
    }


    }