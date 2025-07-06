package tests;

import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import APIClient.AuthClient;
import resources.DataGenerator;

import static org.junit.jupiter.api.Assertions.*;

public class RegisterTests {

    @Test
    public void successfulRegistration(){
        String email = DataGenerator.generateUniqueEmail();
        String password = "okaydocky90";
        int age = DataGenerator.youngCat();

        Response response = AuthClient.register(email, password, age);
        assertEquals(200, response.getStatusCode(), "Created User!");
    }

    @Test
    public void WithEmptyValue(){
        Response response = AuthClient.register("", "", 0);

        assertEquals(422, response.getStatusCode(), "fail because empty values");
    }

    @Test
    public void registrationWithNullValues(){
        Response response = AuthClient.register(null, null, 0);

        assertEquals(422, response.getStatusCode(), "Null Values!!!");
    }

    @Test
    public void WithSQLInjectionEmail(){
        Response response = AuthClient.register(
                "' OR '1'='1@sql.com",
                "okaydocky90",
                DataGenerator.youngCat());
        assertEquals(422, response.getStatusCode(),
                "SQL injection email 😁");
    }

    @Test
    public void WithWhitespace(){
        String email = "  MarvelSpideyy@yandex.ru ";
        Response response = AuthClient.register(
                email,
                "okaydocky90",
                DataGenerator.youngCat());

        assertEquals(422, response.getStatusCode(),
                "email with spaces!!");
    }

    @Test
    public void registrationWithInvalidEmail(){
        Response response = AuthClient.register("fakeemail.ru", "Abogsysa123", 26);
        assertEquals(422, response.getStatusCode(), "Expected 422 for invalid email");
        assertTrue(response.getBody().asString().contains("email"), "Should mention email error");
    }

    @Test
    public void registrationWithShortPassword(){
        Response response = AuthClient.register(
                DataGenerator.generateUniqueEmail(),
                "123",
                DataGenerator.adultCat());
        assertEquals(422, response.getStatusCode(),
                "SORRY BUT short password :))");
    }

    @Test
    public void registrationWithDuplicateEmail(){
        String email = DataGenerator.generateUniqueEmail();
        //first regis!!
        AuthClient.register(email,
                "okaydocky90",
                DataGenerator.adultCat());

        //second regis with same email!
        Response response = AuthClient.register(email,
                "okaydocky90",
                DataGenerator.adultCat());
        assertEquals(422, response.getStatusCode(),
                "repeating email!! CHANGE ITTT!");


    }

}
