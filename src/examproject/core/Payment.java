package examproject.core;

import examproject.db.Storable;

import java.time.ZonedDateTime;
import java.util.HashMap;

public class Payment implements Storable {

    private double amount;
    private String details;
    public ZonedDateTime paymentDate;

    public Payment(double amount, String details, ZonedDateTime paymentDate) {
        this.amount = amount;
        this.details = details;
        this.paymentDate = paymentDate;
    }

    /*
     *  DB integration
     */
    @Override
    public HashMap<String, String> deconstruct() {
        HashMap<String, String> values = new HashMap<String, String>();

        values.put("amount", Double.toString(this.amount));
        values.put("details", this.details);
        values.put("payment_date", this.paymentDate.toString());

        return values;
    }

    public static Payment construct(HashMap<String, String> valuesMap) {
        String amount = valuesMap.get("amount");
        String details = valuesMap.get("details");
        String paymentDate = valuesMap.get("payment_date");

        return new Payment(Double.parseDouble(amount),
                details, ZonedDateTime.parse(paymentDate));
    }

    // For debugging
    @Override
    public String toString() {
        return this.getClass().getCanonicalName()
                + "[amount: " + this.amount
                + ", details: " + this.details
                + ", paymentDate: " + this.paymentDate + "]";
    }
}
