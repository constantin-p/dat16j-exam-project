package examproject.core;

import examproject.db.Storable;

import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.HashMap;

public class LapTime implements Storable {

    public static final String INDIVIDUAL_TYPE = "INDIVIDUAL";

    public LocalTime time;
    public ZonedDateTime date;
    public String ID;
    public String type;

    public LapTime(LocalTime time, ZonedDateTime date, String ID, String type) {
        this.time = time;
        this.date = date;
        this.ID = ID;
        this.type = type;
    }

    /*
     *  DB integration
     */
    @Override
    public HashMap<String, String> deconstruct() {
        HashMap<String, String> values = new HashMap<String, String>();

        values.put("time", this.time.toString());
        values.put("date", this.date.toString());
        values.put("id", this.ID);
        values.put("type", this.type);

        return values;
    }

    public static LapTime construct(HashMap<String, String> valuesMap) {
        String time = valuesMap.get("time");
        String date = valuesMap.get("date");
        String ID = valuesMap.get("id");
        String type = valuesMap.get("type");

        return new LapTime(LocalTime.parse(time), ZonedDateTime.parse(date), ID, type);
    }

    // For debugging
    @Override
    public String toString() {
        return this.getClass().getCanonicalName()
                + "[time: " + this.time
                + ", date: " + this.date
                + ", type: " + this.type
                + ", ID: " + this.ID + "]";
    }
}
