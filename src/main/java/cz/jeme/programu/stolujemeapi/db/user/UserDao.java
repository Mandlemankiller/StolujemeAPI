package cz.jeme.programu.stolujemeapi.db.user;

import cz.jeme.programu.stolujemeapi.Canteen;
import cz.jeme.programu.stolujemeapi.db.CryptoUtils;
import cz.jeme.programu.stolujemeapi.db.Dao;
import cz.jeme.programu.stolujemeapi.db.Database;
import cz.jeme.programu.stolujemeapi.db.StatementWrapper;
import cz.jeme.programu.stolujemeapi.rest.control.UserController;
import org.jetbrains.annotations.NotNull;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.Optional;

public enum UserDao implements Dao {
    INSTANCE;

    private final @NotNull StatementWrapper wrapper = StatementWrapper.wrapper();
    private final @NotNull Database database = Database.INSTANCE;

    @Override
    public void init() {
        try (final Connection connection = database.connection()) {
            // language=mariadb
            final String statementStr = """
                    CREATE TABLE IF NOT EXISTS users (
                    id_user MEDIUMINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
                    email VARCHAR(%d) NOT NULL UNIQUE,
                    name VARCHAR(%d) NOT NULL UNIQUE,
                    canteen VARCHAR(25) NOT NULL,
                    verified BOOLEAN NOT NULL DEFAULT FALSE,
                    registered_time DATETIME NOT NULL,
                    password_hash VARCHAR(%d) NOT NULL,
                    password_salt VARCHAR(%d) NOT NULL
                    );
                    """
                    .formatted(
                            UserController.EMAIL_LENGTH_MAX,
                            UserController.NAME_LENGTH_MAX,
                            CryptoUtils.KEY_LENGTH_BASE64,
                            CryptoUtils.SALT_LENGTH_BASE64
                    );
            connection.prepareStatement(statementStr).execute();
        } catch (final SQLException e) {
            throw new RuntimeException("Could not initialize user data access object!", e);
        }
    }

    public @NotNull Optional<User> userById(final int id) {
        try (final Connection connection = database.connection()) {
            // language=mariadb
            final String statementStr = """
                    SELECT email, name, canteen, verified, registered_time, password_hash, password_salt
                    FROM users WHERE id_user = ?;
                    """;
            final ResultSet result = wrapper
                    .wrap(connection.prepareStatement(statementStr))
                    .setInt(id)
                    .unwrap()
                    .executeQuery();
            if (!result.next()) return Optional.empty();
            return Optional.of(new User.Builder()
                    .id(id)
                    .email(result.getString(1))
                    .name(result.getString(2))
                    .canteen(Canteen.valueOf(result.getString(3)))
                    .verified(result.getBoolean(4))
                    .registeredTime(result.getTimestamp(5).toLocalDateTime())
                    .passwordHash(result.getString(6))
                    .passwordSalt(result.getString(7))
                    .build()
            );
        } catch (final SQLException e) {
            throw new RuntimeException("Could not find user!", e);
        }
    }

    public @NotNull Optional<User> userByEmail(final @NotNull String email) {
        try (final Connection connection = database.connection()) {
            // language=mariadb
            final String statementStr = """
                    SELECT id_user, name, canteen, verified, registered_time, password_hash, password_salt
                    FROM users WHERE email = ?;
                    """;
            final ResultSet result = wrapper.wrap(connection.prepareStatement(statementStr))
                    .setString(email)
                    .unwrap()
                    .executeQuery();
            if (!result.next()) return Optional.empty();
            return Optional.of(new User.Builder()
                    .id(result.getInt(1))
                    .email(email)
                    .name(result.getString(2))
                    .canteen(Canteen.valueOf(result.getString(3)))
                    .verified(result.getBoolean(4))
                    .registeredTime(result.getTimestamp(5).toLocalDateTime())
                    .passwordHash(result.getString(6))
                    .passwordSalt(result.getString(7))
                    .build()
            );
        } catch (final SQLException e) {
            throw new RuntimeException("Could not find user!", e);
        }
    }

