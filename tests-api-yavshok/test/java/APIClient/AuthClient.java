package APIClient;

import com.google.gson.JsonObject;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class AuthClient extends BaseClient {
    public static Response register(String email, String password, int age){
        //using json library
        JsonObject obj = new JsonObject();
        obj.addProperty("email", email);
        obj.addProperty("password", password);
        obj.addProperty("age", age);
        String payload = obj.toString();
        return given()
                // .log().all()
                .header("Content-Type", "application/json")
                .body(payload)
                .when()
                .post("/auth/register");
    }

    public static Response login(String email, String password){
        //using json library
        JsonObject obj = new JsonObject();
        obj.addProperty("email", email);
        obj.addProperty("password", password);
        String payload = obj.toString();
        return given()
                // .log().all()
                .header("Content-Type", "application/json")
                .body(payload)
                .when()
                .post("/auth/login");
    }

    public static Response checkShock(String email){
        //using json library
        JsonObject obj = new JsonObject();
        obj.addProperty("email", email);
        String payload = obj.toString();

        return given()
                //.log().all()
                .header("Content-Type", "application/json")
                .body(payload)
                .when()
                .post("/exist");
    }

    public static Response getUserProfile(String token){
        return given()
                // .log().all()
                .header("Authorization", "Bearer " + token)
                .when()
                .get("/user/me");
    }
    public static Response changeUserName(String token, String newName){
        //using json library
        JsonObject obj = new JsonObject();
        obj.addProperty("name", newName);
        String payload = obj.toString();
        return given()
//                .log().all()
                .header("Authorization", "Bearer " + token)
                .header("Content-Type", "application/json")
                .body(payload)
                .when()
                .patch("/user/name");

    }
}
