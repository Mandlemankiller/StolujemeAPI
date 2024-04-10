package cz.jeme.programu.stolujemeapi.db.meal;

import cz.jeme.programu.stolujemeapi.Canteen;
import cz.jeme.programu.stolujemeapi.db.Entry;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.UUID;

public class Meal implements Entry {
    private final int id;
    private final @NotNull UUID uuid;
    private final @NotNull Canteen canteen;
    private final @NotNull Course course;

    private Meal(final @NotNull Builder builder) {
        id = Objects.requireNonNull(builder.id, "id");
        uuid = Objects.requireNonNull(builder.uuid, "uuid");
        course = Objects.requireNonNull(builder.course, "course");
        canteen = Objects.requireNonNull(builder.canteen, "canteen");
    }

    @Override
    public int id() {
        return id;
    }

    public @NotNull UUID uuid() {
        return uuid;
    }

    public @NotNull Course course() {
        return course;
    }

    public @NotNull Canteen canteen() {
        return canteen;
    }

    @Override
    public @NotNull String toString() {
        return "Meal{" +
               "id=" + id +
               ", uuid=" + uuid +
               ", canteen=" + canteen +
               ", course=" + course +
               '}';
    }

    static final class Builder implements Entry.Builder<Builder, Meal> {
        private @Nullable Integer id;
        private @Nullable UUID uuid;
        private @Nullable Canteen canteen;
        private @Nullable Course course;

        @Override
        public @NotNull Builder id(final int id) {
            this.id = id;
            return this;
        }

        public @NotNull Builder uuid(final @NotNull UUID uuid) {
            this.uuid = uuid;
            return this;
        }

        public @NotNull Builder course(final @NotNull Course course) {
            this.course = course;
            return this;
        }

        public @NotNull Builder canteen(final @NotNull Canteen canteen) {
            this.canteen = canteen;
            return this;
        }

        @Override
        public @NotNull Meal build() {
            return new Meal(this);
        }
    }

    public enum Course {
        SOUP,
        MAIN,
        ADDITION
    }
}
