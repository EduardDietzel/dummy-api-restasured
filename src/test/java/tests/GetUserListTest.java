package tests;

import dto.User;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static tests.BaseTest.getRequest;
import static tests.BaseTest.getRequestWithOneParam;

public class GetUserListTest {

    @Test
    public void getUserList() {
//        given()
//                .when()
//                .header("app-id", "6409b87d8be81d429a8276f3")
//                .log().all()
//                .get("\n" +
//                        "https://dummyapi.io/data/v1/user")
//                .then()
//                .log().all();
        Response actualResponse = getRequest("/user", 200);

        List<User> users = actualResponse.body().jsonPath().getList("data", User.class);
        // check that first user in list users has no empty firstname
        assertFalse(users.get(0).getFirstName().isEmpty());

        //check that all fields for all users in list users are not empty
        for (User user : users) {
            assertFalse(user.getId().isEmpty());
            assertFalse(user.getTitle().isEmpty());
            assertFalse(user.getFirstName().isEmpty());
            assertFalse(user.getLastName().isEmpty());
            assertFalse(user.getPicture().isEmpty());
        }

        //check that limit by default is 20
        int actualLimit = actualResponse.body().jsonPath().getInt("limit");
        assertEquals(20, actualLimit);

        assertEquals(20, users.size());

        //check that page number by default is 0
        int actualPage = actualResponse.body().jsonPath().getInt("page");
        assertEquals(0, actualPage);

    }

    @Test
    public void getUserListByPage(){
        int requestPageNumber = 2;
//        getRequestWithOneParam("/user", 200, "page", requestPageNumber);

        // check that page number from response matches with page number from request params
        int actualPageNumber = getRequestWithOneParam("/user", 200, "page", requestPageNumber).body().jsonPath().getInt("page");
        assertEquals(requestPageNumber, actualPageNumber);

        //Test for checking limit value with query param
        int requestLimit = 20;
        int actualLimit = getRequestWithOneParam("/user", 200, "page", requestPageNumber).body().jsonPath().getInt("limit");
        assertEquals(requestLimit, actualLimit);

    }

    @Test
    public void getUserListWithLimit(){
        int requestLimit = 10;
        int actualLimit =  getRequestWithOneParam("/user", 200, "limit",requestLimit).body().jsonPath().getInt("limit");

        //check that page number from response matches with page number from request param
        assertEquals(requestLimit, actualLimit);
    }

    //Add test for boundary values for param limit
    //[5-50]
    @Test
    public void getUserListWithInvalidLimitLessMin(){
        int requestLimit = 4;
        int actualLimit =  getRequestWithOneParam("/user", 200,
                "limit",requestLimit).body().jsonPath().getInt("limit");

        //check that page number from response matches with page number from request param
        assertEquals(5, actualLimit);
    }

    @Test
    public void getUserListWithInvalidLimitGreatMax(){
        int requestLimit = 51;
        int actualLimit =  getRequestWithOneParam("/user", 200,
                "limit",requestLimit).body().jsonPath().getInt("limit");

        //check that page number from response matches with page number from request param
        assertEquals(50, actualLimit);
    }

}
