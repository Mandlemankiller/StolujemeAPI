package cz.jeme.programu.stolujemeapi.db.user;

import cz.jeme.programu.stolujemeapi.canteen.Canteen;
import cz.jeme.programu.stolujemeapi.db.Skeleton;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public record UserSkeleton(
        int verificationId,
        @NotNull String email,
        @NotNull String name,
        @NotNull String passwordHash,
        @NotNull String passwordSalt,
        @NotNull Canteen canteen
) implements Skeleton {
    private UserSkeleton(final @NotNull Builder builder) {
        this(
                Objects.requireNonNull(builder.verificationId, "registrationId"),
                Objects.requireNonNull(builder.email, "email"),
                Objects.requireNonNull(builder.name, "name"),
                Objects.requireNonNull(builder.passwordHash, "passwordHash"),
                Objects.requireNonNull(builder.passwordSalt, "passwordSalt"),
                Objects.requireNonNull(builder.canteen, "canteen")
        );
    }

    public static final class Builder implements Skeleton.Builder<Builder, UserSkeleton> {
        private @Nullable Integer verificationId;
        private @Nullable String email;
        private @Nullable String name;
        private @Nullable String passwordHash;
        private @Nullable String passwordSalt;
        private @Nullable Canteen canteen;

        public @NotNull Builder verificationId(final int verificationId) {
            this.verificationId = verificationId;
            return this;
        }

        public @NotNull Builder email(final @NotNull String email) {
            this.email = email;
            return this;
        }

        public @NotNull Builder name(final @NotNull String name) {
            this.name = name;
            return this;
        }

        public @NotNull Builder passwordHash(final @NotNull String passwordHash) {
            this.passwordHash = passwordHash;
            return this;
        }

        public @NotNull Builder passwordSalt(final @NotNull String passwordSalt) {
            this.passwordSalt = passwordSalt;
            return this;
        }

        public @NotNull Builder canteen(final @NotNull Canteen canteen) {
            this.canteen = canteen;
            return this;
        }

        @Override
        public @NotNull UserSkeleton build() {
            return new UserSkeleton(this);
        }
    }
}