package br.com.patterns.datasource;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class Database {

    private static final String URL = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1";

    public static final String SCHEMA_SQL = "schema.sql";

    {
        try (var con = getCon();
             var stmt = con.createStatement()) {
            stmt.executeUpdate("DROP ALL OBJECTS");

            InputStream schemaStream = ClassLoader.getSystemResourceAsStream(SCHEMA_SQL);
            if (schemaStream == null) {
                throw new RuntimeException("The '" + SCHEMA_SQL + "' file could not be read.");
            }

            String schemaSql = new String(schemaStream.readAllBytes());
            stmt.executeUpdate(schemaSql);
        } catch (IOException | SQLException e) {
            throw new RuntimeException("The database could not be initialized.", e);
        }
    }

    public Connection getCon() throws SQLException {
        return DriverManager.getConnection(URL);
    }
}
