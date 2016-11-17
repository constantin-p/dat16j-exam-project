package examproject.core;

import java.util.ArrayList;
import java.util.Objects;

public class PlaceholderFunctionalityProvider {
    public static void testPrint() {
        System.out.println("Test from core");
    }

    public static Chairman getChairman(String username, String password) {
        if (Objects.equals(username, "chairman") && Objects.equals(password, "chairman")) {
            return new Chairman(username, password);
        } else {
            throw new IllegalArgumentException("No chairman with the given username & password!");
        }
    }

    public static ArrayList<Member> getMemberList() {
        ArrayList<Member> members = new ArrayList<Member>();

        // Dummy data
        members.add(new Member());
        members.add(new Member());
        members.add(new Member());
        members.add(new Member());

        return members;
    }
}
