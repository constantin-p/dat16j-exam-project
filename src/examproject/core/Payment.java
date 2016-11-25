package examproject.core;

import java.time.ZonedDateTime;

public class Payment {

    private double amount;
    private String details;
    public ZonedDateTime paymentDate;

    public Payment(double amount, String details, ZonedDateTime paymentDate) {
        this.amount = amount;
        this.details = details;
        this.paymentDate = paymentDate;
    }
}
