package generators;

import com.github.javafaker.Faker;
import model.UserCredentials;
import model.UserData;
import model.UserField;

public class UserGenerator {
    private static final Faker faker = new Faker();

    public static UserData getDefaultUserData() {
        String email = faker.internet().emailAddress();
        String password = faker.internet().password(8, 16);
        String name = faker.name().firstName();

        return new UserData(email, password, name);
    }

    public static UserCredentials getRandomCredentials() {
        String email = faker.internet().emailAddress();
        String password = faker.internet().password(8, 16);

        return new UserCredentials(email, password);
    }

    public static UserData getUserDataWithMissingField(UserField emptyField) {
        String email = faker.internet().emailAddress();
        String password = faker.internet().password(8, 16);
        String name = faker.name().firstName();

        switch (emptyField) {
            case EMAIL:
                email = null;
                break;
            case PASSWORD:
                password = null;
                break;
            case NAME:
                name = null;
                break;
        }

        return new UserData(email, password, name);
    }

    public static String getRandomEmail() {
        return faker.internet().emailAddress();
    }

    public static String getRandomPassword() {
        return faker.internet().password(8, 16);
    }

    public static String getRandomName() {
        return faker.name().firstName();
    }
}
