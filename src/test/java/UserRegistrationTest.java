import clients.UserClient;
import generators.UserGenerator;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import model.ErrorResponse;
import model.UserData;
import model.UserField;
import model.UserResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserRegistrationTest {

    private UserClient userClient;
    private String accessToken;

    @Before
    public void setUp() {
        userClient = new UserClient();
    }

    @After
    public void tearDown() {
        if (accessToken != null) {
            userClient.deleteUser(accessToken);
            accessToken = null;
        }
    }

    @Test
    @DisplayName("Создание уникального пользователя")
    public void createUniqueUserTest() {
        UserData userData = UserGenerator.getDefaultUserData();
        Response response = userClient.register(userData);
        response.then().statusCode(200);
        UserResponse userResponse = response.as(UserResponse.class);
        assertTrue(userResponse.isSuccess());
        assertEquals(userData.getEmail().toLowerCase(), userResponse.getUser().getEmail());
        assertEquals(userData.getName(), userResponse.getUser().getName());
        accessToken = userResponse.getAccessToken();
    }

    @Test
    @DisplayName("Создание пользователя, который уже зарегистрирован")
    public void createExistingUserTest() {
        UserData userData = UserGenerator.getDefaultUserData();
        Response firstResponse = userClient.register(userData);
        firstResponse.then().statusCode(200);
        UserResponse firstUserResponse = firstResponse.as(UserResponse.class);
        accessToken = firstUserResponse.getAccessToken();
        Response secondResponse = userClient.register(userData);
        secondResponse.then().statusCode(403);
        ErrorResponse errorResponse = secondResponse.as(ErrorResponse.class);
        assertFalse(errorResponse.isSuccess());
        assertEquals("User already exists", errorResponse.getMessage());
    }

    @Test
    @DisplayName("Создание пользователя без обязательного поля: email")
    public void createUserWithoutEmailTest() {
        UserData userData = UserGenerator.getUserDataWithMissingField(UserField.EMAIL);
        Response response = userClient.register(userData);
        response.then().statusCode(403);
        ErrorResponse errorResponse = response.as(ErrorResponse.class);
        assertFalse(errorResponse.isSuccess());
        assertEquals("Email, password and name are required fields", errorResponse.getMessage());
    }

    @Test
    @DisplayName("Создание пользователя без обязательного поля: name")
    public void createUserWithoutNameTest() {
        UserData userData = UserGenerator.getUserDataWithMissingField(UserField.NAME);
        Response response = userClient.register(userData);
        response.then().statusCode(403);
        ErrorResponse errorResponse = response.as(ErrorResponse.class);
        assertFalse(errorResponse.isSuccess());
        assertEquals("Email, password and name are required fields", errorResponse.getMessage());
    }

    @Test
    @DisplayName("Создание пользователя без обязательного поля: password")
    public void createUserWithoutPasswordTest() {
        UserData userData = UserGenerator.getUserDataWithMissingField(UserField.PASSWORD);
        Response response = userClient.register(userData);
        response.then().statusCode(403);
        ErrorResponse errorResponse = response.as(ErrorResponse.class);
        assertFalse(errorResponse.isSuccess());
        assertEquals("Email, password and name are required fields", errorResponse.getMessage());
    }
}
