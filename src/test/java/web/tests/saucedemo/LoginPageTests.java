package web.tests.saucedemo;

import listener.TestListener;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebElement;
import utils.IConstants;
import utils.PropertyReader;
import web.helpers.saucedemo.SaucedemoLoginHelper;
import web.pages.saucedemo.SaucedemoLoginPage;
import web.tests.CommonConditions;

@ExtendWith(TestListener.class)
public class LoginPageTests extends CommonConditions {

    private static SaucedemoLoginHelper saucedemoLoginHelper;

    @BeforeAll
    public static void navigateToLoginPage() {
        saucedemoLoginHelper = new SaucedemoLoginHelper(driver);
        saucedemoLoginHelper.openPage(PropertyReader.getSaucedemoUrl());
    }

    @Test
    public void isTitleDisplayed() {
        Assertions.assertEquals(IConstants.titleOfLoginPage, saucedemoLoginHelper.getTitleOfLoginPageText());
    }

    @Test
    public void isLoginButtonClickable() {
        Assertions.assertTrue(saucedemoLoginHelper.isLoginButtonClickable());
    }

    @Test
    public void checkLockedUserErrorMessage() {
        saucedemoLoginHelper.clearUsernameField()
                .fillUsernameField(IConstants.lockedUsername)
                .clearPasswordField()
                .fillPasswordField(IConstants.password)
                .clickLoginButton();
        Assertions.assertEquals(IConstants.lockedUserErrorMessage, saucedemoLoginHelper.getLoginErrorMessage());
    }

     @Test
    public void checkLoginWithWrongPassword() {
        saucedemoLoginHelper.clearUsernameField()
                .fillUsernameField(IConstants.lockedUsername)
                .clearPasswordField()
                .fillPasswordField(IConstants.wrongPassword)
                .clickLoginButton();
        Assertions.assertEquals(IConstants.wrongCredentialsMessage, saucedemoLoginHelper.getLoginErrorMessage());
    }

    @Test
    public void checkLoginWithWrongUsername() {
        saucedemoLoginHelper.clearUsernameField()
                .fillUsernameField(IConstants.wrongUsername)
                .clearPasswordField()
                .fillPasswordField(IConstants.password)
                .clickLoginButton();
        Assertions.assertEquals(IConstants.wrongCredentialsMessage, saucedemoLoginHelper.getLoginErrorMessage());
    }

    @Test
    public void checkLoginWithEmptyUsername() {
        saucedemoLoginHelper.clearUsernameField()
                .clearPasswordField()
                .fillPasswordField(IConstants.password)
                .clickLoginButton();
        Assertions.assertEquals(IConstants.emptyUsernameMessage, saucedemoLoginHelper.getLoginErrorMessage());
    }

    @Test
    public void checkLoginWithEmptyCredentials() {
        saucedemoLoginHelper.clearUsernameField()
                .clearPasswordField()
                .clickLoginButton();
        Assertions.assertEquals(IConstants.emptyUsernameMessage, saucedemoLoginHelper.getLoginErrorMessage());
    }

    @Test
    public void checkLoginWithEmptyPassword() {
        saucedemoLoginHelper.clearUsernameField()
                .fillUsernameField(IConstants.lockedUsername)
                .clearPasswordField()
                .clickLoginButton();
        Assertions.assertEquals(IConstants.emptyPasswordMessage, saucedemoLoginHelper.getLoginErrorMessage());
    }

    @Test
    public void isErrorMessageHiddenAfterClickOnErrorButton() {
        saucedemoLoginHelper.clearUsernameField()
                .clearPasswordField()
                .clickLoginButton();
        saucedemoLoginHelper.clickErrorButton();
        Assertions.assertTrue(saucedemoLoginHelper.isErrorMessageHidden());
    }

 }