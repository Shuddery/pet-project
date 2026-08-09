package api.tests.kafka;

import api.BaseApiTest;
import api.models.CatFactModel;
import api.steps.FactBotSteps;
import io.qameta.allure.Description;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;

public class CatFactKafkaIntegrationTest extends BaseApiTest {

    private FactBotSteps botSteps;
    private String sessionId;
    private String uniqueGroupId;

    @BeforeEach
    void setUp() {
        botSteps = new FactBotSteps();
        sessionId = "session-" + UUID.randomUUID();
        uniqueGroupId = "group-" + UUID.randomUUID();
    }

    @Test
    @DisplayName("Successful processing of the chain: Request to Kafka -> REST API -> Response to Kafka")
    @Description("Verification that, upon receiving a request event, the backend correctly retrieves data from an external REST API and publishes it to the response topic")
    public void verifyRequestedFact() {
        CatFactModel expectedApiData = botSteps.processRequestAndSendResponse(sessionId, uniqueGroupId);

        String actualBotResponse = botSteps.getFinalBotResponse(sessionId, uniqueGroupId);

        assertThat("The user should receive the exact data that the backend retrieved via the API",
                actualBotResponse, Matchers.equalTo(expectedApiData.fact()));
    }
}
