package model;

public class DormStaff extends Person {
    private final String duty; // e.g., Security, Cleaner, Manager

    public DormStaff(String name, String duty) {
        super(name);
        this.duty = duty;
    }
    @Override
    public String toString() {
        return getName() + " [" + duty + "]";
    }


    public String getDuty() { return duty; }

    @Override
    public String role() {
        return "Staff(" + duty + ")";
    }
}
