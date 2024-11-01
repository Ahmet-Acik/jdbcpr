package org.ahmet;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Properties;
import java.util.Scanner;

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

    public static void createDatabaseIfNotExists() {
        String url = "jdbc:mysql://localhost:3306/";
        String user = getUsername();
        String password = getPassword();
        String dbName = getDatabaseName();
        boolean createIfNotExists = Boolean.parseBoolean(properties.getProperty("database.createIfNotExists"));

        if (createIfNotExists) {
            try (Connection conn = DriverManager.getConnection(url, user, password);
                 Statement stmt = conn.createStatement()) {
                String sql = "CREATE DATABASE IF NOT EXISTS " + dbName;
                stmt.executeUpdate(sql);
                System.out.println("Database created or already exists.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void executeSchema() {
        String url = "jdbc:mysql://localhost:3306/" + getDatabaseName();
        String user = getUsername();
        String password = getPassword();

        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement stmt = conn.createStatement();
             InputStream input = DatabaseConfigUtil.class.getClassLoader().getResourceAsStream("schema.sql");
             Scanner scanner = new Scanner(input, StandardCharsets.UTF_8.name())) {

            scanner.useDelimiter(";");
            while (scanner.hasNext()) {
                String sql = scanner.next().trim();
                if (!sql.isEmpty()) {
                    stmt.execute(sql);
                }
            }
            System.out.println("Schema executed successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}