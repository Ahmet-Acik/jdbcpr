// TableVerifier.java
package org.ahmet;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class TableVerifier {

    // Private constructor to prevent instantiation
    private TableVerifier() {}

    // Static method to verify the data of a table
    public static void verifyTableData(Connection connection, String tableName, Map<String, String> expectedData, boolean shouldExist) throws SQLException {
        StringBuilder queryBuilder = new StringBuilder("SELECT * FROM " + tableName + " WHERE ");
        for (Map.Entry<String, String> entry : expectedData.entrySet()) {
            queryBuilder.append(entry.getKey()).append(" = '").append(entry.getValue()).append("' AND ");
        }
        String query = queryBuilder.substring(0, queryBuilder.length() - 5); // Remove the last " AND "

        System.out.println("Generated Query: " + query); // Log the generated query
        System.out.println("Expected Data: " + expectedData); // Log the expected data

        try (ResultSet resultSet = connection.createStatement().executeQuery(query)) {
            boolean dataExists = resultSet.next();
            if (shouldExist) {
                if (!dataExists) {
                    throw new AssertionError("No data found for table " + tableName + " with the expected values");
                }
                for (Map.Entry<String, String> entry : expectedData.entrySet()) {
                    String actualValue = resultSet.getString(entry.getKey());
                    assertEquals(entry.getValue(), actualValue, "Data for column " + entry.getKey() + " does not match");
                }
            } else {
                assertFalse(dataExists, "Data found for table " + tableName + " with the expected values, but it should not exist");
            }
        }
    }

    // Static method to verify the columns of a table
    public static void verifyTableColumns(Connection connection, String tableName, Map<String, String> expectedColumns) throws SQLException {
        DatabaseMetaData metaData = connection.getMetaData();
        try (ResultSet columns = metaData.getColumns(null, null, tableName, null)) {
            while (columns.next()) {
                String columnName = columns.getString("COLUMN_NAME");
                String columnType = columns.getString("TYPE_NAME");
                if (expectedColumns.containsKey(columnName)) {
                    assertEquals(expectedColumns.get(columnName), columnType, "Column type for " + columnName + " does not match");
                }
            }
        }
    }
}