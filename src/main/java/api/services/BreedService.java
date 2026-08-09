package api.services;

import api.AbstractService;
import io.qameta.allure.Step;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class BreedService extends AbstractService {

    @Step("Get request to /" + BREEDS_ENDPOINT)
    public Response getBreeds() {
        log.info("Sending GET request to /" + BREEDS_ENDPOINT);
        RequestSpecification requestSpecification = given()
                .basePath(BREEDS_ENDPOINT);
        return client.sendRequest(Method.GET, requestSpecification);
    }

    @Step("Get request with query limit to /" + BREEDS_ENDPOINT)
    public Response getBreedsWithQueryLimitParameter(int limit) {
        log.info("Sending GET request with query limit parameter to /" + BREEDS_ENDPOINT);
        RequestSpecification requestSpecification = given()
                .basePath(BREEDS_ENDPOINT)
                .queryParam(QUERY_PARAM_LIMIT, limit);
        return client.sendRequest(Method.GET, requestSpecification);
    }
}