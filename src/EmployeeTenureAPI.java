import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.TreeMap;

public class EmployeeTenureAPI {

    public static final int MAX_JOBS = 3;

    Connection conn = null;
    Company companies = new Company();
    ArrayList<Employee> employees = new ArrayList<>();

    public static void main(String[] args) {
        String csvFile = "./data/code-challenge-data.csv";

        EmployeeTenureAPI api = new EmployeeTenureAPI();
        if (!api.importCsvData(csvFile)) {
            System.exit(-1);
        }
        HashMap<String, ArrayList<Company>> ds = api.generateRandomDataset();
        api.companies.generateAllTenureData();

        // 5a
        String  foo = api.getEmployeeData("Peter Greene");
        foo = api.getEmployeeData("Lori Palmer");
        foo = api.getEmployeeData("Harry Bradley");
        foo = api.getEmployeeData("Jose Pekerman");

        // 5b
        foo = api.getTenureRanking();

        // 5c
        foo = api.getMostTenured("SXSW");
        foo = api.getMostTenured("HBO");
        api.closeDatabase();
    }

    private Boolean importCsvData(String csvFilePath) {
        String line;
        String cvsSplitBy = ",";
        Boolean importedCsvData  = false;

        createDatabase();
        if (conn != null) {
            try (BufferedReader br = new BufferedReader(new FileReader(csvFilePath))) {
                int TEST_ONLY=0;
                while ((line = br.readLine()) != null) {
                    if (TEST_ONLY++ > 10)
                        break;

                    // use comma as separator
                    String[] employeeData = line.split(cvsSplitBy);

                    // add any row except for header line
                    if (!employeeData[0].equals("first_name") && !employeeData[1].equals("last_name")) {
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

    private HashMap<String, ArrayList<Company>> generateRandomDataset() {
        ArrayList<HashMap<String, String>> results = queryTable("select * from people;", getPeopleTableColumns());
        Iterator<HashMap<String, String>> rows = results.iterator();
        HashMap<String, ArrayList<Company>> dataset = new HashMap<>();

        // generate array of ints, that, when shuffled, gives a list of unique random numbers
        ArrayList<Integer> randomNumbersMax = new ArrayList<Integer>();
        for (int i =0 ; i < companies.getCompanyCount(); i++) {
            randomNumbersMax.add(new Integer(i));
        }

        while (rows.hasNext()) {
            HashMap<String, String> employeeInfo = rows.next();
            String state = employeeInfo.get("state");
            Employee employee = new Employee();
            employee.setName(employeeInfo.get("firstname") + " " + employeeInfo.get("lastname"));
            employee.setAge(Integer.parseInt(employeeInfo.get("age")));
            employee.setState(state);

            if (employee.getAge() >= Employee.MINIMUM_AGE_OF_EMPLOYMENT) {

                ArrayList<Company> inStateCompanies = companies.getCompaniesForState(state);
                // check if any eligible companies were found
                if (inStateCompanies != null && !inStateCompanies.isEmpty()) {
                    Company inStateCompany = null;
                    int companyCount = inStateCompanies.size();
                    ArrayList<Integer> randomNumbers = new ArrayList<>(randomNumbersMax.subList(0, companyCount));
                    Collections.shuffle(randomNumbers);
                    int companyIndex = 0;

                    // find up to MAX_JOBS jobs
                    for (int jobCount = 0; jobCount < MAX_JOBS && jobCount < companyCount; jobCount++) {
                        companyIndex = randomNumbers.get(jobCount);
                        inStateCompany = inStateCompanies.get(companyIndex);
                        Job job = new Job(inStateCompany, employee.getState(), Position.getRandomOccupation());
                        employee.addJob(job);
                        inStateCompany.addEmployee(employee);
                        if (!dataset.containsKey(state)) {
                            ArrayList<Company> company = new ArrayList<>();
                            company.add(inStateCompany);
                            dataset.put(state, company);
                        } else {
                           ArrayList<Company> company = dataset.get(state);
                           if (!company.contains(inStateCompany)) {
                               company.add(inStateCompany);
                           }
                        }
                    }
                }
            }
            this.employees.add(employee);
        }
        return dataset;
    }

    /*
     *  API: 5a: return json-formatted list of objects representing person's career data
     *  from most recent to least recent
     */
    private String getEmployeeData(String employeeName) {
        String jsonEmployeeData = "";

        Employee employeeData = findEmployeeInDataset(employeeName);

        if (employeeData != null) {
            jsonEmployeeData = objectToJson(employeeData.getJobMap());
        }
        return jsonEmployeeData;
    }

    private Employee findEmployeeInDataset(String name) {
        Employee employee = null;
        Iterator<Employee> it = employees.iterator();
        while (it.hasNext()) {
            employee = it.next();
            if (employee.getName().compareTo(name) == 0) {
                break;
            }
        }
        return employee;
    }

    /*
     *  API: 5b: return json-formatted list of companies with average tenure
     */
    private String getTenureRanking() {
        TreeMap<Integer, String> tenureMap = new TreeMap<>(Comparator.reverseOrder());

        HashSet<Company> companies = Company.getCompanies();
        Iterator<Company> it = companies.iterator();
        while (it.hasNext()) {
            Company company = it.next();
            tenureMap.put(company.getAverageTenure(), company.getName());
        }
        return objectToJson(tenureMap);
    }

    /*
     *  API: 5c: return json-formatted list of employees whose tenure at the company
     *  is/was greater than the company average
     */
    private String getMostTenured(String companyName) {
        TenureData tenureData = new TenureData();
        tenureData.most_tenured = new ArrayList<>();

        Company company = Company.getByName(companyName);

        if (tenureData != null) {
            tenureData.company = companyName;
            tenureData.avg_retention = company.getAverageTenure();
            Iterator<Employee> employeeIterator = company.getCurrentAndFormerEmployees().iterator();
            while (employeeIterator.hasNext()) {
                Employee employee = employeeIterator.next();
                int tenure = employee.calculateTenureAtCompany(companyName);
                if (tenure > company.getAverageTenure()) {
                    // fill in company info, if not there, then add employee info to list
                    EmployeeTenure employeeTenure = new EmployeeTenure();
                    employeeTenure.name = employee.getName();
                    employeeTenure.age = employee.getAge();
                    employeeTenure.tenure = tenure;
                    tenureData.most_tenured.add(employeeTenure);
                }
            }
        }

        return objectToJson(tenureData);
    }

    private String objectToJson(Object object) {
        String json = "";
        return json;
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

    private ArrayList<HashMap<String, String>> queryTable(String query, ArrayList<String> columns) {
        ResultSet rs = null;
        ArrayList<HashMap<String, String>> rows = new ArrayList<>();
        try {
            Statement stat = conn.createStatement();
            rs = stat.executeQuery(query);
            while (rs.next()) {
                HashMap<String, String> resultMap = new HashMap<>();
                Iterator<String> columnNames = columns.iterator();
                while (columnNames.hasNext()) {
                    String columnName = columnNames.next();
                    resultMap.put(columnName, rs.getString(columnName));
                }
                rows.add(resultMap);
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
        return rows;
    }

    private ArrayList<String> getPeopleTableColumns() {
        return new ArrayList<>(Arrays.asList("firstname", "lastname", "age", "state"));
    }

    class TenureData {
        String company;
        Integer avg_retention;
        ArrayList<EmployeeTenure> most_tenured;
    }

    class EmployeeTenure {
        String name;
        int age;
        float tenure;
    }
}
