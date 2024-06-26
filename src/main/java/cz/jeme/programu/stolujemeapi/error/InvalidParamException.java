package cz.jeme.programu.stolujemeapi.error;

import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;

public class InvalidParamException extends ApiException {
    public static final @NotNull String INVALID_PARAM_MESSAGE = "Parameter '%s' is invalid: %s";
    private final @NotNull String paramName;

    public InvalidParamException(final @NotNull String paramName, final @NotNull ApiErrorType type) {
        super(
                HttpStatus.UNPROCESSABLE_ENTITY,
                type,
                InvalidParamException.INVALID_PARAM_MESSAGE.formatted(paramName, type.getMessage())
        );
        this.paramName = paramName;
    }

    public @NotNull String getParamName() {
        return paramName;
    }
}