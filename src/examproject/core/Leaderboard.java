package examproject.core;

import java.util.HashMap;

public class Leaderboard {

    public Discipline discipline;
    private HashMap<Member, LapTime> results = new HashMap<Member, LapTime>();

    public Leaderboard(Discipline discipline) {
        this.discipline = discipline;
    }

    public void addResult(Member member, LapTime lapTime) {
        this.results.put(member, lapTime);
    }
}
