import browser.Browser;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.openqa.selenium.WebDriver;
import pages.MainPage;
import utils.WaitUtils;

import static org.junit.Assert.*;

public class ConstructorSectionTest {
    private WebDriver driver;
    private WaitUtils waitUtils;

    @Before
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = Browser.createWebDriver();
        waitUtils = new WaitUtils(driver, 10); // Ожидание до 10 секунд
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    @DisplayName("Переход к разделу 'Соусы'")
    @Description("Проверка перехода к разделу 'Соусы' в конструкторе")
    public void switchToSaucesSectionTest() {
        driver.get("https://stellarburgers.nomoreparties.site/");
        MainPage mainPage = new MainPage(driver);
        mainPage.selectSaucesSection();
        assertTrue(mainPage.isSaucesSectionActive());
    }

    @Test
    @DisplayName("Переход к разделу 'Начинки'")
    @Description("Проверка перехода к разделу 'Начинки' в конструкторе")
    public void switchToFillingsSectionTest() {
        driver.get("https://stellarburgers.nomoreparties.site/");
        MainPage mainPage = new MainPage(driver);
        mainPage.selectFillingsSection();
        assertTrue(mainPage.isFillingsSectionActive());
    }

    @Test
    @DisplayName("Переход к разделу 'Булки'")
    @Description("Проверка перехода к разделу 'Булки' в конструкторе")
    public void switchToBunsSectionTest() {
        driver.get("https://stellarburgers.nomoreparties.site/");
        MainPage mainPage = new MainPage(driver);
        mainPage.selectSaucesSection();
        mainPage.selectBunsSection();
        assertTrue(mainPage.isBunsSectionActive());
    }
}
