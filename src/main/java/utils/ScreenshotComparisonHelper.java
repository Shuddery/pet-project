package utils;

import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.comparison.ImageDiff;
import ru.yandex.qatools.ashot.comparison.ImageDiffer;
import web.driver.DriverFactory;

import static utils.FileUtils.*;

public class ScreenshotComparisonHelper {

    public Screenshot takeActualScreenshot() {
        Screenshot takeScreenshot = new AShot().takeScreenshot(DriverFactory.getDriver(PropertyReader.getBrowser()));
        savePng(takeScreenshot.getImage(), IConstants.pathToResources + "actual.png");
        return takeScreenshot;
    }

    public Screenshot getExpectedScreenshot() {
        return new Screenshot(getImage(IConstants.pathToResources + "expected.png"));
    }

    @Step("Compare screenshots")
    public ImageDiff compareScreenshots(Screenshot actualImage, Screenshot expectedImage, int diffSizeTrigger) {
        Allure.addAttachment("Actual", getInputStream(IConstants.pathToResources + "actual.png"));
        Allure.addAttachment("Expected", getInputStream(IConstants.pathToResources + "expected.png"));

        ImageDiff diff = new ImageDiffer().makeDiff(expectedImage, actualImage).withDiffSizeTrigger(diffSizeTrigger);

        if (diff.hasDiff()) {
            savePng(diff.getMarkedImage(), IConstants.pathToResources + "difference.png");
            Allure.addAttachment("Difference", getInputStream(IConstants.pathToResources + "difference.png"));
        }

        return diff;
    }
}