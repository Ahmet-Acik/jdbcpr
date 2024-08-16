package org.ahmet;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DatabaseConfigUtil {

    private static final Properties properties = new Properties();

    static {
        try (InputStream input = DatabaseConfigUtil.class.getClassLoader().getResourceAsStream("database.properties")) {
            if (input == null) {
                throw new IOException("Unable to find database.properties");
            }
            properties.load(input);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static String getDatabaseName() {
        return properties.getProperty("database.name");
    }

    public static String getUsername() {
        return properties.getProperty("database.user");
    }

    public static String getPassword() {
        return properties.getProperty("database.password");
    }
}