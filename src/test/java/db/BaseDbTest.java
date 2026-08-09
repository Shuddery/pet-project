package db;

import api.db.DbManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public abstract class BaseDbTest {

    protected DbManager dbManager;
    protected String uuid;
    private List<Runnable> cleanupTasks;

    @BeforeEach
    void setUpDb() {
        String env = System.getProperty("env", "local");
        String url = "ci".equalsIgnoreCase(env)
                ? "jdbc:mysql://172.25.0.40:3306/pet_qa_db"
                : "jdbc:mysql://localhost:3306/pet_qa_db";

        this.dbManager = new DbManager(url, "qa_user", "qa_password");
        this.uuid = UUID.randomUUID().toString();
        this.cleanupTasks = new ArrayList<>();
    }

    protected void registerCleanup(Runnable cleanupTask) {
        this.cleanupTasks.add(cleanupTask);
    }

    @AfterEach
    void cleanUpData() {
        for (Runnable task : cleanupTasks) {
            try {
                task.run();
            } catch (Exception e) {
                System.err.println("Clean up error: " + e.getMessage());
            }
        }
    }
}
