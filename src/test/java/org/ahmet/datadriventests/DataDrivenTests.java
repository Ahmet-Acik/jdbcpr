package org.ahmet.datadriventests;

import org.ahmet.BaseDatabaseTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class DataDrivenTests extends BaseDatabaseTest {
    private static final Logger LOGGER = Logger.getLogger(DataDrivenTests.class.getName());
    String dbUrl = "jdbc:mysql://localhost:3306/";
    String dbUserName = "root";
    String dbPassword = "root7623";
    Connection connection;
    Statement statement;
    ResultSet resultSet;

    private ResultSet executeQuery(String query) throws SQLException {
        Connection connection = DriverManager.getConnection(dbUrl, dbUserName, dbPassword);
        Statement statement = connection.createStatement();
        return statement.executeQuery(query);
    }


    @Test
    void testConnection() throws SQLException {
        try (ResultSet resultSet = executeQuery("SELECT * FROM hr_data.countries")) {
            LOGGER.info("Connection successful");
            while (resultSet.next()) {
                LOGGER.info(resultSet.getString("country_id") + " " + resultSet.getString("country_name") + " " + resultSet.getString("region_id"));
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Connection failed", e);
        }
    }
//1,Administration,1700
    @Test
    void testDepartmentsTable() throws SQLException {
        try (ResultSet resultSet = executeQuery("SELECT * FROM hr_data.departments WHERE department_id = 1")) {

            assertNotNull(resultSet, "Result set should not be null");
            if (resultSet.next()) {
                assertEquals(1, resultSet.getInt("department_id"));
                assertEquals("Administration", resultSet.getString("department_name"));
                assertEquals(1700, resultSet.getInt("location_id"));
            }
        }
    }

//AR,Argentina,2
    @Test
    void TestCountriesTable() throws SQLException {
        try (ResultSet resultSet = executeQuery("SELECT * FROM hr_data.countries WHERE country_id = 'AR'")) {
            assertNotNull(resultSet, "Result set should not be null");
            if (resultSet.next()) {
                assertEquals("AR", resultSet.getString("country_id"));
                assertEquals("Argentina", resultSet.getString("country_name"));
                assertEquals(2, resultSet.getInt("region_id"));
            }
        }
    }



    @AfterEach
    void closeConnection() throws SQLException {
        if (resultSet != null && !resultSet.isClosed()) {
            resultSet.close();
        }
        if (statement != null && !statement.isClosed()) {
            statement.close();
        }
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }
}