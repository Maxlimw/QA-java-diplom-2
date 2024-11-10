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
import pages.MainPage;
import pages.LoginPage;
import generators.UserGenerator;
import io.restassured.response.Response;
import pages.PasswordRecoveryPage;
import pages.RegistrationPage;
import utils.WaitUtils;

import static org.junit.Assert.*;

public class LoginTest {
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
    @DisplayName("Вход по кнопке 'Войти в аккаунт' на главной")
    @Description("Проверка входа пользователя через кнопку на главной странице")
    public void loginFromMainPageTest() {
        driver.get("https://stellarburgers.nomoreparties.site/");
        MainPage mainPage = new MainPage(driver);
        mainPage.clickLoginButton();
        LoginPage loginPage = new LoginPage(driver);
        loginPage.setEmail(userData.getEmail());
        loginPage.setPassword(userData.getPassword());
        loginPage.clickLoginButton();
        assertTrue(driver.getCurrentUrl().equals("https://stellarburgers.nomoreparties.site/"));
        Response loginResponse = userClient.login(new UserCredentials(userData.getEmail(), userData.getPassword()));
        accessToken = loginResponse.then().extract().path("accessToken");
    }

    @Test
    @DisplayName("Вход через кнопку 'Личный кабинет'")
    @Description("Проверка входа пользователя через кнопку 'Личный кабинет'")
    public void loginFromPersonalCabinetButtonTest() {
        driver.get("https://stellarburgers.nomoreparties.site/");
        MainPage mainPage = new MainPage(driver);
        mainPage.clickPersonalCabinetButton();
        LoginPage loginPage = new LoginPage(driver);
        loginPage.setEmail(userData.getEmail());
        loginPage.setPassword(userData.getPassword());
        loginPage.clickLoginButton();
        assertTrue(driver.getCurrentUrl().contains("/account/profile"));
        Response loginResponse = userClient.login(new UserCredentials(userData.getEmail(), userData.getPassword()));
        accessToken = loginResponse.then().extract().path("accessToken");
    }

    @Test
    @DisplayName("Вход через кнопку в форме регистрации")
    @Description("Проверка входа пользователя через кнопку в форме регистрации")
    public void loginFromRegistrationFormTest() {
        driver.get("https://stellarburgers.nomoreparties.site/register");
        RegistrationPage registrationPage = new RegistrationPage(driver);
        registrationPage.clickLoginLink();
        LoginPage loginPage = new LoginPage(driver);
        loginPage.setEmail(userData.getEmail());
        loginPage.setPassword(userData.getPassword());
        loginPage.clickLoginButton();
        assertTrue(driver.getCurrentUrl().equals("https://stellarburgers.nomoreparties.site/"));
        Response loginResponse = userClient.login(new UserCredentials(userData.getEmail(), userData.getPassword()));
        accessToken = loginResponse.then().extract().path("accessToken");
    }

    @Test
    @DisplayName("Вход через кнопку в форме восстановления пароля")
    @Description("Проверка входа пользователя через кнопку в форме восстановления пароля")
    public void loginFromPasswordRecoveryFormTest() {
        driver.get("https://stellarburgers.nomoreparties.site/forgot-password");
        PasswordRecoveryPage passwordRecoveryPage = new PasswordRecoveryPage(driver);
        passwordRecoveryPage.clickLoginLink();
        LoginPage loginPage = new LoginPage(driver);
        loginPage.setEmail(userData.getEmail());
        loginPage.setPassword(userData.getPassword());
        loginPage.clickLoginButton();
        assertTrue(driver.getCurrentUrl().equals("https://stellarburgers.nomoreparties.site/"));
        Response loginResponse = userClient.login(new UserCredentials(userData.getEmail(), userData.getPassword()));
        accessToken = loginResponse.then().extract().path("accessToken");
    }
}