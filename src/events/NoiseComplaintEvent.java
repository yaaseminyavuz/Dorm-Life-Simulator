package events;

import model.Dorm;
import policy.Policy;

import java.util.List;

public class NoiseComplaintEvent extends DormEvent {

    @Override
    public String title() { return "Noise Complaint"; }

    @Override
    public Severity severity() { return Severity.MEDIUM; }

    @Override
    public String description() {
        return "Late-night noise reported from a room. Other students can't sleep.";
    }

    @Override
    public List<String> choices() {
        return List.of(
                "Warn the room (no cost).",
                "Send security patrol (-budget, +safety).",
                "Ignore it (-happiness, -discipline)."
        );
    }

    @Override
    public String resolve(Dorm dorm, Policy policy, int choiceIndex) {
        switch (choiceIndex) {
            case 0 -> {
                dorm.getStats().applyImpact(0,
                        -5 * policy.happinessFactor(),
                        +6 * policy.disciplineFactor(),
                        0, 0);
                return "You issued a warning. Discipline improved, but some students are still annoyed.";
            }
            case 1 -> {
                dorm.getStats().applyImpact(-80,
                        -2 * policy.happinessFactor(),
                        +5 * policy.disciplineFactor(),
                        0, +6);
                return "Security patrolled the corridor. Safer dorm, small budget hit.";
            }
            case 2 -> {
                dorm.getStats().applyImpact(0,
                        -10 * policy.happinessFactor(),
                        -4,
                        0, -2);
                return "You ignored it. Students are upset and order declines.";
            }
            default -> {
                return "No action taken.";
            }
        }
    }
}
