import clients.UserClient;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import model.UserCredentials;
import generators.UserGenerator;
import model.UserData;
import model.UserResponse;
import model.ErrorResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserLoginTest {

    private UserClient userClient;
    private String accessToken;
    private UserCredentials validCredentials;

    @Before
    public void setUp() {
        userClient = new UserClient();
        UserData userData = UserGenerator.getDefaultUserData();
        Response response = userClient.register(userData);
        response.then().statusCode(200);
        UserResponse userResponse = response.as(UserResponse.class);
        accessToken = userResponse.getAccessToken();
        validCredentials = new UserCredentials(userData.getEmail(), userData.getPassword());
    }

    @After
    public void tearDown() {
        if (accessToken != null) {
            userClient.deleteUser(accessToken);
            accessToken = null;
        }
    }

    @Test
    @DisplayName("Логин под существующим пользователем")
    public void loginWithExistingUserTest() {
        Response response = userClient.login(validCredentials);
        response.then().statusCode(200);
        UserResponse userResponse = response.as(UserResponse.class);
        assertTrue(userResponse.isSuccess());
        assertEquals(validCredentials.getEmail().toLowerCase(), userResponse.getUser().getEmail());
    }

    @Test
    @DisplayName("Логин с неверным логином и паролем")
    public void loginWithInvalidCredentialsTest() {
        UserCredentials invalidCredentials = new UserCredentials("invalid@example.com", "wrongpassword");
        Response response = userClient.login(invalidCredentials);
        response.then().statusCode(401);
        ErrorResponse errorResponse = response.as(ErrorResponse.class);
        assertFalse(errorResponse.isSuccess());
        assertEquals("email or password are incorrect", errorResponse.getMessage());
    }
}
