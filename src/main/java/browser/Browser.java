package browser;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class Browser {
    public static WebDriver createWebDriver() {
        String browser = System.getProperty("browser", "chrome").toLowerCase();
        switch (browser) {
            case "chrome":
                return createChromeDriver();
            case "yandex":
                return createYandexDriver();
            default:
                throw new IllegalArgumentException("Браузер-> " + browser + " не поддерживается");
        }
    }

    private static WebDriver createChromeDriver() {
        String driverPath = "/opt/homebrew/Caskroom/chromedriver/130.0.6723.116/chromedriver-mac-arm64/chromedriver";
        System.setProperty("webdriver.chrome.driver", driverPath);
        return new ChromeDriver();
    }

    private static WebDriver createYandexDriver() {
        System.setProperty("webdriver.chrome.driver", "/path/to/yandexdriver");

        ChromeOptions options = new ChromeOptions();
        options.setBinary("/Applications/Yandex.app/Contents/MacOS/Yandex");

        return new ChromeDriver(options);
    }
}