package examproject.core;


import java.util.ArrayList;

public class Leaderboard {

    private ArrayList<Member> scores = new ArrayList<Member>();

    public Leaderboard(ArrayList<Member> members) {
        this.scores = members;
    }

    public ArrayList<Member> getScores() {
        return scores;
    }

}
