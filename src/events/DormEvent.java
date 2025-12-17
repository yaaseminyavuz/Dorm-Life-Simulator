package events;

import model.Dorm;
import policy.Policy;

import java.util.List;

public abstract class DormEvent {
    public abstract String title();
    public abstract Severity severity();
    public abstract String description();
    public abstract List<String> choices();

    // choiceIndex: 0..n-1
    public abstract String resolve(Dorm dorm, Policy policy, int choiceIndex);
}
