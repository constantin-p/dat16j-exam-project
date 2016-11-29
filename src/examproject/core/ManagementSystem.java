package examproject.core;

import examproject.db.Database;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ManagementSystem {

    private Chairman currentChairman;
    private Treasurer currentTreasurer;
    private Coach currentCoach;

    /*
     *  Chairman functionality
     */
    public Response chairmanSignIn(String username, String password) {
        HashMap<String, String> searchQuery = new HashMap<String, String>();
        searchQuery.put("username", username);
        searchQuery.put("password", password);

        HashMap<String, String> entry;
        try {
            entry = Database.getTable("chairmen").get(searchQuery);
        } catch (IllegalArgumentException e) {
            DBTables.createChairmenTable();
            entry = Database.getTable("chairmen").get(searchQuery);
        }

        if (entry == null) {
            return new Response(false, "Wrong username/password.");
        }

        this.currentChairman = Chairman.construct(entry);
        return new Response(true);
    }


    /*
     *  Treasurer Functionality
     */
    public Response treasurerSignIn(String username, String password) {
        HashMap<String, String> searchQuery = new HashMap<String, String>();
        searchQuery.put("username", username);
        searchQuery.put("password", password);

        HashMap<String, String> entry;
        try {
            entry = Database.getTable("treasurers").get(searchQuery);
        } catch (IllegalArgumentException e) {
            // No table with the given name was found, create the table and search again
            DBTables.createTreasurersTable();
            entry = Database.getTable("treasurers").get(searchQuery);
        }

        if (entry == null) {
            return new Response(false, "Wrong username/password.");
        }

        this.currentTreasurer = Treasurer.construct(entry);
        return new Response(true);
    }


    /*
     *  Coach Functionality
     */
    public Response coachSignIn(String username, String password) {
        HashMap<String, String> searchQuery = new HashMap<String, String>();
        searchQuery.put("username", username);
        searchQuery.put("password", password);

        HashMap<String, String> entry;
        try {
            entry = Database.getTable("coaches").get(searchQuery);
        } catch (IllegalArgumentException e) {
            // No table with the given name was found, create the table and search again
            DBTables.createCoachesTable();
            entry = Database.getTable("coaches").get(searchQuery);
        }

        if (entry == null) {
            return new Response(false, "Wrong username/password.");
        }

        this.currentCoach = Coach.construct(entry);
        return new Response(true);
    }

    public List<Member> getCoachMembers() {
        return this.currentCoach.getMembers();
    }


    public Response addMember(String firstName, String lastName, String CPRNumber,
                             ZonedDateTime dateOfBirth, ZonedDateTime dateOfRegistration,
                             boolean isActive, boolean isElite, Discipline preferredDiscipline,
                              Coach assignedCoach) {

        // Check for duplicate
        if (this.getMember(CPRNumber) != null) {
            return new Response(false, "Member already registered.");
        }

        // Create the member and add it to the database
        Member member = new Member(firstName, lastName, CPRNumber, dateOfBirth,
                dateOfRegistration, isActive, isElite, preferredDiscipline);

        Database.getTable("members").insert(member.deconstruct());

        // Assign the member to the coach (only for elite members)
        if (isElite && assignedCoach != null) {
            assignedCoach.registerMember(member);
        }

        return new Response(true);
    }

    public List<Member> getMembers() {
        List<HashMap<String, String>> entries;
        try {
            entries = Database.getTable("members").getAll();
        } catch (IllegalArgumentException e) {
            // No table with the given name was found, create the table and search again
            DBTables.createMembersTable();
            entries = Database.getTable("members").getAll();
        }

        List<Member> members = new ArrayList<Member>();
        for (HashMap<String, String> entry : entries) {
            members.add(Member.construct(entry));
        }

        return members;
    }

    public List<Coach> getCoaches() {
        List<HashMap<String, String>> entries;
        try {
            entries = Database.getTable("coaches").getAll();
        } catch (IllegalArgumentException e) {
            // No table with the given name was found, create the table and search again
            DBTables.createCoachesTable();
            entries = Database.getTable("coaches").getAll();
        }

        List<Coach> coaches = new ArrayList<Coach>();
        for (HashMap<String, String> entry : entries) {
            coaches.add(Coach.construct(entry));
        }

        return coaches;
    }

    public List<Discount> getDiscounts() {
        List<HashMap<String, String>> entries;
        try {
            entries = Database.getTable("discounts").getAll();
        } catch (IllegalArgumentException e) {
            // No table with the given name was found, create the table and search again
            DBTables.createDiscountsTable();
            entries = Database.getTable("discounts").getAll();
        }

        List<Discount> discounts = new ArrayList<Discount>();
        for (HashMap<String, String> entry : entries) {
            if (entry.get("type").equals(SeniorDiscount.TYPE)) {
                discounts.add(SeniorDiscount.construct(entry));
            }
        }

        return discounts;
    }

    public List<Discipline> getDisciplines() {
        List<HashMap<String, String>> entries;
        try {
            entries = Database.getTable("disciplines").getAll();
        } catch (IllegalArgumentException e) {
            // No table with the given name was found, create the table and search again
            DBTables.createDisciplinesTable();
            entries = Database.getTable("disciplines").getAll();
        }

        List<Discipline> disciplines = new ArrayList<Discipline>();
        for (HashMap<String, String> entry : entries) {
            disciplines.add(Discipline.construct(entry));
        }

        return disciplines;
    }

    public List<Competition> getCompetitions() {
        List<HashMap<String, String>> entries;
        try {
            entries = Database.getTable("competitions").getAll();
        } catch (IllegalArgumentException e) {
            // No table with the given name was found, create the table and search again
            DBTables.createCompetitionsTable(this.getDisciplines());
            entries = Database.getTable("competitions").getAll();
        }

        List<Competition> competitions = new ArrayList<Competition>();
        for (HashMap<String, String> entry : entries) {
            competitions.add(Competition.construct(entry));
        }

        return competitions;
    }

    public Leaderboard getLeaderboard(Discipline discipline) {
        Leaderboard leaderboard = new Leaderboard(discipline);

        HashMap<String, String> searchQuery = new HashMap<String, String>();
        searchQuery.put("discipline_name", discipline.name);

        List<HashMap<String, String>> entries;
        try {
            entries = Database.getTable("members").getAll(searchQuery);
        } catch (IllegalArgumentException e) {
            // No table with the given name was found, create the table and search again
            DBTables.createMembersTable();
            entries = Database.getTable("members").getAll(searchQuery);
        }

        for (HashMap<String, String> entry : entries) {
            Member member = Member.construct(entry);
            List<LapTime> lapTimesForMember = member.getLapTimes();

            for (LapTime lapTime : lapTimesForMember) {
                leaderboard.addResult(member, lapTime);
            }
        }

        return leaderboard;
    }

    /*
     *  Helpers
     */
    private Member getMember(String CPRNumber) {
        HashMap<String, String> searchQuery = new HashMap<String, String>();
        searchQuery.put("cpr_number", CPRNumber);

        HashMap<String, String> entry;
        try {
            entry = Database.getTable("members").get(searchQuery);
        } catch (IllegalArgumentException e) {
            // No table with the given name was found, create the table and search again
            DBTables.createMembersTable();
            entry = Database.getTable("members").get(searchQuery);
        }

        if (entry == null) {
            return null;
        }

        return Member.construct(entry);
    }
}