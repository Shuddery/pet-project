package api.settings;

import utils.PropertyReader;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

public class KafkaConfig {

    private static final String BOOTSTRAP_SERVERS = System.getenv("CI") != null ?
            "kafka:29092" : PropertyReader.getLocalKafkaServers();

    private static Properties getCommonProperties() {
        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);

        properties.put(ProducerConfig.MAX_BLOCK_MS_CONFIG, "5000");
        properties.put(ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG, "5000");

        return properties;
    }

    public static Properties getProducerProperties() {
        Properties properties = getCommonProperties();

        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        properties.put(ProducerConfig.ACKS_CONFIG, "1");

        return properties;
    }

    public static Properties getConsumerProperties(String groupId) {
        Properties properties = getCommonProperties();

        properties.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        properties.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "10000");

        return properties;
    }
}
