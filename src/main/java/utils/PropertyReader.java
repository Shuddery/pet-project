package utils;

import java.io.IOException;
import java.util.Properties;

public class PropertyReader {
    private static final Properties properties;
    static {
        properties = new Properties();
        try {
            properties.load(PropertyReader.class.getClassLoader().getResourceAsStream("config.properties"));
        } catch (IOException e) {
            throw new RuntimeException("Не удалось загрузить config.properties", e);
        }
    }

    public static String getSaucedemoUrl() { return properties.getProperty("saucedemoUrl"); }
    public static String getReqresUrl() { return properties.getProperty("reqresUrl"); }
    public static String getBrowser() { return properties.getProperty("browser"); }
    public static String getCatfactsUrl() { return properties.getProperty("catfactsUrl"); }

    public static String getLocalKafkaServers() {
        return properties.getProperty("kafka.local.bootstrap.servers");
    }

    public static Integer getTimeoutSeconds() {
        return Integer.valueOf(properties.getProperty("wait.timeout.seconds"));
    }
}