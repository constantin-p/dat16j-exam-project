package examproject.core;

import java.util.Date;

public class ManagementSystem {

    private PlaceholderFunctionalityProvider placeholderFunctionalityProvider;
    private Chairman currentChairman;
    private Treasurer currentTreasurer;
    private Coach currentCoach;

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

    /*
     *  Treasurer Functionality
     */
    // TODO: use response codes instead of boolean
    public boolean treasurerSignIn(String username, String password) {
        try {
            this.currentTreasurer = this.placeholderFunctionalityProvider.getTreasurer(username, password);
        } catch(IllegalArgumentException e) {
            // TODO: Send the error message
            return false;
        }
        return true;
    }
    
    /*
     *  Coach Functionality
     */
    // TODO: use response codes instead of boolean
    public boolean coachSignIn(String username, String password) {
        try {
            this.currentCoach = this.placeholderFunctionalityProvider.getCoach(username, password);
        } catch(IllegalArgumentException e) {
            // TODO: Send the error message
            return false;
        }
        return true;
    }
    /*
        Member functionality
     */
    // TODO: use response codes instead of boolean
    public boolean addMember(String firstName, String lastName, Date dateOfBirth,String cprNumber) {
        // 1. check for duplicate   placeholder functionality -> getMember(...data);
        // 2. if unique, add the new member, error otherwise
         boolean hasMember = this.placeholderFunctionalityProvider.getMember(cprNumber);
        if(hasMember) {
            return false;
        }
        this.placeholderFunctionalityProvider.setMember(new Member(firstName, lastName, dateOfBirth, cprNumber));
        return true;


    }


    protected Member getMember(String id) {
        // placeholder functionality -> getMember(...id);
        // ?id

        return new Member("test", "test", new Date(), "test");
    }

    protected Member updateMember(String username, String password) {
        // placeholder functionality -> getMember(...id);
        // ?id

        return new Member("test", "test", new Date(), "test");
    }
}
