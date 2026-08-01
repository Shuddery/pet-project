package web.tests.saucedemo;

import builder.Credentials;
import listener.TestListener;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import utils.IConstants;
import utils.PropertyReader;
import web.helpers.saucedemo.SaucedemoLoginHelper;
import web.helpers.saucedemo.SaucedemoProductsHelper;
import web.tests.CommonConditions;

@ExtendWith(TestListener.class)
public class ProductsPageTests extends CommonConditions {

    private static final Credentials credentials = new Credentials.Builder()
            .setUsername(IConstants.standardUsername)
            .setPassword(IConstants.password)
            .build();

    private static SaucedemoProductsHelper saucedemoProductsHelper;

    @BeforeAll
    public static void navigateToLoginPage() {
        SaucedemoLoginHelper saucedemoLoginHelper = new SaucedemoLoginHelper(driver);
        saucedemoLoginHelper.openPage(PropertyReader.getSaucedemoUrl());
        saucedemoProductsHelper = saucedemoLoginHelper.fillUsernameField(credentials)
                .fillPasswordField(credentials)
                .clickLoginButton();
    }

    @Test
    public void isStandardUserLoginSuccessful() {
        Assertions.assertTrue(saucedemoProductsHelper.isUrlContainsProductsPath());
    }
}
