import java.util.ArrayList;
import java.util.Random;

public class Position {

    private static Random rand;
    private static ArrayList<String> occupation;

    static {
        rand = new Random();

        occupation = new ArrayList<>();
        occupation.add("Back-end Software Engineer");
        occupation.add("Front-end Software Engineer");
        occupation.add("Manager");
        occupation.add("CEO");
        occupation.add("Accountant");
        occupation.add("Rockstar");
        occupation.add("Personal Trainer");
        occupation.add("Physical Therapist");
        occupation.add("Brand Manager");
        occupation.add("Human Resources");
        occupation.add("Pilot");
        occupation.add("Flight Attendant");
        occupation.add("Bus Driver");
        occupation.add("Teacher");
        occupation.add("Principal");
        occupation.add("Event Planner");
        occupation.add("Security Advisor");
        occupation.add("Booking Specialist");
        occupation.add("Account Executive");
        occupation.add("Counselor");
    }

    public static String getRandomOccupation() {
        // Generate random integer in range of occupation list
        int randomNumber = rand.nextInt(occupation.size() - 1);
        return occupation.get(randomNumber);
    }
}
