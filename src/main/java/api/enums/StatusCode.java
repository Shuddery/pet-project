package api.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StatusCode {

    OK(200),
    CREATED(201),
    NO_CONTENT(204),
    BAD_REQUEST(400),
    CONFLICT(409),
    NOT_FOUND(404),
    METHOD_NOT_ALLOWED(405);

    private final int code;
}