package tests;

import dto.CreateUserRequest;
import dto.ErrorMessageResponse;
import dto.UnsuccessfulUserCreateResponse;
import dto.UserFull;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static tests.BaseTest.*;

public class CreateUserTest {

//    Random random = new Random();

    @Test
    public void successCreateUser(){
        CreateUserRequest requestBody = new CreateUserRequest(getRandomEmail(), "John", "Smith");
        UserFull response = postRequest("https://dummyapi.io/data/v1/user/create", 200, requestBody).body().jsonPath().getObject("", UserFull.class);

        //email from request and email from response are the same
        assertEquals(requestBody.getEmail(), response.getEmail());

        //first name and last name
        assertEquals(requestBody.getFirstName(), response.getFirstName());
        assertEquals(requestBody.getLastName(), response.getLastName());

    }

    @Test
    public void unsuccessfulCreateUserRepeatedEmail(){
        //1. Status code is 400
        //2. "Email already used" error message is diplayed

        CreateUserRequest requestBody = new CreateUserRequest(getRandomEmail(), "John", "Smith");
        postRequest("https://dummyapi.io/data/v1/user/create", 200, requestBody);

        UnsuccessfulUserCreateResponse response =
                postRequest("https://dummyapi.io/data/v1/user/create", 400, requestBody)
                        .body().jsonPath().getObject("data", UnsuccessfulUserCreateResponse.class);
        System.out.println(response.getEmail());

        assertEquals("Email already used", response.getEmail());
    }

    //test without email which check:
    //1. Status code is 400
    //2.  "Path `email` is required." error message is displayed
    @Test
    public void withoutEmail(){
        //CreateUserRequest requestBody = new CreateUserRequest("", "John", "Smith");
        CreateUserRequest requestBody = CreateUserRequest.builder()
                .firstName("John")
                .lastName("Smith")
                .build();
        UnsuccessfulUserCreateResponse response =
                postRequest("https://dummyapi.io/data/v1/user/create", 400, requestBody)
                        .body().jsonPath().getObject("data", UnsuccessfulUserCreateResponse.class);
        assertEquals("Path `email` is required.",  response.getEmail());
    }

    //Add tests for all negative cases for creating user
    // -----

    @Test
    public void createWithGetMethod(){
        CreateUserRequest requestBody = new CreateUserRequest(getRandomEmail(), "John", "Smith");
//        ErrorMessageResponse response = getRequestWithBody("/user/create", 400, requestBody)
//                .body().jsonPath().getObject("", ErrorMessageResponse.class);
//        assertEquals("PARAMS_NOT_VALID", response.getError());

        String responseErrorMessage = getRequestWithBody("/user/create", 400, requestBody).body().jsonPath()
                .getString("error");
        assertEquals("PARAMS_NOT_VALID", responseErrorMessage);
    }
}
