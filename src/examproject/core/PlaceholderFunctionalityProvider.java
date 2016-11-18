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

    public void setMember(Member member){
        this.currentMember = member;

    }

    public boolean getMember(String cprNumber){
        return false;
    }

    public static ArrayList<Member> getMemberList() {
        ArrayList<Member> members = new ArrayList<Member>();

        // Dummy data
        members.add(new Member("test", "test", new Date(), "test"));
        members.add(new Member("test", "test", new Date(), "test"));
        members.add(new Member("test", "test", new Date(), "test"));
        members.add(new Member("test", "test", new Date(), "test"));

        return members;
    }
}
