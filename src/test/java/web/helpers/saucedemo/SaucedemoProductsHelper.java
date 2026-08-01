package web.helpers.saucedemo;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import utils.IConstants;
import web.helpers.AbstractHelper;
import web.pages.saucedemo.SaucedemoProductsPage;
import web.waits.Waits;

public class SaucedemoProductsHelper extends AbstractHelper {

    public SaucedemoProductsHelper(WebDriver driver) {super(driver);}

    @Step("Get text of header in products page")
    public String getTitleOfProductsPageText() {
        log.info("Get title in products page");
        return SaucedemoProductsPage.getTitle(driver).getText();
    }

    @Step("Is url contains products path")
    public boolean isUrlContainsProductsPath() {
        log.info("Is url contains products path - {}", IConstants.productsPagePath);
        return Waits.isUrlContainsPath(driver, IConstants.productsPagePath);
    }

}
