package examproject.core;
import java.util.ArrayList;

public class Competition {

   public ArrayList<Member> participants = new ArrayList<Member>();

    public void enterCompetition(Member member) {
        this.participants.add(member);
    }

}
