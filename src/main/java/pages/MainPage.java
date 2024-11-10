package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class MainPage {
    private WebDriver driver;
    private WebDriverWait wait;

    private By loginButton = By.xpath(".//button[text()='Войти в аккаунт']");
    private By personalCabinetButton = By.xpath(".//p[text()='Личный Кабинет']");
    private By constructorButton = By.xpath(".//p[text()='Конструктор']");
    private By logo = By.className("AppHeader_header__logo__2D0X2");
    private By bunsSection = By.xpath(".//span[text()='Булки']");
    private By saucesSection = By.xpath(".//span[text()='Соусы']");
    private By fillingsSection = By.xpath(".//span[text()='Начинки']");

    public MainPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void clickLoginButton() {
        WebElement loginBtn = wait.until(ExpectedConditions.elementToBeClickable(loginButton));
        loginBtn.click();
    }

    public void clickPersonalCabinetButton() {
        WebElement cabinetBtn = wait.until(ExpectedConditions.elementToBeClickable(personalCabinetButton));
        cabinetBtn.click();
    }

    public void clickConstructorButton() {
        WebElement constructorBtn = wait.until(ExpectedConditions.elementToBeClickable(constructorButton));
        constructorBtn.click();
    }

    public void clickLogo() {
        WebElement logoElement = wait.until(ExpectedConditions.elementToBeClickable(logo));
        logoElement.click();
    }

    public void selectBunsSection() {
        WebElement buns = wait.until(ExpectedConditions.elementToBeClickable(bunsSection));
        buns.click();
    }

    public void selectSaucesSection() {
        WebElement sauces = wait.until(ExpectedConditions.elementToBeClickable(saucesSection));
        sauces.click();
    }

    public void selectFillingsSection() {
        WebElement fillings = wait.until(ExpectedConditions.elementToBeClickable(fillingsSection));
        fillings.click();
    }

    public boolean isBunsSectionActive() {
        WebElement buns = wait.until(ExpectedConditions.visibilityOfElementLocated(bunsSection));
        return buns.getAttribute("class").contains("current");
    }

    public boolean isSaucesSectionActive() {
        WebElement sauces = wait.until(ExpectedConditions.visibilityOfElementLocated(saucesSection));
        return sauces.getAttribute("class").contains("current");
    }

    public boolean isFillingsSectionActive() {
        WebElement fillings = wait.until(ExpectedConditions.visibilityOfElementLocated(fillingsSection));
        return fillings.getAttribute("class").contains("current");
    }
}
