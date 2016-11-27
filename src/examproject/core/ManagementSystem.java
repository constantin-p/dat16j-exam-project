package examproject.core;

import examproject.db.Database;
import examproject.db.TableHandler;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

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

        this.currentChairman = (Chairman) Chairman.construct(entry);
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

        this.currentTreasurer = (Treasurer) Treasurer.construct(entry);
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

        this.currentCoach = (Coach) Coach.construct(entry);
        return new Response(true);
    }

    /*
     *  Member functionality
     */
    public Response addMember(String firstName, String lastName, String CPRNumber,
                             ZonedDateTime dateOfBirth, ZonedDateTime dateOfRegistration,
                             boolean isActive, boolean isElite) {

        // Check for duplicate
        if (this.getMember(CPRNumber) != null) {
            return new Response(false, "Member already registered.");
        }

        // Create the member and add it to the databasse
        Member member = new Member(firstName, lastName, CPRNumber, dateOfBirth,
                dateOfRegistration, isActive, isElite);

        Database.getTable("members").insert(member.deconstruct());

        return new Response(true);
    }


//    protected Member updateMember(String username, String password) {
//        // placeholder functionality -> getMember(...id);
//        // ?id
//        ZonedDateTime dateOfRegistrationUTC = ZonedDateTime.now(ZoneOffset.UTC);
//        return new Member("test", "test", new Date(), "test", dateOfRegistrationUTC);
//    }

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