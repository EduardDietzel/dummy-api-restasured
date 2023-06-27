package tests;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class BaseTest {
    final static String BASE_URI = "https://dummyapi.io/data/v1";
    // принято в базовом урле не ставить в конце слеш, а эндпоинты начинать со слеша

    final static String APP_ID_VALUE = "6409b87d8be81d429a8276f3";

    static RequestSpecification specification = new RequestSpecBuilder()
            .setBaseUri(BASE_URI)
            .addHeader("app-id", APP_ID_VALUE)
            .build();

    public static Response getRequest(String endPoint, int expectedStatusCode){
        Response response = given()
//                .baseUri(BASE_URI)
                .spec(specification)
                .when()
                .log().all()
                .get(endPoint)
                .then()
                .log().all()
                .statusCode(expectedStatusCode)
                .extract().response();
        return response;
    }

    public static Response getRequestWithOneParam(String endPoint, int expectedStatusCode, String paramName, int paramValue){
        Response response = given()
                .spec(specification)
                .when()
                .queryParam(paramName, paramValue)
                .log().all()
                .get(endPoint)
                .then()
                .log().all()
                .statusCode(expectedStatusCode)
                .extract().response();
        return response;
    }
}
