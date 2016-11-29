package examproject.core;

import examproject.db.Storable;

import java.util.HashMap;

public class Discipline implements Storable {

    public String name;

    public Discipline(String name) {
        this.name = name;
    }

    /*
     *  DB integration
    */
    @Override
    public HashMap<String, String> deconstruct() {
        HashMap<String, String> values = new HashMap<String, String>();

        values.put("name", this.name);

        return values;
    }

    public static Discipline construct(HashMap<String, String> valuesMap) {
        String name = valuesMap.get("name");

        return new Discipline(name);
    }

    // For debugging
    @Override
    public String toString() {
        return this.getClass().getCanonicalName()
                + "[name: " + this.name + "]";
    }
}
