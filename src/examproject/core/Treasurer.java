package examproject.core;

public class Treasurer {

    private String username;
    private String password;

    public Treasurer(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    //TODO: add member as arg
    public void  applyDiscount(double x) {

    }

    public void showMemberWithLatePayments() {

    }
}
