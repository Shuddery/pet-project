package db.tests;

import api.models.User;
import db.BaseDbTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DatabaseUserTest extends BaseDbTest {

    private User expectedUser;

    @BeforeEach
    void prepareTestData() {
        expectedUser = new User(null, uuid, "Max", "max@gmail.com");
        dbManager.insertUser(expectedUser);

        // РЕГИСТРИРУЕМ ОЧИСТКУ: базовый класс сам вызовет этот метод после теста!
        registerCleanup(() -> dbManager.deleteEntityByUuid("users", uuid));

        // Если бы тест создавал еще и заказ, вы бы просто добавили вторую строчку:
        // registerCleanup(() -> dbManager.deleteOrderById(someOrderId));
    }

    @Test
    void isUserCreated() {
        User actualUser = dbManager.findEntityByUuid(User.class, "users", uuid)
                .orElseThrow(() -> new AssertionError("User not found"));

        assertEquals(expectedUser.username(), actualUser.username());
    }
}
