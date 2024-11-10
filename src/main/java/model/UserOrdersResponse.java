package model;
import lombok.Data;
import java.util.List;

@Data
public class UserOrdersResponse {
    private boolean success;
    private List<Order> orders;
}

