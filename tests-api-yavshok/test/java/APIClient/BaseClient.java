package APIClient;

import io.restassured.RestAssured;

public class BaseClient {
    static String BASE_URL = "http://localhost:3000";
    static {
        RestAssured.baseURI = BASE_URL;
    }
}
