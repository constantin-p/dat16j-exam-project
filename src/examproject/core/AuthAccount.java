package examproject.core;

import examproject.db.Storable;

import java.util.HashMap;

public class AuthAccount implements Storable {

    private String username;
    private String password;

    public AuthAccount (String username, String password) {
        this.username = username;
        this.password = password;
    }

    /*
     *  DB integration
    */
    @Override
    public HashMap<String, String> deconstruct() {
        HashMap<String, String> values = new HashMap<String, String>();

        values.put("username", this.username);
        values.put("password", this.password);

        return values;
    }

    public static AuthAccount construct(HashMap<String, String> valuesMap) {
        String username = valuesMap.get("username");
        String password = valuesMap.get("password");

        return new AuthAccount(username, password);
    }

    // For debugging
    @Override
    public String toString() {
        return this.getClass().getCanonicalName()
                + "[username: " + this.username
                + ", password: " + this.password + "]";
    }
}
