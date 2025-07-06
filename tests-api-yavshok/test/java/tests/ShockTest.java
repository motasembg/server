package tests;

import APIClient.AuthClient;
import io.restassured.response.Response;
import org.apache.http.auth.AUTH;
import org.junit.jupiter.api.Test;
import resources.DataGenerator;

import static org.junit.jupiter.api.Assertions.*;
public class ShockTest {
    @Test
    public void userAlreadyRegistered(){
        String email = DataGenerator.generateUniqueEmail();
        String password = "okaydocky90";
        int age = DataGenerator.adultCat();

        //first regis!!
        AuthClient.register(email,password,age);
        Response response = AuthClient.checkShock(email);

        assertEquals(200, response.getStatusCode());
        assertTrue(response.jsonPath().getBoolean("exist"), "User in SHOK");
    }

    @Test
    public void userNotRegistered(){
        String email = DataGenerator.generateUniqueEmail();

        Response response = AuthClient.checkShock(email);

        //The Server is Alive
        assertEquals(200, response.getStatusCode());

        //False means that NotExist!
        assertFalse(response.jsonPath().getBoolean("exist"), "User is NOT in SHOK");
    }
}
