package examproject.core;

import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

public class PlaceholderFunctionalityProvider {

    public Member currentMember;
    public static void testPrint() {
        System.out.println("Test from core");
    }


    public Chairman getChairman(String username, String password) {
        if (Objects.equals(username, "chairman") && Objects.equals(password, "chairman")) {
            return new Chairman(username, password);
        } else {
            throw new IllegalArgumentException("No chairman with the given username & password!");
        }
    }

    public static Treasurer getTreasurer(String username, String password) {
        if (Objects.equals(username, "treasurer") && Objects.equals(password, "treasurer")) {
            return new Treasurer(username, password);
        } else {
            throw new IllegalArgumentException("No treasurer with the given username & password!");
        }
    }

    public static Coach getCoach(String username, String password) {
        if (Objects.equals(username, "coach") && Objects.equals(password, "coach")) {
            return new Coach(username, password);
        } else {
            throw new IllegalArgumentException("No coach with the given username & password!");
        }
    }

    public void setMember(Member member){
        this.currentMember = member;

    }

    public boolean getMember(String cprNumber){
        return false;
    }

    public static ArrayList<Member> getMemberList() {
        ArrayList<Member> members = new ArrayList<Member>();

        // Dummy data
        members.add(new Member("Albert", "Taber", new Date(), "2303944734"));
        members.add(new Member("Leslie", "Shilling", new Date(), "1404936473"));
        members.add(new Member("Nolan", "Blane", new Date(), "0207894235"));
        members.add(new Member("Cassi", "Kleinman", new Date(), "2612786432"));

        return members;
    }

}
