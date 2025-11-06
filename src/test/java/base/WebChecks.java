package base;

import io.qameta.allure.Step;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.WaitHelper;

public class WebChecks {
    public WebDriver driver;
    public WebDriverWait wait;

    public WebChecks(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    @Step("Проверяем, что alert содержит текст '{expectedText}'")
    public void checkAlertTextWithoutClose(String expectedText) {
        wait.until(ExpectedConditions.alertIsPresent());
        Alert alert = driver.switchTo().alert();
        String actualText = alert.getText();

        Assertions.assertTrue(
                actualText.contains(expectedText),
                "Текст в alert не содержит ожидаемый текст. Ожидалось, что будет содержать: '"
                        + expectedText + "', но было: '" + actualText + "'"
        );
    }

    @Step("Проверяем, что элемент {nameElement} содержит текст '{expectedText}'")
    public void checkElementContainsText(WebElement element, String nameElement, String expectedText) {
        WaitHelper.waitForVisible(wait, element);

        String actualText = element.getText().trim();

        Assertions.assertTrue(
                actualText.contains(expectedText),
                "Текст элемента " + nameElement + " не содержит ожидаемый текст. " +
                        "Ожидалось, что будет содержать: '" + expectedText + "', но было: '" + actualText + "'"
        );
    }
}
