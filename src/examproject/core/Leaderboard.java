package examproject.core;


import java.util.*;
import java.util.stream.Collectors;

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
        LinkedHashMap<LapTime, Member> sortedResults = new LinkedHashMap<LapTime, Member>();

        this.results.entrySet().stream()
            .sorted(Map.Entry.comparingByKey((k1, k2) -> k1.time.compareTo(k2.time)))
            .forEach((entry) -> sortedResults.put(entry.getKey(), entry.getValue()));
        this.results = sortedResults;
        return this.results;
    }
}
