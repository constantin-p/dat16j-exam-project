package examproject.core;

import java.util.Date;

public class Chairman {

  private  String username;
  private  String password;

  public Chairman (String username, String password) {
      this.username = username;
      this.password = password;
  }

  public String getUserName() {
        return username;
  }

  public boolean addMember(String firstName, String lastName, Date dateOfBirth, String preferredActivity) {
      return false;
  }

}
