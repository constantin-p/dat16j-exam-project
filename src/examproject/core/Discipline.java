package examproject.core;

public class Discipline {

    public String name;
    private Leaderboard leaderboard;

    public Discipline(String name) {
        this.name = name;
    }

    public void setLeaderboard(Leaderboard leaderboard) {
        this.leaderboard = leaderboard;
    }

    public Leaderboard getLeaderboard() {
        return this.leaderboard;
    }
}
