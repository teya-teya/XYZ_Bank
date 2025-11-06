package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.WaitHelper;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CustomersPage {
    public WebDriver driver;
    public WebDriverWait wait;
    public Actions actions;

    public CustomersPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
        this.actions = new Actions(driver);
        PageFactory.initElements(driver, this);
    }

    @FindBy(css = "table")
    public WebElement tableCustomers;

    @FindBy(css = "thead td")
    public List<WebElement> columnNames;

    @FindBy(css = "tbody tr")
    public List<WebElement> rowsOfCustomer;

    @FindBy(xpath = "//tbody//tr/td[position() = 1]")
    public List<WebElement> cellsOfFirstName;

    @Step("Нажимаем на заголовок столбца с текстом '{columnName}'")
    public void clickColumnByName(String columnName) {
        WaitHelper.waitForElementsVisible(wait, columnNames);
        WebElement column = columnNames.stream()
                .filter(col -> col.getText().contains(columnName)
                        || !col.findElements(By.xpath(".//*[contains(text(), '" + columnName + "')]")).isEmpty())
                .findFirst()
                .orElseThrow(() -> new RuntimeException(
                        "Колонка с текстом '" + columnName + "' не найдена"));

        column.click();
    }

    @Step("Удаляем клиента с именем '{customerName}'")
    public void deleteCustomerByName(String customerName) {
        for (WebElement row : rowsOfCustomer) {
            if (row.getText().contains(customerName)) {
                WebElement deleteButton = row.findElement(By.xpath(".//button[text()='Delete']"));
                WaitHelper.waitForClickable(wait, deleteButton);
                deleteButton.click();
                break;
            }
        }
    }

    /**
     *
     * @param columnCells ячейки столбца
     * @return коллекция содержания ячеек
     */
    public List<String> getValuesFromColumn(List<WebElement> columnCells) {
        return columnCells.stream()
                .map(cell -> cell.getText().trim())
                .collect(Collectors.toList());
    }

    /**
     *
     * @param names коллекция имен
     * @return имя, имеющее длину среднего арифметического длин списка имен
     */
    public String getNameClosestToAverage(List<String> names) {
        if (names.isEmpty()) return null;

        double average = names.stream()
                .mapToInt(String::length)
                .average()
                .orElse(0);

        Optional<String> closestName = names.stream()
                .min(Comparator.comparingInt(name -> Math.abs(name.length() - (int) Math.round(average)))
                );

        return closestName.orElse(null);
    }
}
