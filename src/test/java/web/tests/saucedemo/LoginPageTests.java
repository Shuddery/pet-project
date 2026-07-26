package web.tests.saucedemo;

import listener.TestListener;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import utils.PropertyReader;
import web.helpers.saucedemo.SaucedemoLoginHelper;
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
        Assertions.assertEquals(saucedemoLoginHelper.getTitleOfLoginPageText(), "Swag Labs");
    }


}