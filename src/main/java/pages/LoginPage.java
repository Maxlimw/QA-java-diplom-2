package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage {
    private WebDriver driver;
    private WebDriverWait wait;

    private By emailField = By.xpath(".//label[text()='Email']/../input");
    private By passwordField = By.xpath(".//label[text()='Пароль']/../input");
    private By loginButton = By.xpath(".//button[text()='Войти']");
    private By registrationLink = By.xpath(".//a[@href='/register']");
    private By passwordRecoveryLink = By.xpath(".//a[@href='/forgot-password']");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void setEmail(String email) {
        WebElement emailElement = wait.until(ExpectedConditions.visibilityOfElementLocated(emailField));
        emailElement.sendKeys(email);
    }

    public void setPassword(String password) {
        WebElement passwordElement = wait.until(ExpectedConditions.visibilityOfElementLocated(passwordField));
        passwordElement.sendKeys(password);
    }

    public void clickLoginButton() {
        WebElement loginButtonElement = wait.until(ExpectedConditions.elementToBeClickable(loginButton));
        loginButtonElement.click();
    }

    public void clickRegistrationLink() {
        WebElement registrationLinkElement = wait.until(ExpectedConditions.elementToBeClickable(registrationLink));
        registrationLinkElement.click();
    }

    public void clickPasswordRecoveryLink() {
        WebElement passwordRecoveryLinkElement = wait.until(ExpectedConditions.elementToBeClickable(passwordRecoveryLink));
        passwordRecoveryLinkElement.click();
    }
}
