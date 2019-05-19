import java.util.*;

class Company {

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
        companies.add(company);
        ArrayList<Company> companyList;
        Iterator<String> stateIterator = company.state.iterator();
        while (stateIterator.hasNext()) {
            String state = stateIterator.next();
            if (companiesByState.containsKey(state)) {
                companyList = companiesByState.get(state);
            } else {
                companyList = new ArrayList<>();
            }
            companyList.add(company);
            companiesByState.put(state, companyList);
        }
    }

    public void calculateAverageTenure() {
        double totalTenure = 0;
        Iterator<Employee> employeeIterator = currentAndFormerEmployees.iterator();
        while (employeeIterator.hasNext()) {
            totalTenure += employeeIterator.next().calculateTenureAtCompany(this.name);
        }
        this.averageTenureYears = currentAndFormerEmployees.size() > 0 ? totalTenure/currentAndFormerEmployees.size() : 0;
    }

    public void generateAllTenureData() {
        Iterator<Company> it = companies.iterator();
        while (it.hasNext()) {
            Company company = it.next();
            company.calculateAverageTenure();
        }
    }

    public static Company getByName(String companyName) {
        Company matchingCompany = null;
        Iterator<Company> it = companies.iterator();
        while (it.hasNext()) {
            Company company = it.next();
            if (company.getName().compareTo(companyName) == 0) {
                matchingCompany = company;
                break;
            }
        }
        return matchingCompany;
    }

    public void addEmployee(Employee employee) {
        currentAndFormerEmployees.add(employee);
    }

    public int getCompanyCount() {
        return companies.size();
    }

    public ArrayList<Company> getCompaniesForState(String state) {
        return companiesByState.get(state);
    }

    // returning rounded to the hundredths place
    public double getAverageTenure() {
        return Math.round(averageTenureYears * 100.0)/100.0;
    }

    public String getName() {
        return name;
    }

    public static HashSet<Company> getCompanies() {
        return companies;
    }

    public ArrayList<Employee> getCurrentAndFormerEmployees() {
        return currentAndFormerEmployees;
    }

    /*
     *  companiesByState: map with state => Company object
     *  companies: set of all companies
     */
    private static HashMap<String, ArrayList<Company>> companiesByState = new HashMap<>();
    private static HashSet<Company> companies = new HashSet<>();
    private String name;
    private ArrayList<String> state;
    private ArrayList<Employee> currentAndFormerEmployees;
    private double averageTenureYears;
}
