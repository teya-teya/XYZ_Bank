package tests.api;

import api.checks.ApiChecks;
import api.models.EntityRequest;
import api.models.EntityResponse;
import api.services.EntityService;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Epic("API Tests")
@Feature("Entity API - Positive Tests")
public class EntityPositiveTests {

    private final EntityService service = new EntityService();
    private final ApiChecks check = new ApiChecks();
    private final List<String> entitiesIds = new ArrayList<>();

    @AfterEach
    void cleanUp() {
        for (String id : entitiesIds) {
            try {
                Response resp = service.deleteEntityResponse(id);
                if (resp.statusCode() != 204 && resp.statusCode() != 404) {
                    check.statusCodeIsCorrect(resp, 204);
                }
            } catch (Exception e) {
                System.out.println("Не удалось удалить сущность с id = " + id);
            }
        }
        entitiesIds.clear();
    }

    private EntityRequest buildRequest() {
        EntityRequest req = new EntityRequest();
        req.setTitle("Test Entity");
        req.setVerified(true);
        req.setImportant_numbers(Arrays.asList(1, 2, 3));

        EntityRequest.AdditionRequest add = new EntityRequest.AdditionRequest();
        add.setAdditional_info("Info");
        add.setAdditional_number(100);
        req.setAddition(add);

        return req;
    }

    @Test
    @DisplayName("Положительный: создание сущности")
    @Severity(SeverityLevel.BLOCKER)
    void createEntity_positive() {
        EntityRequest body = buildRequest();

        Response response = service.createEntityResponse(body);
        check.statusCodeIsCorrect(response, 200);

        String id = response.asString().trim();
        check.objectIsNotNull(id);
        entitiesIds.add(id);
    }

    @Test
    @DisplayName("Положительный: получение сущности")
    @Severity(SeverityLevel.CRITICAL)
    void getEntity_positive() {
        String id = service.createEntity(buildRequest());
        entitiesIds.add(id);

        Response resp = service.getEntityResponse(id);
        check.statusCodeIsCorrect(resp, 200);

        EntityResponse entity = resp.as(EntityResponse.class);
        check.objectIsNotNull(entity);
    }

    @Test
    @DisplayName("Положительный: получение списка сущностей")
    @Severity(SeverityLevel.NORMAL)
    void getAllEntities_positive() {
        Response resp = service.getAllEntitiesResponse();
        check.statusCodeIsCorrect(resp, 200);
        List<EntityResponse> entities = resp.jsonPath().getList("entity", EntityResponse.class);

        check.objectIsNotNull(entities);
        check.listIsNotEmpty(entities);

        EntityResponse firstEntity = entities.get(0);
        check.objectIsNotNull(firstEntity.getId());
        check.objectIsNotNull(firstEntity.getTitle());
    }

    @Test
    @DisplayName("Положительный: обновление сущности")
    @Severity(SeverityLevel.CRITICAL)
    void updateEntity_positive() {
        String id = service.createEntity(buildRequest());
        entitiesIds.add(id);

        EntityRequest updated = buildRequest();
        updated.setTitle("Updated Title");

        Response updateResp = service.updateEntityResponse(id, updated);
        check.statusCodeIsCorrect(updateResp, 204);

        EntityResponse after = service.getEntity(id);
        assertEquals("Updated Title", after.getTitle(), "Title сущности должен быть 'Updated Title' после обновления");
    }

    @Test
    @DisplayName("Положительный: удаление сущности")
    @Severity(SeverityLevel.NORMAL)
    void deleteEntity_positive() {
        String id = service.createEntity(buildRequest());

        Response response = service.deleteEntityResponse(id);
        check.statusCodeIsCorrect(response, 204);
    }
}
