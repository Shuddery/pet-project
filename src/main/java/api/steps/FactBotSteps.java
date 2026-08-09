package api.steps;

import api.clients.KafkaClient;
import api.models.CatFactModel;
import api.services.CatFactService;
import io.qameta.allure.Step;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.Duration;
import java.util.UUID;

public class FactBotSteps {

    private static final Logger log = LogManager.getLogger(FactBotSteps.class);

    private final KafkaClient kafkaClient = new KafkaClient();
    private final CatFactService catFactService = new CatFactService();

    private static final String REQUEST_TOPIC = "bot.requests";
    private static final String RESPONSE_TOPIC = "bot.responses";
    private static final Duration TIMEOUT = Duration.ofSeconds(5);

    @Step("User sends request via bot: '{command}'")
    public void sendUserRequest(String sessionId, String command) {
        log.info("Sending user command '{}' to topic '{}' with sessionId '{}'", command, REQUEST_TOPIC, sessionId);
        kafkaClient.sendMessage(REQUEST_TOPIC, sessionId, command);
    }

    @Step("Bot processes asynchronous request and sends fact to response topic")
    public CatFactModel processRequestAndSendResponse(String sessionId, String groupId) {
        String uniqueGroupId = groupId + "_backend_" + UUID.randomUUID();
        log.info("Starting backend emulation for sessionId '{}' using groupId '{}'", sessionId, uniqueGroupId);

        String request = kafkaClient.sendAndAwaitMessage(REQUEST_TOPIC, sessionId, "NEED_RANDOM_FACT", uniqueGroupId, TIMEOUT);

        if (!"NEED_RANDOM_FACT".equals(request)) {
            log.error("Received unexpected user command: '{}' for sessionId: '{}'", request, sessionId);
            throw new IllegalStateException("Received unexpected user command from Kafka: " + request);
        }

        log.info("Successfully intercepted expected command. Fetching random cat fact from API...");
        CatFactModel factModel;
        try {
            factModel = catFactService.getFact().as(CatFactModel.class);
        } catch (Exception e) {
            log.error("Failed to fetch or parse cat fact from REST API: ", e);
            throw new RuntimeException("API communication error during backend emulation", e);
        }

        if (factModel == null || factModel.fact() == null) {
            log.error("API returned an empty or invalid CatFactModel object");
            throw new AssertionError("Fetched cat fact model is null or contains no data");
        }

        log.info("Sending API response text back to Kafka response topic '{}'", RESPONSE_TOPIC);
        kafkaClient.sendMessage(RESPONSE_TOPIC, sessionId, factModel.fact());

        return factModel;
    }

    @Step("Verification: reading the final bot response for the user")
    public String getFinalBotResponse(String sessionId, String groupId) {
        String uniqueGroupId = groupId + "_client_" + UUID.randomUUID();
        log.info("Reading final bot response from topic '{}' for sessionId '{}' using groupId '{}'",
                RESPONSE_TOPIC, sessionId, uniqueGroupId);

        return kafkaClient.awaitMessageByKey(RESPONSE_TOPIC, sessionId, uniqueGroupId, TIMEOUT);
    }
}
