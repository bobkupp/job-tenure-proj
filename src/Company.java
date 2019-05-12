import java.util.ArrayList;
import java.util.Arrays;
import java.time.LocalDate;
import java.util.HashMap;

class Company {
    private String name;
    private ArrayList<String> position;
    private ArrayList<String> state;
    private ArrayList<Employee> currentAndFormerEmployees;
    private LocalDate averageTenure;

    private static ArrayList<Company> companies;

    public void Company() {
        if (companies == null) {
            buildCompanyList();
        }
    }

    public Company(String name, ArrayList<String> position, ArrayList<String> state) {
        this.name = name;
        this.position = position;
        this.state = state;
    }

    /*
     *  This could be injected from a Bean
     */
    private void buildCompanyList() {
        companies.add (new Company("Northeastern University", new ArrayList<>(Arrays.asList("Back-end Software Engineer")), new ArrayList<>(Arrays.asList("MA", "NC"))));
        companies.add (new Company("University of Miami", new ArrayList<>(Arrays.asList("Front-end Software Engineer")), new ArrayList<>(Arrays.asList("FL"))));
        companies.add (new Company("NH Learning Solutions", new ArrayList<>(Arrays.asList("Manager")), new ArrayList<>(Arrays.asList("NH"))));
        companies.add (new Company("EverTrue", new ArrayList<>(Arrays.asList("CEO")), new ArrayList<>(Arrays.asList("MA"))));
        companies.add (new Company("Google", new ArrayList<>(Arrays.asList("Accountant")), new ArrayList<>(Arrays.asList("CA", "MA", "KY"))));
        companies.add (new Company("TripAdvisor", new ArrayList<>(Arrays.asList("Rockstar")), new ArrayList<>(Arrays.asList("MA", "NV", "OH"))));
        companies.add (new Company("Microsoft", new ArrayList<>(Arrays.asList("Personal Trainer")), new ArrayList<>(Arrays.asList("WA"))));
        companies.add (new Company("GoDaddy", new ArrayList<>(Arrays.asList("Physical Therapist")), new ArrayList<>(Arrays.asList("AZ", "NV", "DC"))));
        companies.add (new Company("SXSW", new ArrayList<>(Arrays.asList("Brand Manager")), new ArrayList<>(Arrays.asList("TX"))));
        companies.add (new Company("HBO", new ArrayList<>(Arrays.asList("Human Resources")), new ArrayList<>(Arrays.asList("NY", "RI"))));
        companies.add (new Company("Fender", new ArrayList<>(Arrays.asList("Pilot")), new ArrayList<>(Arrays.asList("AZ", "CA", "TN"))));
        companies.add (new Company("Alaskan Airlines", new ArrayList<>(Arrays.asList("Flight Attendant")), new ArrayList<>(Arrays.asList("WA"))));
        companies.add (new Company("Southern Living Magazine", new ArrayList<>(Arrays.asList("Bus Driver", "Teacher")), new ArrayList<>(Arrays.asList("AL", "TX", "LA", "GA", "MO"))));
        companies.add (new Company("Goldman Sachs", new ArrayList<>(Arrays.asList("Principal")), new ArrayList<>(Arrays.asList("NY"))));
        companies.add (new Company("Planet Fitness", new ArrayList<>(Arrays.asList("Event Planner")), new ArrayList<>(Arrays.asList("NH", "ME"))));
        companies.add (new Company("Gold’s Gym", new ArrayList<>(Arrays.asList("Security Advisor")), new ArrayList<>(Arrays.asList("TX", "GA", "KY"))));
        companies.add (new Company("Arnold Strongman Classic", new ArrayList<>(Arrays.asList("Booking Specialist", "Account Executive", "Counselor")), new ArrayList<>(Arrays.asList("OH", "WV", "MT"))));
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

    public void addEmployee() {

    }
}
