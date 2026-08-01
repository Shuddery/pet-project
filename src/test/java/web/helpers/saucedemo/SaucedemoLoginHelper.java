package web.helpers.saucedemo;

import builder.Credentials;
import io.qameta.allure.Step;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import web.helpers.AbstractHelper;
import web.pages.saucedemo.SaucedemoLoginPage;

public class SaucedemoLoginHelper extends AbstractHelper {

    public SaucedemoLoginHelper(WebDriver driver) {
        super(driver);
    }

    @Step("Get text of header in login page")
    public String getTitleOfLoginPageText() {
        log.info("Get title in login page");
        return SaucedemoLoginPage.getTitle(driver).getText();
    }

    @Step("Fill username field with username")
    public SaucedemoLoginHelper fillUsernameField(Credentials credentials) {
        log.info("Fill username field with username - {}", credentials.getUsername());
        SaucedemoLoginPage.getUsernameField(driver).sendKeys(credentials.getUsername());
        return this;
    }

    @Step("Fill username field with username")
    public SaucedemoLoginHelper fillUsernameField(String username) {
        log.info("Fill username field with username - {}", username);
        SaucedemoLoginPage.getUsernameField(driver).sendKeys(username);
        return this;
    }

    @Step("Clear username field")
    public SaucedemoLoginHelper clearUsernameField() {
        log.info("Clear username field");
        SaucedemoLoginPage.getUsernameField(driver).sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.BACK_SPACE);
        return this;
    }

    @Step("Fill password field with password")
    public SaucedemoLoginHelper fillPasswordField(Credentials credentials) {
        log.info("Fill password field with password - {}", credentials.getPassword());
        SaucedemoLoginPage.getPasswordField(driver).sendKeys(credentials.getPassword());
        return this;
    }

    @Step("Fill password field with password")
    public SaucedemoLoginHelper fillPasswordField(String password) {
        log.info("Fill password field with password - {}", password);
        SaucedemoLoginPage.getPasswordField(driver).sendKeys(password);

        return this;
    }

    @Step("Clear password field")
    public SaucedemoLoginHelper clearPasswordField() {
        log.info("Clear password field");
        SaucedemoLoginPage.getPasswordField(driver).sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.BACK_SPACE);
        return this;
    }

    @Step("Click on login button")
    public SaucedemoProductsHelper clickLoginButton() {
        log.info("Click login button");
        SaucedemoLoginPage.getLoginButton(driver).click();
        return new SaucedemoProductsHelper(driver);
    }

    @Step("Is login button clickable")
    public boolean isLoginButtonClickable() {
        log.info("Is login button clickable");
        return SaucedemoLoginPage.getLoginButton(driver).isEnabled();
    }

    @Step("Click error button")
    public SaucedemoLoginHelper clickErrorButton() {
        log.info("Click error button");
        SaucedemoLoginPage.getErrorButton(driver).click();
        return this;
    }

    @Step("Is error message displayed")
    public boolean isErrorMessageDisplayed() {
        log.info("Is error message displayed");
        return SaucedemoLoginPage.getLoginErrorMessage(driver).isDisplayed();
    }

    @Step("Get error message text after unsuccessful login")
    public String getLoginErrorMessage() {
        log.info("Get error message text after unsuccessful login");
        return SaucedemoLoginPage.getLoginErrorMessage(driver).getText();
    }

}