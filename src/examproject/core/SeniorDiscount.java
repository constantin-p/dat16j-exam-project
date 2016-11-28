package examproject.core;

import examproject.db.Storable;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.HashMap;

import static java.time.temporal.ChronoUnit.YEARS;

public class SeniorDiscount implements Discount, Storable {

    private double modifier;
    public static final String TYPE = "SENIOR_DISCOUNT";

    public SeniorDiscount(double modifier) {
        this.modifier = modifier;
    }

    public double getModifier() {
        return this.modifier;
    }

    public String getType() {
        return TYPE;
    }

    //TODO Use date to check condition if over 60
    public boolean checkCondition(Member member) {
        if (YEARS.between(member.dateOfBirth, ZonedDateTime.now(ZoneOffset.UTC)) >= 60) {
            return true;
        }
        return false;
    }

    /*
     *  DB integration
    */
    @Override
    public HashMap<String, String> deconstruct() {
        HashMap<String, String> values = new HashMap<String, String>();

        values.put("modifier", Double.toString(this.getModifier()));
        values.put("type", this.getType());

        return values;
    }

    public static SeniorDiscount construct(HashMap<String, String> valuesMap) {
        String percent = valuesMap.get("modifier");

        return new SeniorDiscount(Double.parseDouble(percent));
    }

    // For debugging
    @Override
    public String toString() {
        return this.getClass().getCanonicalName()
                + "[modifier: " + this.getModifier()
                + ", type: " + this.getType() + "]";
    }
}
