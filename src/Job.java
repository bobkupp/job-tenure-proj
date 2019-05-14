import java.time.LocalDate;
import java.util.Collection;

public class Job {
    public Job(Company company, String state, String position) {
        this.company = company;
        this.state = state;
        this.position = position;
    }
    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    private String position;
    private Company company;
    private String state;
//    private LocalDate startDate;   using as hash key
    private LocalDate endDate;
}
