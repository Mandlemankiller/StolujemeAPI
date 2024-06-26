package cz.jeme.programu.stolujemeapi.db;

import cz.jeme.programu.stolujemeapi.EnvVar;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

public enum Database {
    INSTANCE;

    private final @NotNull String url = EnvVar.DATABASE_URL.require();
    private final @NotNull String user = EnvVar.DATABASE_USER.require();
    private final @NotNull String password = EnvVar.DATABASE_PASSWORD.require();
    private final @NotNull Logger logger = LoggerFactory.getLogger(getClass());

    @ApiStatus.Internal
    public void init() {
        logger.info("Testing database connection...");
        try (final Connection connection = connection()) {
            logger.info("Established connection to database");
            final DatabaseMetaData meta = connection.getMetaData();
            final String driver = "%s (%s)".formatted(meta.getDriverName(), meta.getDriverVersion());
            final String jdbcVersion = "%d.%d".formatted(meta.getDriverMajorVersion(), meta.getDriverMinorVersion());
            logger.info("Driver: {}", driver);
            logger.info("JDBC version: {}", jdbcVersion);
            // language=mariadb
            final ResultSet result = connection.prepareStatement("SELECT VERSION();").executeQuery();
            if (!result.next()) throw new RuntimeException("Database version was not returned!");
            logger.info("Distribution: {}", result.getString(1));
        } catch (final SQLException e) {
            throw new RuntimeException("Could not connect to database!");
        }
        logger.info("Database connection test success");
    }

    public @NotNull Connection connection() throws SQLException {
        final Connection connection;
        try {
            connection = DriverManager.getConnection(
                    url,
                    user,
                    password
            );
        } catch (final SQLException e) {
            logger.error("Could not establish connection to database!");
            throw e;
        }
        return connection;
    }
}