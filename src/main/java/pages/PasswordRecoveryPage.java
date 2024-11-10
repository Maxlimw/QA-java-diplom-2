package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class PasswordRecoveryPage {
    private WebDriver driver;
    private WebDriverWait wait;

    private By loginLink = By.xpath(".//a[@href='/login']");

    public PasswordRecoveryPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void clickLoginLink() {
        WebElement loginLinkElement = wait.until(ExpectedConditions.elementToBeClickable(loginLink));
        loginLinkElement.click();
    }
}
