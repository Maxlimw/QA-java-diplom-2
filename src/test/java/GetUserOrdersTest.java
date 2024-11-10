import clients.OrderClient;
import clients.UserClient;
import generators.OrderGenerator;
import generators.UserGenerator;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import model.ErrorResponse;
import model.UserResponse;
import model.UserOrdersResponse;
import model.Order;
import model.UserData;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class GetUserOrdersTest {

    private UserClient userClient;
    private OrderClient orderClient;
    private String accessToken; // Для авторизации
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
        Order createOrder = OrderGenerator.getDefaultOrder();
        orderClient.createOrder(accessToken, createOrder);
        orderClient.createOrder(accessToken, createOrder);
    }

    @After
    public void tearDown() {
        if (accessToken != null) {
            userClient.deleteUser(accessToken);
            accessToken = null;
        }
    }

    @Test
    @DisplayName("Получение заказов пользователя с авторизацией")
    public void getUserOrdersWithAuthTest() {
        Response response = orderClient.getUserOrders(accessToken);
        response.then().statusCode(200);
        UserOrdersResponse ordersResponse = response.as(UserOrdersResponse.class);
        assertTrue(ordersResponse.isSuccess());
        assertNotNull(ordersResponse.getOrders());
        assertFalse(ordersResponse.getOrders().isEmpty());
        for (Order order : ordersResponse.getOrders()) {
            assertNotNull(order.get_id());
            assertNotNull(order.getIngredients());
            assertNotNull(order.getStatus());
            assertNotNull(order.getNumber());
            assertNotNull(order.getCreatedAt());
            assertNotNull(order.getUpdatedAt());
        }
    }

    @Test
    @DisplayName("Получение заказов пользователя без авторизации")
    public void getUserOrdersWithoutAuthTest() {
        Response response = orderClient.getUserOrdersWithoutAuth();
        response.then().statusCode(401);
        ErrorResponse errorResponse = response.as(ErrorResponse.class);
        assertFalse(errorResponse.isSuccess());
        assertEquals("You should be authorised", errorResponse.getMessage());
    }
}
