package generators;
import model.Order;

import java.util.List;

public class OrderGenerator {

    final static String DEFAULT_INGREDIENT_HASH = "61c0c5a71d1f82001bdaaa6d";
    final static String INCORRECT_INGREDIENT_HASH = "abc";

    public static Order getDefaultOrder() {
        return new Order(List.of(DEFAULT_INGREDIENT_HASH));
    }

    public static Order getOrderWithoutIngredients() {
        return new Order(List.of());
    }

    public static Order getOrderWithIncorrectHash() {
        return new Order(List.of(INCORRECT_INGREDIENT_HASH));
    }
}
