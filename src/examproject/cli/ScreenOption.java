package examproject.cli;

public class ScreenOption {

    public String label;
    public Runnable callback;

    public ScreenOption(String label, Runnable callback) {
        this.label = label;
        this.callback = callback;
    }

    // For debugging
    @Override
    public String toString() {
        return this.getClass().getCanonicalName()
                + "[label: " + this.label
                + ", callback: " + this.callback + "]";
    }
}