    public @NotNull Optional<User> userByName(final @NotNull String name) {
        try (final Connection connection = database.connection()) {
            // language=mariadb
            final String statementStr = """
                    SELECT id_user, email, canteen, verified, registered_time, password_hash, password_salt
                    FROM users WHERE name = ?;
                    """;
            final ResultSet result = wrapper.wrap(connection.prepareStatement(statementStr))
                    .setString(name)
                    .unwrap()
                    .executeQuery();
            if (!result.next()) return Optional.empty();
            return Optional.of(new User.Builder()
                    .id(result.getInt(1))
                    .email(result.getString(2))
                    .name(name)
                    .canteen(Canteen.valueOf(result.getString(3)))
                    .verified(result.getBoolean(4))
                    .registeredTime(result.getTimestamp(5).toLocalDateTime())
                    .passwordHash(result.getString(6))
                    .passwordSalt(result.getString(6))
                    .build()
            );
        } catch (final SQLException e) {
            throw new RuntimeException("Could not find user!", e);
        }
    }


    public boolean existsUserId(final int id) {
        try (final Connection connection = database.connection()) {
            // language=mariadb
            final String statementStr = """
                    SELECT 1 FROM users WHERE id_user = ?;
                    """;
            return wrapper.wrap(connection.prepareStatement(statementStr))
                    .setInt(id)
                    .unwrap()
                    .executeQuery()
                    .next();
        } catch (final SQLException e) {
            throw new RuntimeException("Could search for user!", e);
        }
    }

    public boolean existsUserEmail(final @NotNull String email) {
        try (final Connection connection = database.connection()) {
            // language=mariadb
            final String statementStr = """
                    SELECT 1 FROM users WHERE email = ?;
                    """;
            return wrapper.wrap(connection.prepareStatement(statementStr))
                    .setString(email)
                    .unwrap()
                    .executeQuery()
                    .next();
        } catch (final SQLException e) {
            throw new RuntimeException("Could search for user!", e);
        }
    }

    public boolean existsUserName(final @NotNull String name) {
        try (final Connection connection = database.connection()) {
            // language=mariadb
            final String statementStr = """
                    SELECT 1 FROM users WHERE name = ?;
                    """;
            return wrapper.wrap(connection.prepareStatement(statementStr))
                    .setString(name)
                    .unwrap()
                    .executeQuery()
                    .next();
        } catch (final SQLException e) {
            throw new RuntimeException("Could search for user!", e);
        }
    }


    public @NotNull User insertUser(final @NotNull UserSkeleton skeleton) {
        try (final Connection connection = database.connection()) {
            // language=mariadb
            final String statementStr = """
                    INSERT INTO users (email, name, canteen, registered_time, password_hash, password_salt)
                    VALUES (?, ?, ?, ?, ?, ?);
                    """;
            final LocalDateTime registered = LocalDateTime.now();
            final PreparedStatement statement = wrapper
                    .wrap(connection.prepareStatement(statementStr, Statement.RETURN_GENERATED_KEYS))
                    .setString(skeleton.email())
                    .setString(skeleton.name())
                    .setString(skeleton.canteen().toString())
                    .setTimestamp(Timestamp.valueOf(registered))
                    .setString(skeleton.passwordHash())
                    .setString(skeleton.passwordSalt())
                    .unwrap();
            statement.execute();
            final ResultSet result = statement.getGeneratedKeys();
            if (!result.next()) throw new RuntimeException("Id was not returned!");
            return new User.Builder()
                    .id(result.getInt(1))
                    .email(skeleton.email())
                    .name(skeleton.name())
                    .canteen(skeleton.canteen())
                    .verified(false)
                    .registeredTime(registered)
                    .passwordHash(skeleton.passwordHash())
                    .passwordSalt(skeleton.passwordSalt())
                    .build();
        } catch (final SQLException e) {
            throw new RuntimeException("Could not create user!", e);
        }
    }
}