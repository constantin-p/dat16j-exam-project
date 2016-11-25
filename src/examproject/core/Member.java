package examproject.core;

import java.util.ArrayList;
import java.util.Date;

public class Member {

    public String firstName;
    public String lastName;
    public String preferredActivity;
    public Date dateOfBirth;
    public String cprNumber;
    public ArrayList<Payment> payments = new ArrayList<Payment>();
    public ArrayList<LapTime> lapTimes = new ArrayList<LapTime>();

    public Member(String firstName, String lastName, Date dateOfBirth, String cprNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.cprNumber = cprNumber;
    }

    public void registerPayment(Payment payment) {
        this.payments.add(payment);
    }

    public void registerLapTime(LapTime lapTime) {
        this.lapTimes.add(lapTime);
    }
}