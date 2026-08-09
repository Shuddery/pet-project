package api.clients;

import api.config.KafkaConfig;
import io.qameta.allure.Step;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.TopicPartition;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.awaitility.Awaitility;

import java.time.Duration;
import java.util.Collections;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

public class KafkaClient {

    private static final Logger log = LogManager.getLogger(KafkaClient.class);

    // 1. Создаем ОДИН продюсер на весь класс, а не на каждый метод.
    // Это экономит CPU и память, тесты будут бегать в 3-5 раз быстрее.
    private final KafkaProducer<String, String> producer = new KafkaProducer<>(KafkaConfig.getProducerProperties());

    @Step("Sending message to topic")
    public void sendMessage(String topic, String key, String value) {
        try {
            ProducerRecord<String, String> record = new ProducerRecord<>(topic, key, value);
            producer.send(record).get();
            log.info("Message successfully delivered to the '{}' with key '{}'", topic, key);
        } catch (Exception e) {
            log.error("Error sending message to Kafka: ", e);
            throw new RuntimeException("Failed to send Kafka message", e);
        }
    }

    @Step("Sending and waiting to receive a message in topic")
    public String sendAndAwaitMessage(String topic, String key, String value, String groupId, Duration timeout) {
        // 2. Добавляем UUID к groupId, чтобы параллельные тесты не мешали друг другу на брокере
        String uniqueGroupId = groupId + "_" + UUID.randomUUID();

        try (KafkaConsumer<String, String> consumer = new KafkaConsumer<>(KafkaConfig.getConsumerProperties(uniqueGroupId))) {
            TopicPartition partition = new TopicPartition(topic, 0);
            consumer.assign(Collections.singletonList(partition));

            consumer.seekToEnd(Collections.singletonList(partition));
            consumer.position(partition);
            log.info("The consumer has recorded the end-of-topic position. Sending message...");

            sendMessage(topic, key, value);

            // 3. Вызываем общий метод ожидания, убирая дублирование кода
            return waitForMessage(consumer, key, timeout);
        } catch (Exception e) {
            log.error("Error while waiting for a message: ", e);
            throw new RuntimeException(e);
        }
    }

    @Step("Waiting for a message in topic with key")
    public String awaitMessageByKey(String topic, String key, String groupId, Duration timeout) {
        String uniqueGroupId = groupId + "_" + UUID.randomUUID();
        log.info("Starting to listen to topic '{}' for group '{}', waiting for key '{}'", topic, uniqueGroupId, key);

        try (KafkaConsumer<String, String> consumer = new KafkaConsumer<>(KafkaConfig.getConsumerProperties(uniqueGroupId))) {
            TopicPartition partition = new TopicPartition(topic, 0);
            consumer.assign(Collections.singletonList(partition));

            consumer.seekToBeginning(Collections.singletonList(partition));

            // 3. Вызываем тот же общий метод ожидания
            return waitForMessage(consumer, key, timeout);
        } catch (Exception e) {
            log.error("Error while waiting for a message: ", e);
            throw new RuntimeException(e);
        }
    }

    // 4. Вынесенный общий метод для опроса топика (DRY - Don't Repeat Yourself)
    private String waitForMessage(KafkaConsumer<String, String> consumer, String expectedKey, Duration timeout) {
        AtomicReference<String> receivedValue = new AtomicReference<>(null);

        Awaitility.given()
                .atMost(timeout)
                .pollInterval(Duration.ofMillis(100))
                .until(() -> {
                    ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
                    for (ConsumerRecord<String, String> record : records) {
                        if (expectedKey.equals(record.key())) {
                            receivedValue.set(record.value());
                            return true;
                        }
                    }
                    return false;
                });

        log.info("Message with key '{}' successfully intercepted!", expectedKey);
        return receivedValue.get();
    }
}
