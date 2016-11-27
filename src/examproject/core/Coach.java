package examproject.core;

import examproject.db.Storable;

import java.util.HashMap;

public class Coach extends AuthAccount {

    public Coach (String username, String password) {
        super(username, password);
    }

    public static Coach construct(HashMap<String, String> valuesMap) {
        String username = valuesMap.get("username");
        String password = valuesMap.get("password");

        return new Coach(username, password);
    }
}