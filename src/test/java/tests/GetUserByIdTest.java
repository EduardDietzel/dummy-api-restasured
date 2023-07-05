package tests;

import dto.ErrorMessageResponse;
import dto.UserFull;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static tests.BaseTest.getRequest;

public class GetUserByIdTest {

    String requestUserId = "60d0fe4f5311236168a10a01";

    @Test
    public void getUserById(){
        // parse response to UserFull class in test getUserById()
        UserFull userFull =  getRequest("/user/" + requestUserId,
                200).body().jsonPath().getObject("", UserFull.class);
        // System.out.println(userFull.getId());
        // check that id is not empty
        assertFalse(userFull.getId().isEmpty());

        //check that id from endpoint equals to id from response
        assertEquals(requestUserId, userFull.getId());

        //check that picture value ends with ".jpg"
        assertTrue(userFull.getPicture().endsWith(".jpg"));

        //check that email ends with "@example.com"
        assertTrue(userFull.getEmail().endsWith("@example.com"));

        //check that actual year (1960) in dateOfBirth
        assertTrue(userFull.getDateOfBirth().startsWith("1960"));

        //verify that registerDate and updatedDate are the same
        assertEquals(userFull.getRegisterDate(), userFull.getUpdatedDate());

    }

    // create test for invalid id to check:
    // 1. Status code is 400 Bad request
    // 2. Error message text "PARAMS_NOT_VALID"

    String invalidRequestUserId = "jh34787djbh";

    @Test
    public void getUserByInvalidId(){
        String errorMessage = getRequest("/user/" + invalidRequestUserId,400).body().jsonPath().getString("error");
        assertEquals("PARAMS_NOT_VALID", errorMessage);
    }

    @Test
    public void getUserByInvalidIdDto(){
        ErrorMessageResponse response = getRequest("/user/" + invalidRequestUserId,400).body().jsonPath().getObject("", ErrorMessageResponse.class);
        assertEquals("BODY_NOT_VALID", response.getError());
    }

}
