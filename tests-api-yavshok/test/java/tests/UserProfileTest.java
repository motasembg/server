package tests;

import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import resources.DataGenerator;
import APIClient.AuthClient;
import static org.junit.jupiter.api.Assertions.*;
public class UserProfileTest {

    @Test
    public void WithInvalidToken() {
        String fakeToken = "3857892359825#$#@$@#$#@$%235hfibdifbd";

        Response response = AuthClient.getUserProfile(fakeToken);

        assertEquals(401, response.getStatusCode(),
                "Fake TOKEN!!");
    }

    @Test
    public void WithNullToken() {
        Response response = AuthClient.getUserProfile(null);

        assertEquals(401, response.getStatusCode(),
                "there is no token!!");
    }
}
