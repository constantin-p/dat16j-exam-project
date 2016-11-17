package examproject.core;

public class SeniorDiscount implements Discount {

    private double percent;

    public SeniorDiscount(double percent) {
        this.percent = percent;
    }

    public double getModifier() {
        return this.percent;
    }

    public String getType() {
        return "Senior Discount";
    }

    //TODO Use date to check condition if over 60
    public boolean checkCondition(Member member) {
        return true;
    }
}
