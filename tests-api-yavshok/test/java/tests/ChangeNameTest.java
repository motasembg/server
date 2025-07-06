package tests;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import APIClient.AuthClient;
import resources.DataGenerator;
import resources.TokenGenerator;

import static org.junit.jupiter.api.Assertions.*;

public class ChangeNameTest {

    //успешно сменилось имя
    @Test
    public void changeNameSuccessfully(){
        String newName = "amIMarvel?";

        String email = DataGenerator.generateUniqueEmail();
        String password = "okaydocky90";
        int age = DataGenerator.oldCat();

        //regist
        AuthClient.register(email, password, age);
        //login
        String token = AuthClient.login(email, password)
                .jsonPath()
                .getString("token");
        Response response = AuthClient.changeUserName(token, newName);

        assertEquals(200, response.getStatusCode());
        assertEquals(newName, response.jsonPath().getString("user.name"), "Name updated!");

        Response profile = AuthClient.getUserProfile(token);
        assertEquals(newName, profile.jsonPath().getString("user.name"), "reflect new name");


    }

    //на пустую Value
    @Test
    public void emptyValueFail(){
        String email = DataGenerator.generateUniqueEmail();
        String password = "okaydocky90";
        int age = DataGenerator.adultCat();

        AuthClient.register(email, password, age);
        String token = AuthClient.login(email, password).jsonPath().getString("token");

        Response response = AuthClient.changeUserName(token, "");

        assertEquals(422, response.getStatusCode(), "Empty name should not be allowed");
    }

    //на очень длинную Value
    @Test
    public void withAlotOfLetter(){
        String longName = "a".repeat(100);

        String email = DataGenerator.generateUniqueEmail();
        String password = "okaydocky90";
        int age = DataGenerator.adultCat();

        AuthClient.register(email, password, age);
        String token = AuthClient.login(email, password).jsonPath().getString("token");

        Response response = AuthClient.changeUserName(token, longName);

        assertTrue(response.getStatusCode() == 422,
                "Doesn't allow us to make > 50 symbol!");
    }

    //Token Нет
    @Test
    public void withoutToken(){
        String newName = "amIHarryPotter?";

        Response response = AuthClient.changeUserName(null, newName);
        assertEquals(401, response.getStatusCode(),
                "Auth header required dude!");
    }

    //Token Файк
    @Test
    public void withFakeToken(){
        String newName = "nowAmHacker!";
        String fakeToken = TokenGenerator.generateNewToken();
        Response response = AuthClient.changeUserName(fakeToken, newName);

        assertEquals(401, response.getStatusCode(),
                "Invalid token should not be accepted");
    }
}

