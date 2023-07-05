package tests;

import dto.CreateUserRequest;
import dto.UserFull;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static tests.BaseTest.*;

public class UpdateUserTest {
    @Test
    public void successUpdateLastName(){
        //Change last name
        //Assert that new last name is the same with last name from request
        CreateUserRequest requestBodyCreate = new CreateUserRequest(getRandomEmail(), "John", "Smith");
        UserFull response =
                postRequest("/user/create", 200, requestBodyCreate)
                        .body().jsonPath().getObject("", UserFull.class);
        String userId = response.getId();
        //Change last name
        CreateUserRequest requestBodyUpdate = CreateUserRequest.builder().lastName("New last name").build();
        UserFull updatedUserResponse = putRequest("/user/" + userId, 200, requestBodyUpdate)
                .body().jsonPath().getObject("", UserFull.class);
        //Assert that new last name is the same with last name from request
        assertEquals(requestBodyUpdate.getLastName(), updatedUserResponse.getLastName());
    }

    //Try to update email
    @Test
    public void updateEmail(){
        CreateUserRequest requestBodyCreate =
                new CreateUserRequest(getRandomEmail(), "John", "Smith");
        UserFull response =
                postRequest("/user/create", 200, requestBodyCreate)
                        .body().jsonPath().getObject("", UserFull.class);
        String userId = response.getId();
        //Try to update email
        CreateUserRequest requestBodyUpdate = CreateUserRequest.builder()
                .email("New email")
                .build();
        UserFull updatedUserResponse = putRequest("/user/" + userId, 200, requestBodyUpdate)
                .body().jsonPath().getObject("", UserFull.class);
        //Assert that new last name is the same with last name from request
        assertNotEquals(requestBodyUpdate.getEmail(), updatedUserResponse.getEmail());
        assertEquals(requestBodyCreate.getEmail(), updatedUserResponse.getEmail());
    }
}
