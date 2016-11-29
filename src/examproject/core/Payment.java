package examproject.core;

import examproject.db.Storable;

import java.time.ZonedDateTime;
import java.util.HashMap;

public class Payment implements Storable {

    private double amount;
    private String details;
    public ZonedDateTime date;

    public Payment(double amount, String details, ZonedDateTime date) {
        this.amount = amount;
        this.details = details;
        this.date = date;
    }

    /*
     *  DB integration
     */
    @Override
    public HashMap<String, String> deconstruct() {
        HashMap<String, String> values = new HashMap<String, String>();

        values.put("amount", Double.toString(this.amount));
        values.put("details", this.details);
        values.put("date", this.date.toString());

        return values;
    }

    public static Payment construct(HashMap<String, String> valuesMap) {
        String amount = valuesMap.get("amount");
        String details = valuesMap.get("details");
        String date = valuesMap.get("date");

        return new Payment(Double.parseDouble(amount),
                details, ZonedDateTime.parse(date));
    }

    // For debugging
    @Override
    public String toString() {
        return this.getClass().getCanonicalName()
                + "[amount: " + this.amount
                + ", details: " + this.details
                + ", date: " + this.date + "]";
    }
}
