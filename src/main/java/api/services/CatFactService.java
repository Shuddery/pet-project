package api.services;

import api.AbstractService;
import io.qameta.allure.Step;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class CatFactService extends AbstractService {

    @Step("Get request to /" + FACT_ENDPOINT)
    public Response getFact() {
        log.info("Sending GET request to /" + FACT_ENDPOINT);
        RequestSpecification requestSpecification = given()
                .basePath(FACT_ENDPOINT);
        return client.sendRequest(Method.GET, requestSpecification);
    }

    @Step("Get request with query max_length to /" + FACT_ENDPOINT)
    public Response getFactWithLength(int max_length) {
        log.info("Sending GET request to /{} with max_length={}", FACT_ENDPOINT, max_length);
        RequestSpecification requestSpecification = given()
                .basePath(FACT_ENDPOINT)
                .queryParam(QUERY_PARAM_LENGTH, max_length);
        return client.sendRequest(Method.GET, requestSpecification);
    }

    @Step("Get request to /" + FACTS_ENDPOINT)
    public Response getFacts() {
        log.info("Sending GET request to /" + FACTS_ENDPOINT);
        RequestSpecification requestSpecification = given()
                .basePath(FACTS_ENDPOINT);
        return client.sendRequest(Method.GET, requestSpecification);
    }

    @Step("Get request with query max_length to /" + FACTS_ENDPOINT)
    public Response getFactsWithLength(int max_length) {
        log.info("Sending GET request to /{} with max_length={}", FACTS_ENDPOINT, max_length);
        RequestSpecification requestSpecification = given()
                .basePath(FACTS_ENDPOINT)
                .queryParam(QUERY_PARAM_LENGTH, max_length);
        return client.sendRequest(Method.GET, requestSpecification);
    }


    @Step("Get request with query limit to /" + FACTS_ENDPOINT)
    public Response getFactsWithLimit(int limit) {
        log.info("Sending GET request to /{} with limit={}", FACTS_ENDPOINT, limit);
        RequestSpecification requestSpecification = given()
                .basePath(FACTS_ENDPOINT)
                .queryParam(QUERY_PARAM_LIMIT, limit);
        return client.sendRequest(Method.GET, requestSpecification);
    }
}
