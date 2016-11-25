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
    private ArrayList<String> appliedDiscounts = new ArrayList<String>();

    public Member(String firstName, String lastName, Date dateOfBirth, String cprNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.cprNumber = cprNumber;
    }


    public void applyDiscount(Discount discount) {
        this.appliedDiscounts.add(discount.getType());
    }

    public void removeDiscount(Discount discount) {
        int index = this.appliedDiscounts.indexOf(discount.getType());
        if (index != -1) {
            this.appliedDiscounts.remove(index);
        } else {
            throw new IllegalArgumentException("No discount of the given type found");
        }
    }

    public String getAppliedDiscountsString() {
        String result = "";

        for (int i = 0; i < this.appliedDiscounts.size(); i++) {
           result = (i == this.appliedDiscounts.size() - 1)
            ? result + this.appliedDiscounts.get(i)
            : result + this.appliedDiscounts.get(i) + ", ";
        }
        return result;
    }

    public void registerPayment(Payment payment) {
        this.payments.add(payment);
    }

    public void registerLapTime(LapTime lapTime) {
        this.lapTimes.add(lapTime);
    }
}