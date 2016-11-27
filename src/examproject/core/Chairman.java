package examproject.core;

import examproject.db.Storable;

import java.util.HashMap;

public class Chairman extends AuthAccount {

    public Chairman (String username, String password) {
        super(username, password);
    }

    public static Chairman construct(HashMap<String, String> valuesMap) {
        String username = valuesMap.get("username");
        String password = valuesMap.get("password");

        return new Chairman(username, password);
    }
}
