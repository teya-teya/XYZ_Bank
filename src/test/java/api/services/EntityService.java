package api.services;

import api.models.EntityRequest;
import api.models.EntityResponse;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class EntityService {

    private RequestSpecification getRequest() {
        return given()
                .baseUri("http://localhost:8080/api")
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .log().all();
    }

    @Step("API: Создать сущность")
    public Response createEntityResponse(EntityRequest entity) {
        return getRequest()
                .body(entity)
                .post("/create");
    }

    public String createEntity(EntityRequest entity) {
        return createEntityResponse(entity).asString();
    }

    @Step("API: Получить сущность по ID")
    public Response getEntityResponse(String id) {
        return getRequest()
                .get("/get/{id}", id);
    }

    public EntityResponse getEntity(String id) {
        return getEntityResponse(id).as(EntityResponse.class);
    }

    @Step("API: Получить все сущности")
    public Response getAllEntitiesResponse() {
        return getRequest()
                .post("/getAll");
    }

    @Step("API: Обновить сущность")
    public Response updateEntityResponse(String id, EntityRequest entity) {
        return getRequest()
                .body(entity)
                .patch("/patch/{id}", id);
    }

    @Step("API: Удалить сущность")
    public Response deleteEntityResponse(String id) {
        return getRequest()
                .delete("/delete/{id}", id);
    }
}
