package tests;

import base.BaseTest;
import base.WebChecks;
import io.qameta.allure.AllureId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.AddCustomerPage;
import pages.CustomersPage;
import pages.NavigationButtonsComponent;
import utils.RandomUtils;

import static enums.UrlConstants.MANAGER;
import static io.qameta.allure.Allure.step;

public class AddCustomerTest extends BaseTest {
    WebChecks webChecks;
    AddCustomerPage addCustomerPage;
    NavigationButtonsComponent navBtns;
    CustomersPage customersPage;

    @BeforeEach
    public void start() {
        getDriver().get(config.getBaseUrl() + MANAGER.getValue());
        webChecks = new WebChecks(getDriver(), getWait());
        addCustomerPage = new AddCustomerPage(getDriver(), getWait());
        navBtns = new NavigationButtonsComponent(getDriver(), getWait());
        customersPage = new CustomersPage(getDriver(), getWait());
    }

    @Test
    @AllureId("TC-001")
    @DisplayName("Создание клиента с заполнением всех полей")
    void testAddCustomer() {
        String postCode = RandomUtils.tenDigits();
        String firstName = RandomUtils.generateFirstNameFromDigits(postCode);

        navBtns.clickBtnCenter("Add Customer");
        addCustomerPage.fillInput("First Name", firstName)
                        .fillInput("Last Name", RandomUtils.lastName())
                        .fillInput("Post Code", postCode)
                        .clickBtnAddCustomer();

        webChecks.checkAlertTextWithoutClose("Customer added successfully with customer id :");

        addCustomerPage.clickOkOnAlert();
        navBtns.clickBtnCenter("Customers");

        webChecks.checkElementContainsText(customersPage.tableCustomers, "Таблица клиентов", firstName);

        step("Постусловие", () -> customersPage.deleteCustomerByName(firstName));
    }
}
