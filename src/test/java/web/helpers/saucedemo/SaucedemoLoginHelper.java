package web.helpers.saucedemo;

import io.qameta.allure.Step;
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
        return SaucedemoLoginPage.getTitleOfLoginPage(driver).getText();
    }
}