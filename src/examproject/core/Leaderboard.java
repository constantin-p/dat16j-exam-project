package examproject.core;

import java.util.*;

public class Leaderboard {

    public Discipline discipline;
    private HashMap<Member, LapTime> results = new HashMap<Member, LapTime>();

    public Leaderboard(Discipline discipline) {
        this.discipline = discipline;
    }

    public void addResult(Member member, LapTime lapTime) {
        this.results.put(member, lapTime);
    }

    public LinkedHashMap<Member, LapTime> getResults() {
        List<Member> mapKeys = new ArrayList<Member>(this.results.keySet());
        List<LapTime> mapValues = new ArrayList<LapTime>(this.results.values());

        mapValues.sort((v1, v2) -> v1.time.compareTo(v2.time));

        LinkedHashMap<Member, LapTime> sortedMap = new LinkedHashMap<Member, LapTime>();

        Iterator<LapTime> valueIt = mapValues.iterator();
        while (valueIt.hasNext()) {
            LapTime val = valueIt.next();
            Iterator<Member> keyIt = mapKeys.iterator();

            while (keyIt.hasNext()) {
                Member key = keyIt.next();
                LapTime comp1 = this.results.get(key);
                LapTime comp2 = val;

                if (comp1.equals(comp2)) {
                    keyIt.remove();
                    sortedMap.put(key, val);
                    break;
                }
            }
        }
        return sortedMap;
    }
}
