package examproject.core;

import examproject.db.Storable;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Objects;

import static java.time.temporal.ChronoUnit.YEARS;

public class Member implements Storable {

    public String firstName;
    public String lastName;
    public String CPRNumber;

    public ZonedDateTime dateOfBirth;
    public ZonedDateTime dateOfRegistration;

    public Discipline preferredDiscipline;
    public boolean isActive;
    public boolean isElite;


    public ArrayList<LapTime> lapTimes = new ArrayList<LapTime>();
    private ArrayList<String> appliedDiscounts = new ArrayList<String>();
    private ArrayList<Double> appliedModifiers = new ArrayList<Double>();

    public ArrayList<Payment> payments = new ArrayList<Payment>();

    public Member(String firstName, String lastName, String CPRNumber,
                  ZonedDateTime dateOfBirth, ZonedDateTime dateOfRegistration,
                  boolean isActive, boolean isElite) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.CPRNumber = CPRNumber;

        this.dateOfBirth = dateOfBirth;
        this.dateOfRegistration = dateOfRegistration;

        this.isActive = isActive;
        this.isElite = isElite;
    }

    public double calculateFee() {
        double fee = this.getBaseFee();

        // deduct the discounts
        for (int i = 0; i < this.appliedModifiers.size(); i++) {
            fee = fee - (fee * this.appliedModifiers.get(i));
        }
        return fee;
    }

    public void registerPayment(Payment payment) {
       this.payments.add(payment);
    }

    public void registerLapTime(LapTime lapTime) {
        this.lapTimes.add(lapTime);
    }

    public boolean hasPaidThisYear() {
        int currentYear = Integer.parseInt(ZonedDateTime.now(ZoneOffset.UTC).format(DateTimeFormatter.ofPattern("yyyy")));
        for(int j = 0; j < this.payments.size(); j++) {
            if(Integer.parseInt(this.payments.get(j).paymentDate.format(DateTimeFormatter.ofPattern("yyyy"))) == currentYear) {
                return true;
            }
        }
        return false;
    }

    public Response hasLatePayment() {
        int startYear = Integer.parseInt(this.dateOfRegistration.format(DateTimeFormatter.ofPattern("yyyy")));
        int currentYear = Integer.parseInt(ZonedDateTime.now(ZoneOffset.UTC).format(DateTimeFormatter.ofPattern("yyyy")));
        System.out.println(startYear + " " + currentYear);
        for(int i = startYear; i < currentYear; i++) {
            boolean found = false;
            for(int j = 0; j < this.payments.size(); j++) {
                if(Integer.parseInt(this.payments.get(j).paymentDate.format(DateTimeFormatter.ofPattern("yyyy"))) == i) {
                    found = true;
                    break;
                }
            }
            if(!found) {
                return new Response(true, String.valueOf(i));
            }
        }
        return new Response(false);
    }



    public void applyDiscount(Discount discount) {
        this.appliedDiscounts.add(discount.getType());
        this.appliedModifiers.add(discount.getModifier());

    }

    public void removeDiscount(Discount discount) {
        int index = this.appliedDiscounts.indexOf(discount.getType());
        if (index != -1) {
            this.appliedDiscounts.remove(index);
            this.appliedModifiers.remove(index);

        } else {
            throw new IllegalArgumentException("No discount of the given type found");
        }
    }

    public boolean hasDiscount(Discount discount) {
        for (int i = 0; i < this.appliedDiscounts.size(); i++) {
            if (Objects.equals(this.appliedDiscounts.get(i), discount.getType())) {
                return true;
            }
        }
        return false;
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


    private double getBaseFee() {
        if (this.isActive) {
            if (YEARS.between(this.dateOfBirth, ZonedDateTime.now(ZoneOffset.UTC)) >= 18) {
                return 1600.0;
            }
            return 1000.0;
        }
        return 500.0;
    }


    /*
     *  DB integration
     */
    @Override
    public HashMap<String, String> deconstruct() {
        HashMap<String, String> values = new HashMap<String, String>();

        values.put("first_name", this.firstName);
        values.put("last_name", this.lastName);
        values.put("cpr_number", this.CPRNumber);

        values.put("date_of_birth", this.dateOfBirth.toString());
        values.put("date_of_registration", this.dateOfRegistration.toString());

        values.put("is_active", Boolean.toString(this.isActive));
        values.put("is_elite", Boolean.toString(this.isElite));

        return values;
    }

    public static Member construct(HashMap<String, String> valuesMap) {
        String firstName = valuesMap.get("first_name");
        String lastName = valuesMap.get("last_name");
        String CPRNumber = valuesMap.get("cpr_number");

        String dateOfBirth = valuesMap.get("date_of_birth");
        String dateOfRegistration = valuesMap.get("date_of_registration");

        String isActive = valuesMap.get("is_active");
        String isElite = valuesMap.get("is_elite");

        return new Member(firstName, lastName, CPRNumber,
                ZonedDateTime.parse(dateOfBirth), ZonedDateTime.parse(dateOfRegistration),
                Boolean.parseBoolean(isActive), Boolean.parseBoolean(isElite));
    }

    // For debugging
    @Override
    public String toString() {
        return this.getClass().getCanonicalName()
                + "[firstName: " + this.firstName
                + ", lastName: " + this.lastName
                + ", CPRNumber: " + this.CPRNumber
                + ", preferredDiscipline: " + this.preferredDiscipline
                + ", dateOfBirth: " + this.dateOfBirth
                + ", dateOfRegistration: " + this.dateOfRegistration
                + ", isActive: " + this.isActive
                + ", isElite: " + this.isElite + "]";
    }
}