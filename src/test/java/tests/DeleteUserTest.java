package tests;

import dto.CreateUserRequest;
import dto.ErrorMessageResponse;
import dto.UserFull;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static tests.BaseTest.*;

public class DeleteUserTest {

    @Test
    public void deleteExistingUser(){
        // create new user with requestUserId
        CreateUserRequest requestBody = new CreateUserRequest(getRandomEmail(), "John", "Smith");
        UserFull response = postRequest("/user/create", 200, requestBody).body().jsonPath().getObject("", UserFull.class);
        String idFromDeleteRequest =  response.getId();
        // Test
        // delete existing User by requestUserId
        String idFromDeleteResponse = deleteRequest("/user/" + idFromDeleteRequest, 200)
                .body().jsonPath().getString("id");
        assertEquals(idFromDeleteRequest, idFromDeleteResponse);
    }

    // delete already deleted user
    // 1. Status code is 404
    // 2. RESOURCE_NOT_FOUND message text

    @Test
    public void deleteDeletedUser(){
        // create new user with requestUserId
        CreateUserRequest requestBody = new CreateUserRequest(getRandomEmail(), "John", "Smith");
        UserFull response = postRequest("/user/create", 200, requestBody).body().jsonPath().getObject("", UserFull.class);
        String idFromDeleteRequest =  response.getId();
        // Test
        // delete existing User by requestUserId
        deleteRequest("/user/" + idFromDeleteRequest, 200);
        // delete deleted user
        ErrorMessageResponse errorResponse = deleteRequest("/user/" + idFromDeleteRequest, 404)
                .body().jsonPath().getObject("", ErrorMessageResponse.class);
        assertEquals("RESOURCE_NOT_FOUND", errorResponse.getError());
    }

    // delete invalid user
    // 1. 400 status code
    // 2. "PARAMS_NOT_VALID"
    @Test
    public void deleteInvalidUser(){
        String invalidUserId = "hdghsghsdcd";
        ErrorMessageResponse errorResponse = deleteRequest("/user/" + invalidUserId, 400)
                .body().jsonPath().getObject("", ErrorMessageResponse.class);
        assertEquals("PARAMS_NOT_VALID", errorResponse.getError());
    }

    //POST method instead of DELETE
    //1. 404
    //2. PATH_NOT_FOUND
    @Test
    public void deleteWithWrongMethodPost(){
        CreateUserRequest requestBody = new CreateUserRequest(getRandomEmail(), "John", "Smith");
        UserFull response =
                postRequest("/user/create", 200, requestBody)
                        .body().jsonPath().getObject("", UserFull.class);
        String idFromDeleteRequest = response.getId();
        CreateUserRequest emptyRequestBody = CreateUserRequest.builder().build();
        ErrorMessageResponse errorResponse =
                postRequest("/user/" + idFromDeleteRequest, 404, emptyRequestBody)
                        .body().jsonPath().getObject("", ErrorMessageResponse.class);
        assertEquals("PATH_NOT_FOUND",errorResponse.getError());
    }

}
