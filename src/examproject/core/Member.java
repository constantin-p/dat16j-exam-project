package examproject.core;

import java.util.Date;

public class Member {

    public String firstName;
    public String lastName;
    public String preferredActivity;
    public Date dateOfBirth;

    public Member(String firstName, String lastName, Date dateOfBirth) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
    }
}