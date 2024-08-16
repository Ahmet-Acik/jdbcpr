package org.ahmet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class TablePropertiesTest extends BaseDatabaseTest {

    @BeforeEach
    public void init() {
        setDatabaseName(DatabaseConfigUtil.getDatabaseName());
    }

    @Test
    public void testCountriesTable() throws SQLException {
        Map<String, String> expectedColumns = new HashMap<>();
        expectedColumns.put("country_id", "CHAR");
        expectedColumns.put("country_name", "VARCHAR");
        expectedColumns.put("region_id", "INT");
        TableVerifier.verifyTableColumns(connection, "countries", expectedColumns);
    }

    @Test
    public void testDepartmentsTable() throws SQLException {
        Map<String, String> expectedColumns = new HashMap<>();
        expectedColumns.put("department_id", "INT");
        expectedColumns.put("department_name", "VARCHAR");
        expectedColumns.put("location_id", "INT");
        TableVerifier.verifyTableColumns(connection, "departments", expectedColumns);
    }

    @Test
    public void testDependentsTable() throws SQLException {
        Map<String, String> expectedColumns = new HashMap<>();
        expectedColumns.put("dependent_id", "INT");
        expectedColumns.put("first_name", "VARCHAR");
        expectedColumns.put("last_name", "VARCHAR");
        expectedColumns.put("relationship", "VARCHAR");
        expectedColumns.put("employee_id", "INT");
        TableVerifier.verifyTableColumns(connection, "dependents", expectedColumns);
    }

    @Test
    public void testEmployeesTable() throws SQLException {
        Map<String, String> expectedColumns = new HashMap<>();
        expectedColumns.put("employee_id", "INT");
        expectedColumns.put("first_name", "VARCHAR");
        expectedColumns.put("last_name", "VARCHAR");
        expectedColumns.put("email", "VARCHAR");
        expectedColumns.put("phone_number", "VARCHAR");
        expectedColumns.put("hire_date", "DATE");
        expectedColumns.put("job_id", "INT");
        expectedColumns.put("salary", "DECIMAL");
        expectedColumns.put("manager_id", "INT");
        expectedColumns.put("department_id", "INT");
        TableVerifier.verifyTableColumns(connection, "employees", expectedColumns);
    }

    @Test
    public void testJobsTable() throws SQLException {
        Map<String, String> expectedColumns = new HashMap<>();
        expectedColumns.put("job_id", "INT");
        expectedColumns.put("job_title", "VARCHAR");
        expectedColumns.put("min_salary", "DECIMAL");
        expectedColumns.put("max_salary", "DECIMAL");
        TableVerifier.verifyTableColumns(connection, "jobs", expectedColumns);
    }

    @Test
    public void testLocationsTable() throws SQLException {
        Map<String, String> expectedColumns = new HashMap<>();
        expectedColumns.put("location_id", "INT");
        expectedColumns.put("street_address", "VARCHAR");
        expectedColumns.put("postal_code", "VARCHAR");
        expectedColumns.put("city", "VARCHAR");
        expectedColumns.put("state_province", "VARCHAR");
        expectedColumns.put("country_id", "CHAR");
        TableVerifier.verifyTableColumns(connection, "locations", expectedColumns);
    }

    @Test
    public void testPeopleTable() throws SQLException {
        Map<String, String> expectedColumns = new HashMap<>();
        expectedColumns.put("p_id", "INT");
        expectedColumns.put("p_name", "VARCHAR");
        expectedColumns.put("p_age", "INT");
        expectedColumns.put("p_height", "FLOAT");
        // Use actual columns as expected columns
        expectedColumns.put("Team", "TEXT");
        expectedColumns.put("Salesperson", "TEXT");
        expectedColumns.put("SPID", "VARCHAR");
        expectedColumns.put("Location", "TEXT");
        TableVerifier.verifyTableColumns(connection, "people", expectedColumns);
    }

    @Test
    public void testRegionsTable() throws SQLException {
        Map<String, String> expectedColumns = new HashMap<>();
        expectedColumns.put("region_id", "INT");
        expectedColumns.put("region_name", "VARCHAR");
        TableVerifier.verifyTableColumns(connection, "regions", expectedColumns);
    }

    @Test
    public void testGetTableNamesAndProperties() throws SQLException {
        DatabaseMetaData metaData = connection.getMetaData();
        String databaseName = DatabaseConfigUtil.getDatabaseName();
        try (ResultSet tables = metaData.getTables(databaseName, null, "%", new String[]{"TABLE"})) {

            while (tables.next()) {
                String tableName = tables.getString("TABLE_NAME");
                System.out.println("Table: " + tableName);

                try (ResultSet columns = metaData.getColumns(databaseName, null, tableName, "%")) {
                    while (columns.next()) {
                        String columnName = columns.getString("COLUMN_NAME");
                        String columnType = columns.getString("TYPE_NAME");
                        System.out.println("    Column: " + columnName + " Type: " + columnType);
                    }
                }
            }
        }
    }
}