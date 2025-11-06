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
import utils.WaitHelper;

import java.util.List;

import static enums.UrlConstants.MANAGER;

public class CustomersTest extends BaseTest {
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
    @AllureId("TC-002")
    @DisplayName("Сортировка клиентов по имени")
    void testSorted() {
        navBtns.clickBtnCenter("Customers");
        customersPage.clickColumnByName("First Name");
        List<String> beforeSort = customersPage.rowsOfCustomer.stream()
                .map(row -> row.getText().trim())
                .toList();

        webChecks.checkColumnSorted(customersPage.cellsOfFirstName, "First Name", WebChecks.SortOrder.DESC)
                .checkRowsContentSame(beforeSort, customersPage.rowsOfCustomer);

        customersPage.clickColumnByName("First Name");

        webChecks.checkColumnSorted(customersPage.cellsOfFirstName, "First Name", WebChecks.SortOrder.ASC)
                .checkRowsContentSame(beforeSort, customersPage.rowsOfCustomer);

    }

    @Test
    @AllureId("TC-003")
    @DisplayName("Удаление клиента")
    void testDeleteCustomer() {
        navBtns.clickBtnCenter("Customers");
        WaitHelper.waitForVisible(getWait(), customersPage.tableCustomers);
        List<String> firstName = customersPage.getValuesFromColumn(customersPage.cellsOfFirstName);
        String name = customersPage.getNameClosestToAverage(firstName);

        customersPage.deleteCustomerByName(name);

        webChecks.checkElementNotContainsText(customersPage.tableCustomers, "Таблица клиентов", name);
    }

}
