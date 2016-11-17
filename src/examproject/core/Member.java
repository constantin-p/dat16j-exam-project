package examproject.core;

import java.util.Date;

public class Member {

    public String firstName;
    public String lastName;
    public String preferredActivity;
    public Date dateOfBirth;
    public String cprNumber;

    public Member(String firstName, String lastName, Date dateOfBirth, String cprNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.cprNumber = cprNumber;
    }
}