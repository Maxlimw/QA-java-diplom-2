package model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderResponse {
    private boolean success;
    private String name;
    private Order order;
    private String message;

    @Data
    public static class Order {
        private int number;
    }
}
