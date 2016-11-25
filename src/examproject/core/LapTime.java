package examproject.core;

import java.time.ZonedDateTime;

public class LapTime {

    public String time;
    public ZonedDateTime date;
    public Competition competition;

    public LapTime(String time, ZonedDateTime date) {
        this.time = time;
        this.date = date;
    }

    public LapTime(String time, ZonedDateTime date, Competition competition) {
        this.time = time;
        this.date = date;
        this.competition = competition;
    }
}
