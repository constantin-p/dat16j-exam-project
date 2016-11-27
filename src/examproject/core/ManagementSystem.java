package examproject.core;

import examproject.db.Database;
import examproject.db.Storable;
import examproject.db.TableHandler;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class ManagementSystem {

    private PlaceholderFunctionalityProvider placeholderFunctionalityProvider;
    private Chairman currentChairman;
    private Treasurer currentTreasurer;
    private List<Discount> discounts = new ArrayList<Discount>();

    private ArrayList<Competition> competitions = new ArrayList<Competition>();

    private Coach currentCoach;
    private ArrayList<Discipline> disciplines = new ArrayList<Discipline>();
    private HashMap<String, Discipline> disciplineMap = new HashMap();

    public ManagementSystem() {

//        Chairman chairman = new Chairman("chairman", "chairman");
//        List<Storable> dataRows = new ArrayList<Storable>();
//        dataRows.add(chairman);

//        try {
//            List<String> columns = new ArrayList<String>();
//            columns.add("username");
//            columns.add("password");
//            Database.createTable("chairmen", columns);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

//        try {
//            Database.getTable("chairmen")
//                    .insert(chairman.deconstruct());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

//        try {
//            Database.getTable("chairmen")
//                    .getAll();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }


        // TODO: remove
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/mm/yyyy");
        ZonedDateTime utc = ZonedDateTime.now(ZoneOffset.UTC);
        System.out.println(utc + "  " + utc.getYear() + " " + utc.format(format));
        this.placeholderFunctionalityProvider = new PlaceholderFunctionalityProvider();
        this.discounts.add(new SeniorDiscount(0.25));


        this.disciplines.add(new Discipline("Freestyle 100"));
        this.disciplineMap.put("freestyle-100", this.disciplines.get(this.disciplines.size() - 1));
        this.disciplines.add(new Discipline("Freestyle 200"));
        this.disciplineMap.put("freestyle-200", this.disciplines.get(this.disciplines.size() - 1));
        this.disciplines.add(new Discipline("Backstroke 100"));
        this.disciplineMap.put("backstroke-100", this.disciplines.get(this.disciplines.size() - 1));
        this.disciplines.add(new Discipline("Backstroke 200"));
        this.disciplineMap.put("backstroke-200", this.disciplines.get(this.disciplines.size() - 1));
        this.disciplines.add(new Discipline("Butterfly 100"));
        this.disciplineMap.put("butterfly-100", this.disciplines.get(this.disciplines.size() - 1));
        this.disciplines.add(new Discipline("Butterfly 200"));
        this.disciplineMap.put("butterfly-200", this.disciplines.get(this.disciplines.size() - 1));

        this.competitions.add(new Competition("World Aquatics - freestyle", this.disciplineMap.get("freestyle-100")));
        this.competitions.add(new Competition("Duel in the Pool - butterfly", this.disciplineMap.get("butterfly-200")));
        this.competitions.add(new Competition("Back it up - backstroke", this.disciplineMap.get("backstroke-100")));
        this.competitions.add(new Competition("Last one alive wins - freestyle", this.disciplineMap.get("freestyle-200")));
    }

    /*
     *  Chairman functionality
     */
    // TODO: use response codes instead of boolean
    public Response chairmanSignIn(String username, String password) {
        HashMap<String, String> searchQuery = new HashMap<String, String>();
        searchQuery.put("username", username);
        searchQuery.put("password", password);

        HashMap<String, String> entry = Database.getTable("chairmen").get(searchQuery);
        if (entry == null) {
            return new Response(false, "Wrong username/password.");
        }

        this.currentChairman = (Chairman) Chairman.construct(entry);
        return new Response(true);
    }

    /*
     *  Treasurer Functionality
     */
    // TODO: use response codes instead of boolean
    public boolean treasurerSignIn(String username, String password) {
        try {
            this.currentTreasurer = this.placeholderFunctionalityProvider.getTreasurer(username, password);
        } catch(IllegalArgumentException e) {
            // TODO: Send the error message
            return false;
        }
        return true;
    }

    /*
     *  Coach Functionality
     */
    // TODO: use response codes instead of boolean
    public boolean coachSignIn(String username, String password) {
        try {
            this.currentCoach = this.placeholderFunctionalityProvider.getCoach(username, password);
        } catch(IllegalArgumentException e) {
            // TODO: Send the error message
            return false;
        }
        return true;
    }
    /*
     *  Member functionality
     */
    // TODO: use response codes instead of boolean
    public boolean addMember(String firstName, String lastName, Date dateOfBirth, String cprNumber, ZonedDateTime dateOfRegistration) {
        // 1. check for duplicate   placeholder functionality -> getMember(...data);
        // 2. if unique, add the new member, error otherwise
        boolean hasMember = this.placeholderFunctionalityProvider.getMember(cprNumber);
        if(hasMember) {
            return false;
        }
        Member member = new Member(firstName, lastName, dateOfBirth, cprNumber, dateOfRegistration);
        // member.registerPayment(new Payment(1005.0, "Membership fee", ZonedDateTime.now(ZoneOffset.UTC)));
        System.out.println(member.hasLatePayment().success + " " + member.hasLatePayment().info);
        this.placeholderFunctionalityProvider.setMember(member);

        return true;
    }


    protected Member getMember(String id) {
        // placeholder functionality -> getMember(...id);
        // ?id
        ZonedDateTime dateOfRegistrationUTC = ZonedDateTime.now(ZoneOffset.UTC);
        return new Member("test", "test", new Date(), "test", dateOfRegistrationUTC);
    }

    protected Member updateMember(String username, String password) {
        // placeholder functionality -> getMember(...id);
        // ?id
        ZonedDateTime dateOfRegistrationUTC = ZonedDateTime.now(ZoneOffset.UTC);
        return new Member("test", "test", new Date(), "test", dateOfRegistrationUTC);
    }

    public ArrayList<Member> getMembers() {
        return this.placeholderFunctionalityProvider.getMemberList();
    }

    public List<Discount> getDiscounts() {
        return this.discounts;
    }

    public ArrayList<Discipline> getDisciplines() {
        return this.disciplines;
    }
    public ArrayList<Competition> getCompetitions(){
        return this.competitions;
    }

}

