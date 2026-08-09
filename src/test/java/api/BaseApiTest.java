package api;

import io.restassured.RestAssured;
import io.restassured.config.ConnectionConfig;
import org.junit.jupiter.api.BeforeAll;

import java.util.concurrent.TimeUnit;

public class BaseApiTest {
    @BeforeAll
    static void init() {
        RestAssured.useRelaxedHTTPSValidation();
        ConnectionConfig.CloseIdleConnectionConfig closeIdleConnectionConfig = new ConnectionConfig.CloseIdleConnectionConfig(600L, TimeUnit.SECONDS);
        RestAssured.config().connectionConfig(ConnectionConfig.connectionConfig().closeIdleConnectionsAfterEachResponseAfter(closeIdleConnectionConfig));
    }
}