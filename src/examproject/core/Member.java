package examproject.core;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

public class Member {

    public String firstName;
    public String lastName;
    public String preferredActivity;
    public Date dateOfBirth;
    public String cprNumber;

    public double fee = 1000.0;
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
        return fee;
    }

    public void registerPayment(Payment payment) {
       this.payments.add(payment);
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

}