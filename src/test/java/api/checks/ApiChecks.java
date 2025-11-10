package api.checks;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.assertj.core.api.Assertions;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ApiChecks {

    @Step("Проверить статус код ответа: ожидается {expectedStatus}")
    public void statusCodeIsCorrect(Response response, int expectedStatus) {
        assertEquals(expectedStatus, response.statusCode(),
                "Статус код должен быть " + expectedStatus);
    }

    @Step("Проверить что объект не null")
    public void objectIsNotNull(Object obj) {
        assertNotNull(obj, "Объект не должен быть null");
    }

    @Step("Проверить что список не пустой")
    public void listIsNotEmpty(List<?> list) {
        assertFalse(list.isEmpty(), "Список не должен быть пустым");
    }
}
