package clients;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import model.UserCredentials;
import model.UserData;

import static io.restassured.RestAssured.given;

public class UserClient extends BaseClient{
    private final static String REGISTER_PATH = "api/auth/register";
    private final static String LOGIN_PATH = "api/auth/login";
    private final static String USER_PATH = "api/auth/user";

    @Step("Создание пользователя")
    public Response register(UserData userData) {
        return given()
                .spec(getBaseSpec())
                .body(userData)
                .when()
                .post(REGISTER_PATH);
    }

    @Step("Логин пользователя")
    public Response login(UserCredentials credentials) {
        return given()
                .spec(getBaseSpec())
                .body(credentials)
                .when()
                .post(LOGIN_PATH);
    }

    @Step("Изменение данных пользователя c авторизацией")
    public Response updateUser(String accessToken, UserData userData) {
        return given()
                .spec(getBaseSpec())
                .header("Authorization", accessToken)
                .body(userData)
                .when()
                .patch(USER_PATH);
    }
    @Step("Изменение данных пользователя без авторизации")
    public Response updateUserWithoutAuth(UserData updatedUserData) {
        return given()
                .spec(getBaseSpec())
                .body(updatedUserData)
                .when()
                .patch(USER_PATH);
    }

    @Step("Удаление пользователя")
    public Response deleteUser(String accessToken) {
        return given()
                .spec(getBaseSpec())
                .header("Authorization", accessToken)
                .when()
                .delete(USER_PATH);
    }
}
