// BaseDatabaseTest.java
package org.ahmet;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class BaseDatabaseTest {

    protected Connection connection;
    private String databaseName;

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }

    @BeforeEach
    public void setUp() throws SQLException {
        DatabaseConfigUtil.createDatabaseIfNotExists();
        DatabaseConfigUtil.executeSchema();
        databaseName = DatabaseConfigUtil.getDatabaseName();
        if (databaseName == null || databaseName.isEmpty()) {
            throw new SQLException("Database name is not set or is empty");
        }
        String username = DatabaseConfigUtil.getUsername();
        String password = DatabaseConfigUtil.getPassword();
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + databaseName, username, password);
    }

    @AfterEach
    public void tearDown() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }
}