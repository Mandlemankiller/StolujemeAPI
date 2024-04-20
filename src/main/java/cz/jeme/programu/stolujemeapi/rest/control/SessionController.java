package cz.jeme.programu.stolujemeapi.rest.control;

import com.fasterxml.jackson.annotation.JsonProperty;
import cz.jeme.programu.stolujemeapi.db.CryptoUtils;
import cz.jeme.programu.stolujemeapi.db.session.Session;
import cz.jeme.programu.stolujemeapi.db.session.SessionDao;
import cz.jeme.programu.stolujemeapi.db.session.SessionSkeleton;
import cz.jeme.programu.stolujemeapi.db.user.User;
import cz.jeme.programu.stolujemeapi.db.user.UserDao;
import cz.jeme.programu.stolujemeapi.error.ApiErrorType;
import cz.jeme.programu.stolujemeapi.error.InvalidParamException;
import cz.jeme.programu.stolujemeapi.rest.ApiUtils;
import cz.jeme.programu.stolujemeapi.rest.Request;
import cz.jeme.programu.stolujemeapi.rest.Response;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.spec.InvalidKeySpecException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.function.Supplier;

@RestController
public final class SessionController {
    public static final @NotNull String CREDENTIALS_PLACEHOLDER = "#credentials";
    public static final @NotNull Duration SESSION_DURATION = Duration.ofDays(30);

    public static void throwIncorrectCredentials() {
        throw new InvalidParamException(SessionController.CREDENTIALS_PLACEHOLDER, ApiErrorType.INVALID_CREDENTIALS);
    }

    public static @NotNull Supplier<@NotNull InvalidParamException> supplyIncorrectCredentials() {
        return () -> new InvalidParamException(SessionController.CREDENTIALS_PLACEHOLDER, ApiErrorType.INVALID_CREDENTIALS);
    }

    private SessionController() {
    }

    @PostMapping("/login")
    @ResponseBody
    private @NotNull Response login(final @NotNull @RequestBody LoginRequest request) {
        final String email = ApiUtils.require(
                request.email(),
                "email"
        );

        final String password = ApiUtils.require(
                request.password(),
                "password"
        );

        final User user = UserDao.INSTANCE.userByEmail(email)
                .orElseThrow(SessionController.supplyIncorrectCredentials());

        try {
            if (!CryptoUtils.validate(password, user.passwordHash(), user.passwordSalt()))
                SessionController.throwIncorrectCredentials();
        } catch (final InvalidKeySpecException e) {
            throw new RuntimeException("Could not validate password!", e);
        }

        final String token = CryptoUtils.genToken();

        final Session session = SessionDao.INSTANCE.insertSession(
                new SessionSkeleton.Builder()
                        .userId(user.id())
                        .duration(SessionController.SESSION_DURATION)
                        .token(token)
                        .build()
        );

        return new LoginResponse(new SessionData(session));
    }

    public record LoginRequest(
            @JsonProperty("email")
            @Nullable String email,

            @JsonProperty("password")
            @Nullable String password
    ) implements Request {
    }

    public record LoginResponse(
            @JsonProperty("session")
            @NotNull SessionData sessionData
    ) implements Response {
    }

    @PostMapping("/logout")
    @ResponseBody
    private @NotNull Response logout() {
        final Session session = ApiUtils.authenticate();
        if (!SessionDao.INSTANCE.endSession(session.id()))
            throw new RuntimeException("Session could not be ended!");
        return ApiUtils.emptyResponse();
    }

    @PostMapping("/auth")
    @ResponseBody
    private @NotNull Response auth() { // test auth
        final Session session = ApiUtils.authenticate();
        final User user = UserDao.INSTANCE.userById(session.userId())
                .orElseThrow(() -> new RuntimeException("Session user id does not correspond to any users!"));
        return new AuthResponse(
                new UserController.UserData(user),
                new SessionData(session)
        );
    }

    public record AuthResponse(
            @JsonProperty("user")
            @NotNull UserController.UserData userData,
            @JsonProperty("session")
            @NotNull SessionData sessionData
    ) implements Response {
    }

    public record SessionData(
            @JsonProperty("token")
            @NotNull String token,
            @JsonProperty("creationTime")
            @NotNull LocalDateTime creationTime,
            @JsonProperty("expirationTime")
            @NotNull LocalDateTime expirationTime
    ) {
        public SessionData(final @NotNull Session session) {
            this(session.token(), session.creationTime(), session.expirationTime());
        }
    }
}