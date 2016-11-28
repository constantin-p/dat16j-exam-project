package examproject.core;
import examproject.db.Database;
import examproject.db.Storable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Competition implements Storable {

    public String name;
    public Discipline discipline;

    public Competition(String name, Discipline discipline) {
        this.name = name;
        this.discipline = discipline;
    }


    /*
     *  Members
     */
    public boolean hasMember(Member member) {
        List<Member> members = this.getMembers();
        for (int i = 0; i < members.size(); i++) {
            if (members.get(i).CPRNumber.equals(member.CPRNumber)) {
                return true;
            }
        }
        return false;
    }

    public void registerMember(Member member) {
        // The member already exists so create the junction with the member entry
        this.registerMemberToCompetition(member);
    }

    private void registerMemberToCompetition(Member member) {
        HashMap<String, String> competitionMemberJunction = new HashMap<String, String>();
        competitionMemberJunction.put("member_cpr_number", member.CPRNumber);
        competitionMemberJunction.put("competition_name", this.name);

        try {
            Database.getTable("member_competition").insert(competitionMemberJunction);
        } catch (IllegalArgumentException e) {
            // No table with the given name was found, create the table and insert the member_competition entry
            DBTables.createCompetitionMemberTable();
            Database.getTable("member_competition").insert(competitionMemberJunction);
        }
    }

    private List<Member> getMembers() {
        HashMap<String, String> searchQuery = new HashMap<String, String>();
        searchQuery.put("competition_name", this.name);

        List<HashMap<String, String>> entries;
        try {
            entries = Database.getTable("member_competition").getAll(searchQuery);
        } catch (IllegalArgumentException e) {
            // No table with the given name was found, create the table and search again
            DBTables.createCompetitionMemberTable();
            entries = Database.getTable("member_competition").getAll(searchQuery);
        }

        List<Member> members = new ArrayList<Member>();
        for (HashMap<String, String> entry : entries) {
            HashMap<String, String> memberSearchQuery = new HashMap<String, String>();
            memberSearchQuery.put("cpr_number", entry.get("member_cpr_number"));

            members.addAll(getMembersFromQuery(memberSearchQuery));
        }

        return members;
    }

    private List<Member> getMembersFromQuery(HashMap<String, String> searchQuery) {
        List<HashMap<String, String>> entries;
        try {
            entries = Database.getTable("members").getAll(searchQuery);
        } catch (IllegalArgumentException e) {
            // No table with the given name was found, create the table and search again
            DBTables.createMembersTable();
            entries = Database.getTable("members").getAll(searchQuery);
        }

        List<Member> members = new ArrayList<Member>();
        for (HashMap<String, String> entry : entries) {
            members.add(Member.construct(entry));
        }

        return members;
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
                + ", discipline: " + this.discipline + "]";
    }
}
