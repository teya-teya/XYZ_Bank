package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.WaitHelper;

import java.util.List;

public class NavigationButtonsComponent {
    public WebDriver driver;
    public WebDriverWait wait;
    public Actions actions;

    public NavigationButtonsComponent(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
        this.actions = new Actions(driver);
        PageFactory.initElements(driver, this);
    }

    @FindBy(css = "div.center button")
    public List<WebElement> buttonsCenter;

    @Step("Нажимаем на кнопку '{nameBtn}' верхнего меню")
    public void clickBtnCenter(String nameBtn) {
        WaitHelper.waitForElementsVisible(wait, buttonsCenter);
        WebElement button = buttonsCenter.stream()
                .filter(btn -> nameBtn.equals(btn.getText()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Кнопка '" + nameBtn + "' не найдена"));

        WaitHelper.waitForClickable(wait, button);
        button.click();
    }
}
