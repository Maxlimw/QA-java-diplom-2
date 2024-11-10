import clients.UserClient;
import generators.UserGenerator;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import model.ErrorResponse;
import model.UserData;
import model.UserResponse;
import model.UserCredentials;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserUpdateTest {

    private UserClient userClient;
    private String accessToken;
    private UserData originalUserData;

    @Before
    public void setUp() {
        userClient = new UserClient();
        originalUserData = UserGenerator.getDefaultUserData();
        Response response = userClient.register(originalUserData);
        response.then().statusCode(200);

        UserResponse userResponse = response.as(UserResponse.class);
        accessToken = userResponse.getAccessToken();
    }

    @After
    public void tearDown() {
        if (accessToken != null) {
            userClient.deleteUser(accessToken);
            accessToken = null;
        }
    }

    @Test
    @DisplayName("Изменение данных пользователя с авторизацией")
    public void updateUserWithAuthorizationTest() {
        String newEmail = UserGenerator.getRandomEmail();
        String newPassword = UserGenerator.getRandomPassword();
        String newName = UserGenerator.getRandomName();
        UserData updatedUserData = new UserData(newEmail, newPassword, newName);
        Response response = userClient.updateUser(accessToken, updatedUserData);
        response.then().statusCode(200);
        UserResponse userResponse = response.as(UserResponse.class);
        assertTrue(userResponse.isSuccess());
        assertEquals(newEmail.toLowerCase(), userResponse.getUser().getEmail());
        assertEquals(newName, userResponse.getUser().getName());
        UserCredentials newCredentials = new UserCredentials(newEmail, newPassword);
        Response loginResponse = userClient.login(newCredentials);
        loginResponse.then().statusCode(200);
    }

    @Test
    @DisplayName("Изменение данных пользователя без авторизации")
    public void updateUserWithoutAuthorizationTest() {
        String newEmail = UserGenerator.getRandomEmail();
        String newPassword = UserGenerator.getRandomPassword();
        String newName = UserGenerator.getRandomName();
        UserData updatedUserData = new UserData(newEmail, newPassword, newName);
        Response response = userClient.updateUserWithoutAuth(updatedUserData);
        response.then().statusCode(401);
        ErrorResponse errorResponse = response.as(ErrorResponse.class);
        assertFalse(errorResponse.isSuccess());
        assertEquals("You should be authorised", errorResponse.getMessage());
    }
}