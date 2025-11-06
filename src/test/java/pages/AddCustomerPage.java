package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.WaitHelper;

import java.util.List;

public class AddCustomerPage {
    public WebDriver driver;
    public WebDriverWait wait;
    public Actions actions;

    public AddCustomerPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
        this.actions = new Actions(driver);
        PageFactory.initElements(driver, this);
    }

    @FindBy(css = "div.form-group input")
    public List<WebElement> inputs;

    @FindBy(xpath = "//button[text()='Add Customer']")
    public WebElement btnAddCustomer;

    @Step("Заполняем поле {nameInput} текстом '{text}'")
    public AddCustomerPage fillInput(String nameInput, String text) {
        WaitHelper.waitForElementsVisible(wait, inputs);
        WebElement input = inputs.stream()
                .filter(fld -> nameInput.equals(fld.getAttribute("placeholder")))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Поле '" + nameInput + "' не найдено"));

        WaitHelper.waitForVisible(wait, input);
        input.clear();
        input.sendKeys(text);
        return this;
    }

    @Step("Нажимаем кнопку 'AddCustomer'")
    public void clickBtnAddCustomer() {
        WaitHelper.waitForClickable(wait, btnAddCustomer);
        btnAddCustomer.click();
    }

    @Step("Нажимаем на кнопку 'ok' в alert")
    public void clickOkOnAlert() {
        wait.until(ExpectedConditions.alertIsPresent());
        Alert alert = driver.switchTo().alert();
        alert.accept();
    }
}
