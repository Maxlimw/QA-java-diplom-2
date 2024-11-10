import browser.Browser;
import clients.UserClient;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.*;
import model.UserData;
import model.UserCredentials;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.openqa.selenium.WebDriver;
import pages.*;
import generators.UserGenerator;
import io.restassured.response.Response;
import utils.WaitUtils;

import static org.junit.Assert.*;

public class LogoutTest {
    private WebDriver driver;
    private UserClient userClient;
    private UserData userData;
    private String accessToken;
    private WaitUtils waitUtils;

    @Before
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = Browser.createWebDriver();
        userClient = new UserClient();
        waitUtils = new WaitUtils(driver, 10); // Ожидание до 10 секунд
        String email = UserGenerator.getRandomEmail();
        String password = UserGenerator.getRandomPassword();
        String name = UserGenerator.getRandomName();
        userData = new UserData(email, password, name);
        userClient.register(userData);
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
        if (accessToken != null) {
            userClient.deleteUser(accessToken);
        }
    }

    @Test
    @DisplayName("Выход из аккаунта")
    @Description("Проверка выхода из аккаунта через кнопку 'Выйти' в личном кабинете")
    public void logoutTest() {
        driver.get("https://stellarburgers.nomoreparties.site/");
        MainPage mainPage = new MainPage(driver);
        mainPage.clickLoginButton();
        LoginPage loginPage = new LoginPage(driver);
        loginPage.setEmail(userData.getEmail());
        loginPage.setPassword(userData.getPassword());
        loginPage.clickLoginButton();
        mainPage.clickPersonalCabinetButton();
        AccountPage accountPage = new AccountPage(driver);
        accountPage.clickLogoutButton();
        assertTrue(driver.getCurrentUrl().contains("/login"));
        Response loginResponse = userClient.login(new UserCredentials(userData.getEmail(), userData.getPassword()));
        accessToken = loginResponse.then().extract().path("accessToken");
    }
}