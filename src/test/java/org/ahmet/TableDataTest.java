package org.ahmet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class TableDataTest extends BaseDatabaseTest {

    @BeforeEach
    public void init() {
        setDatabaseName(DatabaseConfigUtil.getDatabaseName());
    }

    @ParameterizedTest(name = "Valid data for countries table: {0}")
    @MethodSource("provideCountriesTableData")
    public void testValidCountriesTableData(Map<String, String> expectedData) throws SQLException {
        TableVerifier.verifyTableData(connection, "countries", expectedData, true);
    }

    @ParameterizedTest(name = "Empty data for countries table")
    @MethodSource("provideEmptyTableData")
    public void testEmptyCountriesTableData(Map<String, String> expectedData) throws SQLException {
        TableVerifier.verifyTableData(connection, "countries", expectedData, true);
    }

    @ParameterizedTest(name = "Missing columns data for countries table: {0}")
    @MethodSource("provideMissingColumnsData")
    public void testCountriesTableDataWithMissingColumns(Map<String, String> expectedData) throws SQLException {
        TableVerifier.verifyTableData(connection, "countries", expectedData, true);
    }

    @ParameterizedTest(name = "Invalid data for countries table: {0}")
    @MethodSource("provideInvalidData")
    public void testInvalidCountriesTableData(Map<String, String> expectedData) throws SQLException {
        TableVerifier.verifyTableData(connection, "countries", expectedData, false);
    }

    private static Stream<Map<String, String>> provideCountriesTableData() {
        return Stream.of(
            createEntry("AR", "Argentina", "2"),
            createEntry("AU", "Australia", "3"),
            createEntry("BE", "Belgium", "1"),
            createEntry("BR", "Brazil", "2"),
            createEntry("CA", "Canada", "2"),
            createEntry("CH", "Switzerland", "1"),
            createEntry("CN", "China", "3"),
            createEntry("DE", "Germany", "1"),
            createEntry("DK", "Denmark", "1"),
            createEntry("EG", "Egypt", "4"),
            createEntry("FR", "France", "1"),
            createEntry("HK", "HongKong", "3"),
            createEntry("IL", "Israel", "4"),
            createEntry("IN", "India", "3"),
            createEntry("IT", "Italy", "1"),
            createEntry("JP", "Japan", "3"),
            createEntry("KW", "Kuwait", "4"),
            createEntry("MX", "Mexico", "2"),
            createEntry("NG", "Nigeria", "4"),
            createEntry("NL", "Netherlands", "1"),
            createEntry("SG", "Singapore", "3"),
            createEntry("UK", "United Kingdom", "1"),
            createEntry("US", "United States of America", "2"),
            createEntry("ZM", "Zambia", "4"),
            createEntry("ZW", "Zimbabwe", "4")
        );
    }

    private static Stream<Map<String, String>> provideEmptyTableData() {
        return Stream.of(new HashMap<>());
    }

    private static Stream<Map<String, String>> provideMissingColumnsData() {
        Map<String, String> entry = new HashMap<>();
        entry.put("country_id", "US");
        // Missing country_name and region_id
        return Stream.of(entry);
    }

    private static Stream<Map<String, String>> provideInvalidData() {
        Map<String, String> entry = new HashMap<>();
        entry.put("country_id", "XX"); // Invalid country_id
        entry.put("country_name", "InvalidCountry");
        entry.put("region_id", "999"); // Invalid region_id
        return Stream.of(entry);
    }

    private static Map<String, String> createEntry(String countryId, String countryName, String regionId) {
        Map<String, String> entry = new HashMap<>();
        entry.put("country_id", countryId);
        entry.put("country_name", countryName);
        entry.put("region_id", regionId);
        return entry;
    }
}