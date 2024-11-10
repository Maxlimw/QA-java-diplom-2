import clients.OrderClient;
import clients.UserClient;
import generators.OrderGenerator;
import generators.UserGenerator;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import model.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class OrderCreationTest {

    private UserClient userClient;
    private OrderClient orderClient;
    private String accessToken;
    private UserData userData;

    @Before
    public void setUp() {
        userClient = new UserClient();
        orderClient = new OrderClient();
        userData = UserGenerator.getDefaultUserData();
        Response response = userClient.register(userData);
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
    @DisplayName("Создание заказа с авторизацией и ингредиентами")
    public void createOrderWithAuthAndIngredientsTest() {
        Order createOrder = OrderGenerator.getDefaultOrder();
        Response response = orderClient.createOrder(accessToken, createOrder);
        response.then().statusCode(200);
        OrderResponse orderResponse = response.as(OrderResponse.class);
        assertTrue(orderResponse.isSuccess());
        assertNotNull(orderResponse.getOrder());
        assertNotNull(orderResponse.getOrder().getNumber());
    }

    @Test
    @DisplayName("Создание заказа без авторизации и с ингредиентами")
    public void createOrderWithoutAuthTest() {
        Order createOrder = OrderGenerator.getDefaultOrder();
        Response response = orderClient.createOrderWithoutAuth(createOrder);
        response.then().statusCode(200);
        OrderResponse orderResponse = response.as(OrderResponse.class);
        assertTrue(orderResponse.isSuccess());
        assertNotNull(orderResponse.getOrder());
        assertNotNull(orderResponse.getOrder().getNumber());
    }

    @Test
    @DisplayName("Создание заказа без ингредиентов")
    public void createOrderWithoutIngredientsTest() {
        Order createOrder = OrderGenerator.getOrderWithoutIngredients();
        Response response = orderClient.createOrder(accessToken, createOrder);
        response.then().statusCode(400);
        ErrorResponse errorResponse = response.as(ErrorResponse.class);
        assertFalse(errorResponse.isSuccess());
        assertEquals("Ingredient ids must be provided", errorResponse.getMessage());
    }

    @Test
    @DisplayName("Создание заказа с неверным хешем ингредиентов")
    public void createOrderWithInvalidIngredientsTest() {
        Order createOrder = OrderGenerator.getOrderWithIncorrectHash();
        Response response = orderClient.createOrder(accessToken, createOrder);
        response.then().statusCode(500);
    }
}

