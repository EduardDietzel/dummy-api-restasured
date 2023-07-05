package tests;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static io.restassured.RestAssured.given;

public class BaseTest {
    final static String BASE_URI = "https://dummyapi.io/data/v1";
    // принято в базовом урле не ставить в конце слеш, а эндпоинты начинать со слеша

    final static String APP_ID_VALUE = "6409b87d8be81d429a8276f3";

    static RequestSpecification specification = new RequestSpecBuilder()
            .setBaseUri(BASE_URI)
            .addHeader("app-id", APP_ID_VALUE)
            .setContentType(ContentType.JSON)
            .build();

    public static Response getRequest(String endPoint, int expectedStatusCode) {
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

    public static Response getRequestWithOneParam(String endPoint, int expectedStatusCode, String paramName, int paramValue) {
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

    public static Response postRequest(String endPoint, int expectedStatusCode, Object body) {
        Response response = given()
                .spec(specification)
                .body(body)
                .when()
                .log().all()
                .post(endPoint)
                .then()
                .log().all()
                .statusCode(expectedStatusCode)
                .extract().response();
        return response;
    }

    // delete
    public static Response deleteRequest(String endPoint, int expectedStatusCode){
        Response response = given()
                .spec(specification)
                .when()
                .log().all()
                .delete(endPoint)
                .then()
                .log().all()
                .statusCode(expectedStatusCode)
                .extract().response();
        return response;
    }

    // put Request
    public static Response putRequest(String endPoint, int expectedStatusCode, Object body) {
        Response response = given()
                .spec(specification)
                .body(body)
                .when()
                .log().all()
                .put(endPoint)
                .then()
                .log().all()
                .statusCode(expectedStatusCode)
                .extract().response();
        return response;
    }

    public static String getRandomEmail() {
        String SALTCHARS = "abcdefghigk1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 20) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr + "@gmail.com";

    }

    public static Response getRequestWithBody(String endPoint, Integer responseCode,  Object body) {
        Response response = given()
                .spec(specification)
                .body(body)
                .when()
                .log().all()
                .get(endPoint)
                .then()
                .log().all()
                .statusCode(responseCode)
                .extract().response();
        return response;
    }

}