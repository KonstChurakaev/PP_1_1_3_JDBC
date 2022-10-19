package jm.task.core.jdbc.util;


import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {


    private static Connection connection = null;


    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                Properties properties = getProps();
                connection = DriverManager.getConnection(
                        properties.getProperty("db.url"),
                        properties.getProperty("db.username"),
                        properties.getProperty("db.password"));
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        return connection;
    }


    private static Properties getProps() throws IOException {
        Properties properties = new Properties();
        try (InputStream in = Files.newInputStream(Paths.get(
                Util.class.getResource("/database.properties").toURI()))) {
            properties.load(in);
            return properties;

        } catch (URISyntaxException | IOException e) {
            throw new IOException("Database config file not found", e);
        }
    }

}

