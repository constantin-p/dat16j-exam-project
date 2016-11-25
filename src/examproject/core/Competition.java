package examproject.core;
import java.util.ArrayList;

public class Competition {

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

}
