package cz.jeme.programu.stolujemeapi.db.user;

import cz.jeme.programu.stolujemeapi.canteen.Canteen;
import cz.jeme.programu.stolujemeapi.db.Entry;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.LocalDateTime;
import java.util.Objects;

public record User(
        int id,
        int registrationId,
        @NotNull String email,
        @NotNull String name,
        @NotNull LocalDateTime creationTime,
        @NotNull String passwordHash,
        @NotNull String passwordSalt,
        @NotNull Canteen canteen
) implements Entry {
    private User(final @NotNull Builder builder) {
        this(
                Objects.requireNonNull(builder.id, "id"),
                Objects.requireNonNull(builder.registrationId, "registrationId"),
                Objects.requireNonNull(builder.email, "email"),
                Objects.requireNonNull(builder.name, "name"),
                Objects.requireNonNull(builder.creationTime, "creationTime"),
                Objects.requireNonNull(builder.passwordHash, "passwordHash"),
                Objects.requireNonNull(builder.passwordSalt, "passwordSalt"),
                Objects.requireNonNull(builder.canteen, "canteen")
        );
    }

    @ApiStatus.Internal
    public static final class Builder implements Entry.Builder<Builder, User> {
        private @Nullable Integer id;
        private @Nullable Integer registrationId;
        private @Nullable String email;
        private @Nullable String name;
        private @Nullable LocalDateTime creationTime;
        private @Nullable String passwordHash;
        private @Nullable String passwordSalt;
        private @Nullable Canteen canteen;

        @Override
        public @NotNull Builder id(final int id) {
            this.id = id;
            return this;
        }

        public @NotNull Builder registrationId(final int registrationId) {
            this.registrationId = registrationId;
            return this;
        }

        public @NotNull Builder email(final @Nullable String email) {
            this.email = email;
            return this;
        }

        public @NotNull Builder name(final @Nullable String name) {
            this.name = name;
            return this;
        }

        public @NotNull Builder creationTime(final @Nullable LocalDateTime creationTime) {
            this.creationTime = creationTime;
            return this;
        }

        public @NotNull Builder passwordHash(final @Nullable String passwordHash) {
            this.passwordHash = passwordHash;
            return this;
        }

        public @NotNull Builder passwordSalt(final @Nullable String passwordSalt) {
            this.passwordSalt = passwordSalt;
            return this;
        }

        public @NotNull Builder canteen(final @Nullable Canteen canteen) {
            this.canteen = canteen;
            return this;
        }

        @Override
        public @NotNull User build() {
            return new User(this);
        }
    }
}