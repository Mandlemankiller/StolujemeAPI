package cz.jeme.programu.stolujemeapi.rest;

import cz.jeme.programu.stolujemeapi.error.ApiErrorType;
import cz.jeme.programu.stolujemeapi.error.InvalidParamException;
import cz.jeme.programu.stolujemeapi.error.MissingParamException;
import jakarta.servlet.http.HttpServletRequest;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.http.HttpStatus;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.server.ResponseStatusException;

import java.util.function.Function;

public final class RequestUtils {
    private RequestUtils() {
        throw new AssertionError();
    }


    public static <T> @NotNull T require(final @Nullable T param, final @NotNull String name) {
        if (param == null) throw new MissingParamException(name);
        return param;
    }

    public static <T> @NotNull T validate(final @Nullable T param,
                                          final @NotNull String name,
                                          final @NotNull Function<@NotNull T, @NotNull ApiErrorType> validation) {
        require(param, name);
        ApiErrorType type = validation.apply(param);
        if (type == ApiErrorType.OK) return param;
        if (type == ApiErrorType.MISSING_PARAMETER)
            throw new IllegalArgumentException("Validation returned %s when parameter not missing!"
                    .formatted(ApiErrorType.MISSING_PARAMETER.name()));
        throw new InvalidParamException(
                name,
                type
        );
    }

    // TODO
    @NotNull
    public static String authorize() {
        RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
        if (attributes == null)
            throw new RuntimeException("No request attributes are bound to this thread!");

        HttpServletRequest servletRequest = ((ServletRequestAttributes) attributes).getRequest();
        String token = servletRequest.getHeader("Authorization");
        if (token == null)
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "Missing authorization (bearer token)!"
            );
        if (!token.startsWith("Bearer "))
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "Invalid bearer token prefix! Please use 'Bearer '!"
            );
        return token.substring(7);
    }
}