package cz.jeme.programu.stolujemeapi.db.user;

import cz.jeme.programu.stolujemeapi.Canteen;
import cz.jeme.programu.stolujemeapi.db.Skeleton;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public final class UserSkeleton implements Skeleton {
    private final @NotNull String email;
    private final @NotNull String name;
    private final @NotNull String passwordHash;
    private final @NotNull String passwordSalt;
    private final @NotNull Canteen canteen;

    private UserSkeleton(final @NotNull Builder builder) {
        email = Objects.requireNonNull(builder.email, "email");
        name = Objects.requireNonNull(builder.name, "name");
        passwordHash = Objects.requireNonNull(builder.passwordHash, "passwordHash");
        passwordSalt = Objects.requireNonNull(builder.passwordSalt, "passwordSalt");
        canteen = Objects.requireNonNull(builder.canteen, "canteen");
    }

    public @NotNull String email() {
        return email;
    }

    public @NotNull String name() {
        return name;
    }

    public @NotNull String passwordHash() {
        return passwordHash;
    }

    public @NotNull String passwordSalt() {
        return passwordSalt;
    }

    public @NotNull Canteen canteen() {
        return canteen;
    }

    @Override
    public boolean equals(final @Nullable Object object) {
        if (this == object) return true;
        if (!(object instanceof final UserSkeleton that)) return false;

        return email.equals(that.email) && name.equals(that.name) && passwordHash.equals(that.passwordHash) && passwordSalt.equals(that.passwordSalt) && canteen == that.canteen;
    }

    @Override
    public int hashCode() {
        int result = email.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + passwordHash.hashCode();
        result = 31 * result + passwordSalt.hashCode();
        result = 31 * result + canteen.hashCode();
        return result;
    }

    @Override
    public @NotNull String toString() {
        return "UserSkeleton{" +
               "email='" + email + '\'' +
               ", name='" + name + '\'' +
               ", passwordHash='" + passwordHash + '\'' +
               ", passwordSalt='" + passwordSalt + '\'' +
               ", canteen=" + canteen +
               '}';
    }

    public static final class Builder implements Skeleton.Builder<Builder, UserSkeleton> {
        private @Nullable String email;
        private @Nullable String name;
        private @Nullable String passwordHash;
        private @Nullable String passwordSalt;
        private @Nullable Canteen canteen;

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