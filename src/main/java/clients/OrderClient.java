package clients;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import model.Order;

import static io.restassured.RestAssured.given;

public class OrderClient extends BaseClient{
    private static final String ORDER_PATH = "api/orders";

    @Step("Создание заказа")
    public Response createOrder(String accessToken, Order orderRequest) {
        return given()
                .spec(getBaseSpec())
                .header("Authorization", accessToken)
                .body(orderRequest)
                .when()
                .post(ORDER_PATH);
    }

    @Step("Создание заказа без авторизации")
    public Response createOrderWithoutAuth(Order orderRequest) {
        return given()
                .spec(getBaseSpec())
                .body(orderRequest)
                .when()
                .post(ORDER_PATH);
    }

    @Step("Получение заказов пользователя с авторизацией")
    public Response getUserOrders(String accessToken) {
        return given()
                .spec(getBaseSpec())
                .header("Authorization", accessToken)
                .when()
                .get(ORDER_PATH);
    }

    @Step("Получение заказов пользователя без авторизации")
    public Response getUserOrdersWithoutAuth() {
        return given()
                .spec(getBaseSpec())
                .when()
                .get(ORDER_PATH);
    }
}

