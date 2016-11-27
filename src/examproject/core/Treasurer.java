package examproject.core;

import examproject.db.Storable;

import java.util.HashMap;

public class Treasurer extends AuthAccount {

    public Treasurer (String username, String password) {
        super(username, password);
    }

    public static Storable construct(HashMap<String, String> valuesMap) {
        String username = valuesMap.get("username");
        String password = valuesMap.get("password");

        return new Treasurer(username, password);
    }
}
