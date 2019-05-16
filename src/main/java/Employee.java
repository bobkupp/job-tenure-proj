import java.time.LocalDate;
import java.util.Collection;
import java.util.Iterator;
import java.util.Random;
import java.util.TreeMap;
import java.time.temporal.ChronoUnit;

public class Employee {

    public static final int MINIMUM_AGE_OF_EMPLOYMENT = 18;

    public void addJob(Job job) {
        // need to figure out start/end dates
        LocalDate startDate = generateStartDate();
        job.setStartDate(startDate);
        job.setEndDate(generateEndDate(startDate));
        jobMap.put(startDate, job);
    }

    private LocalDate generateStartDate() {
        LocalDate start = null;
        if (jobMap.isEmpty()) {
            // set first job at age 18
            int yearsAgo = age - MINIMUM_AGE_OF_EMPLOYMENT;
            // sorta randomize start date
            start = LocalDate.now().minusYears(yearsAgo).minusWeeks(rand.nextInt(52)).plusDays(rand.nextInt(365));
        } else {
            Collection values = jobMap.values();
            Iterator<Job> iterator = values.iterator();
            while (iterator.hasNext()) {
                LocalDate prevEndDate = iterator.next().getEndDate();
                if (start == null || start.compareTo(prevEndDate) < 0) {
                    start = prevEndDate;
                }
            }
        }
        return start;
    }

    private LocalDate generateEndDate(LocalDate start) {
        LocalDate now = LocalDate.now();
        LocalDate end = start.plusYears(rand.nextInt(10)).minusMonths(rand.nextInt(12)).plusDays(rand.nextInt(365));
        if (end.compareTo(now) > 0) {
            end = now;
        }
        return end;
    }

    public int calculateTenureAtCompany(String companyName) {
        int tenure = 0;
        Company company = Company.getByName(companyName);
        if (company != null) {
            Iterator<Job> jobIterator = jobMap.values().iterator();
            while (jobIterator.hasNext()) {
                Job job = jobIterator.next();
                if (job.getCompany().equals(company)) {
                    tenure += ChronoUnit.DAYS.between(job.getStartDate(), job.getEndDate());
                }
            }
        }
        return tenure;
    }

    public Employee() {
        this.jobMap = new TreeMap<>();
        this.rand = new Random();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public TreeMap<LocalDate, Job> getJobMap() {
        return jobMap;
    }

    private Random rand;
    private String name;
    private int age;
    private String state;

    private TreeMap<LocalDate, Job> jobMap;
}
