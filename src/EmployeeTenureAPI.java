import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import java.sql.*;

public class EmployeeTenureAPI {

    Connection conn = null;

    public static void main(String[] args) {
        String csvFile = "./data/code-challenge-data.csv";

        EmployeeTenureAPI api = new EmployeeTenureAPI();
        if (!api.importCsvData(csvFile)) {
            System.exit(-1);
        }
        api.queryTable(); // test
        api.closeDatabase();
    }

    private Boolean importCsvData(String csvFilePath) {
        String line;
        String cvsSplitBy = ",";
        Boolean importedCsvData  = false;

        createDatabase();
        if (conn != null) {
            try (BufferedReader br = new BufferedReader(new FileReader(csvFilePath))) {

                while ((line = br.readLine()) != null) {

                    // use comma as separator
                    String[] employeeData = line.split(cvsSplitBy);
                    if (employeeData[0] != "first_name" && employeeData[1] != "last_name") {
                        if (!addRow(employeeData)) {
                            break;
                        }
                    }
                }
                importedCsvData = true;

            } catch (IOException ioe) {
                ioe.printStackTrace();
                System.out.println("Error reading csv data file: " + csvFilePath + ", error: " + ioe.getMessage());
            }
        }
        return importedCsvData;
    }

    private void createDatabase() {
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:test.db");
            Statement stat = conn.createStatement();
            stat.executeUpdate("drop table if exists people;");
            stat.executeUpdate("create table people (firstname, lastname, age, state);");
        } catch (ClassNotFoundException cnfe) {
            System.out.println("Unable to create database, JDBC class not found: " + cnfe.getMessage());
        } catch (SQLException sqle) {
            System.out.println("Unable to create database, failed to create table: " + sqle.getMessage());
            closeDatabase();
            conn = null;
        }
    }

    private void closeDatabase() {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException sqle) {
                System.out.println("Failure attempting to close database: " + sqle.getMessage());
            }
        }
    }

    private Boolean addRow(String[] employeeData) {
        try {
            PreparedStatement prep = conn.prepareStatement("insert into people values (?, ?, ?, ?);");

            for (int i = 0; i < employeeData.length; i++) {
                prep.setString(i + 1, employeeData[i]);
            }
            prep.addBatch();

            conn.setAutoCommit(false);
            prep.executeBatch();
            conn.setAutoCommit(true);
        } catch (SQLException sqle) {
            System.out.println("Unable to add row to 'people' table: " + sqle.getMessage());
            return false;
        }
        return true;
    }

    private ResultSet queryTable() {
        ResultSet rs = null;
        try {
            Statement stat = conn.createStatement();
            rs = stat.executeQuery("select * from people;");
            while (rs.next()) {
                System.out.println(
                    "name = " + rs.getString("firstname") + " " + rs.getString("lastname") +
                    ", age = " + rs.getString("age") + ", state = " + rs.getString("state"));
            }
        } catch (SQLException sqle) {
            System.out.println("Unable to add row to 'people' table: " + sqle.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException sqle) {
                System.out.println("Error trying to close resultSet: " + sqle.getMessage());
            }
        }
        return rs;
    }
}
