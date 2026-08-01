package web.driver;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.HashMap;
import java.util.Map;

public class DriverFactory {

    private static WebDriver driver;
    private DriverFactory() {};

    public static WebDriver getDriver(String browserName) {
        if (driver == null) {
            driver = switch (browserName) {
                case "firefox" -> {
                    WebDriverManager.firefoxdriver().setup();
                    yield new FirefoxDriver();
                }
                case "edge" -> {
                    WebDriverManager.edgedriver().setup();
                    yield new EdgeDriver();
                }
                default -> {
                    WebDriverManager.chromedriver().setup();
                    yield new ChromeDriver(getChromeOptions());
                }
            };
        }
        return driver;
    }

    private static ChromeOptions getChromeOptions() {
        ChromeOptions chromeOptions = new ChromeOptions();
        Map<String, Object> prefs = new HashMap<>();
        chromeOptions.addArguments("--headless");
        prefs.put("profile.password_manager_leak_detection", false);
        chromeOptions.setExperimentalOption("prefs", prefs);
        chromeOptions.addArguments("--user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/109.0.0.0 Safari/537.36");
        chromeOptions.addArguments("--window-size=1920,1200");
        chromeOptions.addArguments("--no-sandbox"); // Отключает sandbox (полезно в Docker-контейнерах и CI)
        chromeOptions.addArguments("--disable-dev-shm-usage"); // Решает проблему с нехваткой памяти в Docker-контейнерах (shared memory).
        return chromeOptions;
    }

    public static void closeDriver() {
        driver.quit();
        driver = null;
    }
}