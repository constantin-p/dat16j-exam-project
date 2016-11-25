package examproject.core;

public class Payment {

    private double total;
    private double paid;
    private String details;

    public Payment(double total, double paid, String details) {
        this.total = total;
        this.paid = paid;
        this.details = details;
    }
}
