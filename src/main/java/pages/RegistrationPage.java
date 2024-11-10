package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class RegistrationPage {
    private WebDriver driver;
    private WebDriverWait wait;

    private By nameField = By.xpath("//div[label[text()='Имя']]/input");

    private By emailField = By.xpath("//div[label[text()='Email']]/input");

    private By passwordField = By.xpath("//input[@name='Пароль']");

    private By registerButton = By.xpath("//button[text()='Зарегистрироваться']");

    private By passwordError = By.xpath("//p[text()='Некорректный пароль']");

    private By loginLink = By.xpath("//a[@href='/account']");

    public RegistrationPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void setName(String name) {
        WebElement nameInput = wait.until(ExpectedConditions.visibilityOfElementLocated(nameField));
        nameInput.clear();
        nameInput.sendKeys(name);
    }

    public void setEmail(String email) {
        WebElement emailInput = wait.until(ExpectedConditions.visibilityOfElementLocated(emailField));
        emailInput.clear();
        emailInput.sendKeys(email);
    }

    public void setPassword(String password) {
        WebElement passwordInput = wait.until(ExpectedConditions.visibilityOfElementLocated(passwordField));
        passwordInput.clear();
        passwordInput.sendKeys(password);
    }

    public void clickRegisterButton() {
        WebElement registerBtn = wait.until(ExpectedConditions.elementToBeClickable(registerButton));
        registerBtn.click();
    }

    public void clickLoginLink() {
        WebElement loginLnk = wait.until(ExpectedConditions.elementToBeClickable(loginLink));
        loginLnk.click();
    }

    public boolean isPasswordErrorVisible() {
        try {
            WebElement error = wait.until(ExpectedConditions.visibilityOfElementLocated(passwordError));
            return error.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public By getPasswordErrorLocator() {
        return passwordError;
    }
}
