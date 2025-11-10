package base;

import io.qameta.allure.Step;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.WaitHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

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

    @Step("Проверяем, что элемент {nameElement} не содержит текст '{unexpectedText}'")
    public void checkElementNotContainsText(WebElement element, String nameElement, String unexpectedText) {
        WaitHelper.waitForVisible(wait, element);

        String actualText = element.getText().trim();

        Assertions.assertFalse(
                actualText.contains(unexpectedText),
                "Текст элемента " + nameElement + " содержит запрещённый текст: '"
                        + unexpectedText + "'. Фактический текст: '" + actualText + "'"
        );
    }

    public enum SortOrder {
        ASC,
        DESC
    }
    @Step("Проверяем, что колонка '{columnName}' отсортирована в порядке: {order}")
    public WebChecks checkColumnSorted(List<WebElement> elements, String columnName, SortOrder order) {

        List<String> values = elements.stream()
                .map(cell -> cell.getText().trim())
                .toList();

        List<String> sorted = new ArrayList<>(values);

        if (order == SortOrder.ASC) {
            sorted.sort(String::compareToIgnoreCase);
        } else {
            sorted.sort(Collections.reverseOrder(String.CASE_INSENSITIVE_ORDER));
        }

        Assertions.assertEquals(values, sorted,
                "Колонка '" + columnName + "' должна быть отсортирована в порядке: " + order);
        return this;
    }

    @Step("Проверяем, что строки после сортировки содержат те же элементы (по уникальному содержимому строки)")
    public void checkRowsContentSame(List<String> beforeSort, List<WebElement> rows) {

        List<String> afterSort = rows.stream()
                .map(row -> row.getText().trim())
                .toList();

        Assertions.assertEquals(new HashSet<>(beforeSort), new HashSet<>(afterSort),
                "После сортировки содержимое строк таблицы изменилось!");
    }


}
