package examproject.core;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

public class Member {

    public String firstName;
    public String lastName;
    public String preferredActivity;
    public Date dateOfBirth;
    public String cprNumber;
    private ArrayList<String> appliedDiscounts = new ArrayList<String>();
    private ArrayList<Double> appliedModifiers = new ArrayList<Double>();

    private double baseFee = 1000.0;
    public ZonedDateTime dateOfRegistration;
    public ArrayList<Payment> payments = new ArrayList<Payment>();

    public Member(String firstName, String lastName, Date dateOfBirth, String cprNumber, ZonedDateTime dateOfRegistration) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.cprNumber = cprNumber;
        this.dateOfRegistration = dateOfRegistration;
    }

    public double calculateFee() {
        double fee = this.baseFee;

        for (int i = 0; i < this.appliedModifiers.size(); i++) {
            fee = fee - (fee * this.appliedModifiers.get(i));
        }
        return fee;
    }

    public void registerPayment(Payment payment) {
       this.payments.add(payment);
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
}