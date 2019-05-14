import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.time.LocalDate;
import java.util.HashMap;

class Company {
    /*
     *  companies: map with state => Company-object
     */
    private static HashMap<String, ArrayList<Company>> companies = new HashMap<>();

    public Company() {
        if (companies.isEmpty()) {
            buildCompanyList();
        }
        currentAndFormerEmployees = new ArrayList<>();
    }

    public Company(String name, ArrayList<String> state) {
        this.name = name;
        this.state = state;
        currentAndFormerEmployees = new ArrayList<>();
    }

    /*
     *  This could be injected from a Bean
     *
     *  add each company with its list of states and its list of jobs
     *
     */
    private void buildCompanyList() {
        addCompany(new Company("Northeastern University", new ArrayList<>(Arrays.asList("MA", "NC"))));
        addCompany(new Company("University of Miami", new ArrayList<>(Arrays.asList("FL"))));
        addCompany(new Company("NH Learning Solutions", new ArrayList<>(Arrays.asList("NH"))));
        addCompany(new Company("EverTrue", new ArrayList<>(Arrays.asList("MA"))));
        addCompany(new Company("Google", new ArrayList<>(Arrays.asList("CA", "MA", "KY"))));
        addCompany(new Company("TripAdvisor", new ArrayList<>(Arrays.asList("MA", "NV", "OH"))));
        addCompany(new Company("Microsoft", new ArrayList<>(Arrays.asList("WA"))));
        addCompany(new Company("GoDaddy", new ArrayList<>(Arrays.asList("AZ", "NV", "DC"))));
        addCompany(new Company("SXSW", new ArrayList<>(Arrays.asList("TX"))));
        addCompany(new Company("HBO", new ArrayList<>(Arrays.asList("NY", "RI"))));
        addCompany(new Company("Fender", new ArrayList<>(Arrays.asList("AZ", "CA", "TN"))));
        addCompany(new Company("Alaskan Airlines", new ArrayList<>(Arrays.asList("WA"))));
        addCompany(new Company("Southern Living Magazine", new ArrayList<>(Arrays.asList("AL", "TX", "LA", "GA", "MO"))));
        addCompany(new Company("Goldman Sachs", new ArrayList<>(Arrays.asList("NY"))));
        addCompany(new Company("Planet Fitness", new ArrayList<>(Arrays.asList("NH", "ME"))));
        addCompany(new Company("Gold’s Gym", new ArrayList<>(Arrays.asList("TX", "GA", "KY"))));
        addCompany(new Company("Arnold Strongman Classic", new ArrayList<>(Arrays.asList("OH", "WV", "MT"))));
    }

    /*
     *  add company to hash map for each state that it has offices in
     */
    private void addCompany(Company company) {
        ArrayList<Company> companyList;
        Iterator<String> stateIterator = company.state.iterator();
        while (stateIterator.hasNext()) {
            String state = stateIterator.next();
            if (companies.containsKey(state)) {
                companyList = companies.get(state);
            } else {
                companyList = new ArrayList<>();
            }
            companyList.add(company);
            companies.put(state, companyList);
        }
    }

    public LocalDate calculateAverageTenure() {
        HashMap<String, LocalDate> tenureMap = new HashMap<>();
//        foreach (currentAndFormerEmployees : employee) {
//            if (tenureMap.has(employee.name)) {
//                tenureMap.value += employee.endDate - employee.startDate;
//            } else {
//                tenureMap.value = employee.endDate - employee.startDate;
//            }
//        }
//        LocalDate totalTenure =0;
//        foreach tenureMap : tenure {
//            totalTenure += tenure.value;
//        }
//        LocalDate averageTenure = totalTenure/count(tenureMap);
//        return averageTenure;
        return null;        // TBD
    }

    public void addEmployee(Employee employee) {
        currentAndFormerEmployees.add(employee);
    }

    public int getCompanyCount() {
        return companies.size();
    }

    public ArrayList<Company> getCompaniesForState(String state) {
        return companies.get(state);
    }

    private String name;
    private ArrayList<String> state;
    private ArrayList<Employee> currentAndFormerEmployees;
    private LocalDate averageTenure;
}
