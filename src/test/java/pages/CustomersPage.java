package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

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

}
