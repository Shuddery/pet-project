package api.models;

public record BreedModel(
    String breed,
    String country,
    String origin,
    String coat,
    String pattern
) {}