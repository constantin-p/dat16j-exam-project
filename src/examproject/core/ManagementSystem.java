package examproject.core;

import java.util.Date;

public class ManagementSystem {

    private PlaceholderFunctionalityProvider placeholderFunctionalityProvider;
    private Chairman currentChairman;

    public ManagementSystem() {
        this.placeholderFunctionalityProvider = new PlaceholderFunctionalityProvider();
    }

    // TODO: use response codes instead of boolean
    public boolean chairmanSignIn(String username, String password) {
        try {
            this.currentChairman = this.placeholderFunctionalityProvider.getChairman(username, password);
        } catch(IllegalArgumentException e) {
            // TODO: Send the error message
            // System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    // TODO: use response codes instead of boolean
    protected boolean treasurerSignIn(String username, String password) {
        // placeholder functionality -> signIn('treasurer', username, password);
        return false;
    }

    /*
        Member functionality
     */
    // TODO: use response codes instead of boolean
    protected boolean addMember(String firstName, String lastName, Date dateOfBirth) {
        // 1. check for duplicate   placeholder functionality -> getMember(...data);
        // 2. if unique, add the new member, error otherwise

        return false;
    }

    protected Member getMember(String id) {
        // placeholder functionality -> getMember(...id);
        // ?id

        return new Member();
    }

    protected Member updateMember(String username, String password) {
        // placeholder functionality -> getMember(...id);
        // ?id

        return new Member();
    }
}
