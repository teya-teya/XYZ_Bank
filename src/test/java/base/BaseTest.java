package base;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Allure;
import org.aeonbits.owner.ConfigFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.WebDriverConfig;

import java.io.ByteArrayInputStream;
import java.time.Duration;

@ExtendWith(BaseTest.ScreenshotTestWatcher.class)
public class BaseTest {
    protected ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    protected ThreadLocal<WebDriverWait> wait = new ThreadLocal<>();
    protected WebDriverConfig config = ConfigFactory.create(WebDriverConfig.class);

    @BeforeEach
    public void setup() {
        WebDriverManager.chromedriver().setup();
        driver.set(new ChromeDriver());
        wait.set(new WebDriverWait(driver.get(), Duration.ofSeconds(10)));
        driver.get().manage().window().maximize();
    }

    protected WebDriver getDriver() {
        return driver.get();
    }

    protected WebDriverWait getWait() {
        return wait.get();
    }

    public static class ScreenshotTestWatcher implements TestWatcher {
        @Override
        public void testFailed(ExtensionContext context, Throwable cause) {
            Object testInstance = context.getRequiredTestInstance();
            if (testInstance instanceof BaseTest baseTest) {
                baseTest.takeScreenshot("Скриншот при ошибке");
                baseTest.closeDriver();
            }
        }

        @Override
        public void testSuccessful(ExtensionContext context) {
            Object testInstance = context.getRequiredTestInstance();
            if (testInstance instanceof BaseTest baseTest) {
                baseTest.closeDriver();
            }
        }
    }


    protected void takeScreenshot(String screenshotName) {
        WebDriver currentDriver = getDriver();
        if (currentDriver instanceof TakesScreenshot) {
            byte[] screenshot = ((TakesScreenshot) currentDriver).getScreenshotAs(OutputType.BYTES);
            Allure.addAttachment(screenshotName, new ByteArrayInputStream(screenshot));
        }
    }

    protected void closeDriver() {
        WebDriver currentDriver = getDriver();
        if (currentDriver != null) {
            currentDriver.quit();
            driver.remove();
            wait.remove();
        }
    }
}
