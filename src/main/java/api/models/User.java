package api.models;

public record User(
        Integer id,
        String uuid,
        String username,
        String email
) {}