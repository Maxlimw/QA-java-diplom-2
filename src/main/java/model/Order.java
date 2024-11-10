package model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    private List<String> ingredients;
    private String _id;
    private String status;
    private int number;
    private String createdAt;
    private String updatedAt;

    public Order(List<String> ingredients) {
        this.ingredients = ingredients;
    }
}

