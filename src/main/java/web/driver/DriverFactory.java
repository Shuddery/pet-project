package web.driver;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class DriverFactory {

    private static WebDriver driver;

    private DriverFactory() {}

    /**
     * Точка входа для получения драйвера.
     * @param browserName Дефолтное имя браузера при локальном запуске (из IDE)
     */
    public static WebDriver getDriver(String browserName) {
        if (driver == null) {
            if (System.getenv("CI") != null) {
                driver = createRemoteChrome();
            } else {
                driver = createLocalDriver(browserName.toLowerCase());
            }
        }
        return driver;
    }

    /**
     * Локальный запуск на вашем компьютере (для отладки автотестов)
     */
    private static WebDriver createLocalDriver(String browser) {
        return switch (browser) {
            case "edge" -> {
                WebDriverManager.edgedriver().setup();
                yield new EdgeDriver(getEdgeOptions());
            }
            default -> {
                WebDriverManager.chromedriver().setup();
                yield new ChromeDriver(getChromeOptions());
            }
        };
    }

    private static WebDriver createRemoteChrome() {
        try {
            String selenoidUrl = "http://docker.internal";
            DesiredCapabilities capabilities = new DesiredCapabilities();

            capabilities.setBrowserName("chrome");

            Map<String, Object> selenoidOptions = new HashMap<>();
            selenoidOptions.put("enableVNC", true);
            selenoidOptions.put("enableVideo", false);
            capabilities.setCapability("selenoid:options", selenoidOptions);

            capabilities.setCapability(ChromeOptions.CAPABILITY, getChromeOptions());

            return new RemoteWebDriver(new URL(selenoidUrl), capabilities);
        } catch (MalformedURLException e) {
            throw new RuntimeException("Критическая ошибка: Некорректный URL для Selenoid", e);
        }
    }

    private static ChromeOptions getChromeOptions() {
        ChromeOptions chromeOptions = new ChromeOptions();
        Map<String, Object> prefs = new HashMap<>();

        chromeOptions.addArguments("--headless");
        chromeOptions.addArguments("--no-sandbox");
        chromeOptions.addArguments("--disable-dev-shm-usage");
        chromeOptions.addArguments("--disable-gpu");
        chromeOptions.addArguments("--window-size=1920,1200");
        chromeOptions.addArguments("--user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/109.0.0.0 Safari/537.36");

        prefs.put("profile.password_manager_leak_detection", false);
        chromeOptions.setExperimentalOption("prefs", prefs);

        return chromeOptions;
    }

    private static EdgeOptions getEdgeOptions() {
        EdgeOptions edgeOptions = new EdgeOptions();
        edgeOptions.addArguments("--headless");
        edgeOptions.addArguments("--no-sandbox");
        edgeOptions.addArguments("--disable-dev-shm-usage");
        edgeOptions.addArguments("--disable-gpu");
        edgeOptions.addArguments("--window-size=1920,1200");
        return edgeOptions;
    }

    public static void closeDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}
