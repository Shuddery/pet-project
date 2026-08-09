package api.db;

import api.models.User;
import org.flywaydb.core.Flyway;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.mapper.reflect.ConstructorMapper;

import java.util.Optional;

public class DbManager {
    private final Jdbi jdbi;

    public DbManager(String url, String user, String password) {
        Flyway flyway = Flyway.configure()
                .dataSource(url, user, password)
                .load();
        flyway.migrate();

        this.jdbi = Jdbi.create(url, user, password);


    }


    public void insertUser(User user) {
        jdbi.useHandle(handle -> handle.createUpdate(
                        "INSERT INTO users (uuid, username, email) VALUES (:uuid, :username, :email)")
                .bind("uuid", user.uuid())
                .bind("username", user.username())
                .bind("email", user.email())
                .execute());
    }


    public <T> Optional<T> findEntityByUuid(Class<T> recordClass, String tableName, String uuid) {
        String query = String.format("SELECT * FROM %s WHERE uuid = :uuid", tableName);
        return jdbi.withHandle(handle -> handle.createQuery(query)
                .bind("uuid", uuid)
                .registerRowMapper(ConstructorMapper.factory(recordClass))
                .mapTo(recordClass)
                .findFirst());
    }

    public void deleteEntityByUuid(String tableName, String uuid) {
        String query = String.format("DELETE FROM %s WHERE uuid = :uuid", tableName);
        jdbi.useHandle(handle -> handle.createUpdate(query)
                .bind("uuid", uuid)
                .execute());
    }
}
