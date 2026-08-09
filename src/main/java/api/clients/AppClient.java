package api.clients;

import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import utils.PropertyReader;

import static io.restassured.RestAssured.given;

public class AppClient {

    private static final String BASE_URI = PropertyReader.getCatfactsUrl();

    public Response sendRequest(Method method, RequestSpecification requestSpec) {
        return given()
                .spec(requestSpec)
                .baseUri(BASE_URI)
                .contentType(ContentType.JSON)
                .when()
                .request(method)
                .then()
                .extract()
                .response();
    }
}