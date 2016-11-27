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
            this.createChairmenTable();
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
            this.createTreasurersTable();
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
            this.createCoachesTable();
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
            this.createDiscountsTable();
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
            this.createDiscountsTable();
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
            this.createDisciplinesTable();
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
            this.createCompetitionsTable();
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
            this.createMembersTable();
            entry = Database.getTable("members").get(searchQuery);
        }

        if (entry == null) {
            return null;
        }

        return Member.construct(entry);
    }


    /*
     *  DB Tables
     */
    private void createChairmenTable() {
        // 1. Create the table
        try {
            List<String> columns = new ArrayList<String>();
            columns.add("username");
            columns.add("password");
            Database.createTable("chairmen", columns);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 2. Add the hardcoded chairman entry
        try {
            Database.getTable("chairmen")
                    .insert(new Chairman("chairman", "chairman").deconstruct());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createTreasurersTable() {
        // 1. Create the table
        try {
            List<String> columns = new ArrayList<String>();
            columns.add("username");
            columns.add("password");
            Database.createTable("treasurers", columns);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 2. Add the hardcoded treasurer entry
        try {
            Database.getTable("treasurers")
                    .insert(new Treasurer("treasurer", "treasurer").deconstruct());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createCoachesTable() {
        // 1. Create the table
        try {
            List<String> columns = new ArrayList<String>();
            columns.add("username");
            columns.add("password");
            Database.createTable("coaches", columns);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 2. Add the hardcoded coach entries
        try {
            TableHandler table = Database.getTable("coaches");
            table.insert(new Coach("coach1", "coach1").deconstruct());
            table.insert(new Coach("coach2", "coach2").deconstruct());
            table.insert(new Coach("coach3", "coach3").deconstruct());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createDiscountsTable() {
        // 1. Create the table
        try {
            List<String> columns = new ArrayList<String>();
            columns.add("modifier");
            columns.add("type");
            Database.createTable("discounts", columns);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 2. Add the hardcoded discount entry
        try {
            Database.getTable("discounts")
                    .insert(new SeniorDiscount(0.25).deconstruct());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createDisciplinesTable() {
        // 1. Create the table
        try {
            List<String> columns = new ArrayList<String>();
            columns.add("name");
            Database.createTable("disciplines", columns);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 2. Add the hardcoded discipline entries
        try {
            TableHandler table = Database.getTable("disciplines");
            table.insert(new Discipline("FREESTYLE_100").deconstruct());
            table.insert(new Discipline("FREESTYLE_200").deconstruct());
            table.insert(new Discipline("BACKSTROKE_100").deconstruct());
            table.insert(new Discipline("BACKSTROKE_200").deconstruct());
            table.insert(new Discipline("BUTTERFLY_100").deconstruct());
            table.insert(new Discipline("BUTTERFLY_200").deconstruct());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createCompetitionsTable() {
        // 1. Create the table
        try {
            List<String> columns = new ArrayList<String>();
            columns.add("name");
            columns.add("discipline_name");
            Database.createTable("competitions", columns);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 2. Add the hardcoded competition entries
        try {
            TableHandler table = Database.getTable("competitions");
            List<Discipline> disciplines = this.getDisciplines();

            table.insert(new Competition("World Aquatics",
                    disciplines.get(getRandom(0, disciplines.size() - 1))).deconstruct());
            table.insert(new Competition("Duel in the Pool",
                    disciplines.get(getRandom(0, disciplines.size() - 1))).deconstruct());
            table.insert(new Competition("Back it up",
                    disciplines.get(getRandom(0, disciplines.size() - 1))).deconstruct());
            table.insert(new Competition("Last one alive wins",
                    disciplines.get(getRandom(0, disciplines.size() - 1))).deconstruct());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createMembersTable() {
        // Create the table
        try {
            List<String> columns = new ArrayList<String>();
            columns.add("first_name");
            columns.add("last_name");
            columns.add("cpr_number");
            columns.add("date_of_birth");
            columns.add("date_of_registration");
            columns.add("is_active");
            columns.add("is_elite");
            Database.createTable("members", columns);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createPaymentsTable() {
        // Create the table
        try {
            List<String> columns = new ArrayList<String>();
            columns.add("amount");
            columns.add("details");
            columns.add("payment_date");
            Database.createTable("payments", columns);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
     *  Helpers
     */
    private int getRandom(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }
}