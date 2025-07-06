package tests;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import APIClient.AuthClient;
import resources.DataGenerator;

import static org.junit.jupiter.api.Assertions.*;

public class LoginTests {

    @Test()
    public void normalLoginWithToken(){
        String email = DataGenerator.generateUniqueEmail();
        String password = "okaydocky90";

        //we should make regist first
        AuthClient.register(email, password, 26);
        //now login
        Response response = AuthClient.login(email, password);

        assertEquals(200, response.getStatusCode(), "200 We Are In!");
        assertTrue(response.jsonPath().getString("token").length() > 10, "Token Back");
    }

    @Test
    public void withEmptyValues(){
        Response response = AuthClient.login("", "");

        assertEquals(422, response.getStatusCode(),
                "empty email and password!!");
    }

    @Test
    public void withNullValues(){
        Response response = AuthClient.login(null, "okaydocky90");
        assertEquals(422, response.getStatusCode(), "The Email is Null!!");

        response = AuthClient.login(DataGenerator.generateUniqueEmail(), null);
        assertEquals(422, response.getStatusCode(),
                "The pass is Null!!");
    }

    @Test
    public void withSQLInjection(){
        String email = "' OR '1'='1";
        String password = "' OR '1'='1";

        Response response = AuthClient.login(email, password);

        assertEquals(422, response.getStatusCode(),
                "no SQL injection, We Are Save 🔫 ");
    }

    @Test
    public void withInvalidPassword(){
        String email = DataGenerator.generateUniqueEmail();
        String password = "okaydocky90";

        //also we should make regist first
        AuthClient.register(email, password, 26);
        //now login)
        Response response = AuthClient.login(email, "12345678m");

        assertEquals(422, response.getStatusCode(),
                "Неправильный логин или пароль");
    }

    @Test
    public  void loginWithInvalidEmailFormat(){
        Response response = AuthClient.login("shokbok23yandex.ru", "okaydocky90");

        assertEquals(422, response.getStatusCode(),
                "email validation error");
    }
}
