package examproject.core;
import examproject.db.Storable;

import java.util.ArrayList;
import java.util.HashMap;

public class Competition implements Storable {

    public String name;
    public Discipline discipline;
    public ArrayList<Member> participants = new ArrayList<Member>();

    public Competition(String name, Discipline discipline) {
        this.name = name;
        this.discipline = discipline;
    }

    public void enterCompetition(Member member) {
        this.participants.add(member);
    }


    /*
     *  DB integration
     */
    @Override
    public HashMap<String, String> deconstruct() {
        HashMap<String, String> values = new HashMap<String, String>();

        values.put("name", this.name);
        values.put("discipline_name", this.discipline.name);

        return values;
    }

    public static Competition construct(HashMap<String, String> valuesMap) {
        String name = valuesMap.get("name");
        String disciplineName = valuesMap.get("discipline_name");

        return new Competition(name, new Discipline(disciplineName));
    }

    // For debugging
    @Override
    public String toString() {
        return this.getClass().getCanonicalName()
                + "[name: " + this.name
                + ", discipline: " + this.discipline
                + ", participants: " + this.participants + "]";
    }
}
