package tests;

import org.junit.jupiter.api.Test;

import static tests.BaseTest.getRequest;

public class GetUserByIdTest {

    @Test
    public void getUserById(){
        getRequest("/user/60d0fe4f5311236168a109ca", 200);
    }

    // parse response to UserFull class in test getUserById()
    // check that id is not empty
}
