package examproject.core;

import examproject.db.Storable;

public interface Discount extends Storable {

    public double getModifier();
    public boolean checkCondition(Member member);
    public String getType();
}


