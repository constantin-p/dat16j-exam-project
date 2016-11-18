package examproject.core;

public interface Discount {

    public double getModifier();
    public boolean checkCondition(Member member);
    public String getType();
}


