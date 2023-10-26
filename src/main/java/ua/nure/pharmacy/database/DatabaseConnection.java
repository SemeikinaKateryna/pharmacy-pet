package ua.nure.pharmacy.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DatabaseConnection {
    private static final String DB_PROPERTIES_FILE = "db.properties";

    public static Connection getConnection() throws SQLException {
        Properties properties = loadDbProperties();
        String url = properties.getProperty("db.url");
        String username = properties.getProperty("db.username");
        String password = properties.getProperty("db.password");
        try {
            return DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            throw e;
        }
    }

    private static Properties loadDbProperties() {
        Properties properties = new Properties();
        try (InputStream inputStream = DatabaseConnection.class.getClassLoader().getResourceAsStream(DB_PROPERTIES_FILE)) {
            if (inputStream != null) {
                properties.load(inputStream);
            } else {
                throw new IOException("Unable to find db.properties file.");
            }
        } catch (IOException e) {
            throw new RuntimeException("Error reading db.properties file.", e);
        }
        return properties;
    }
}
