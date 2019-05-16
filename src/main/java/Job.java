import java.time.LocalDate;

public class Job {
    public Job(String companyName, String state, String position) {
        this.companyName = companyName;
        this.state = state;
        this.position = position;
    }
    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompany(String companyName) {
        this.companyName = companyName;
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

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate endDate) {
        this.startDate = endDate;
    }

    private String position;
    private String companyName;
    private String state;
    private LocalDate startDate;
    private LocalDate endDate;
}
