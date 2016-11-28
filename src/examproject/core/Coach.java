package examproject.core;

import examproject.db.Database;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Coach extends AuthAccount {

    public Coach (String username, String password) {
        super(username, password);
    }

    /*
     *  Members
     */
    public void registerMember(Member member) {
        // The discount already exists so create the junction with the member entry
        this.registerMemberToCoach(member);
    }

    private void registerMemberToCoach(Member member) {
        HashMap<String, String> coachMemberJunction = new HashMap<String, String>();
        coachMemberJunction.put("member_cpr_number", member.CPRNumber);
        coachMemberJunction.put("coach_username", this.username);

        try {
            Database.getTable("member_coach").insert(coachMemberJunction);
        } catch (IllegalArgumentException e) {
            // No table with the given name was found, create the table and insert the payment_member entry
            DBTables.createCoachMemberTable();
            Database.getTable("member_coach").insert(coachMemberJunction);
        }
    }

    public List<Member> getMembers() {
        HashMap<String, String> searchQuery = new HashMap<String, String>();
        searchQuery.put("coach_username", this.username);

        List<HashMap<String, String>> entries;
        try {
            entries = Database.getTable("member_coach").getAll(searchQuery);
        } catch (IllegalArgumentException e) {
            // No table with the given name was found, create the table and search again
            DBTables.createCoachMemberTable();
            entries = Database.getTable("member_coach").getAll(searchQuery);
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
            DBTables.createCoachesTable();
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
    public static Coach construct(HashMap<String, String> valuesMap) {
        String username = valuesMap.get("username");
        String password = valuesMap.get("password");

        return new Coach(username, password);
    }
}