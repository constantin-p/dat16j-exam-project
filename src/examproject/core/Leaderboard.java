package examproject.core;


import java.util.*;

public class Leaderboard {

    public Discipline discipline;
    private LinkedHashMap<LapTime, Member> results = new LinkedHashMap<LapTime, Member>();

    public Leaderboard(Discipline discipline) {
        this.discipline = discipline;
    }

    public void addResult(LapTime lapTime, Member member) {
        this.results.put(lapTime, member);
    }

    public LinkedHashMap<LapTime, Member> getResults() {
        List<LapTime> mapKeys = new ArrayList<LapTime>(this.results.keySet());
        List<Member> mapValues = new ArrayList<Member>(this.results.values());

        this.results.entrySet().stream()
                .sorted(Map.Entry.comparingByKey((k1, k2) -> k1.time.compareTo(k2.time)));

        return this.results;
    }
}
