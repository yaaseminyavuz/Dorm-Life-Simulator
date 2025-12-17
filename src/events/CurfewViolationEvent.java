package events;

import model.Dorm;
import policy.Policy;

import java.util.List;

public class CurfewViolationEvent extends DormEvent {
    @Override public String title() { return "Curfew Violation"; }
    @Override public Severity severity() { return Severity.MEDIUM; }

    @Override public String description() {
        return "A student returned after curfew. Others are watching your response.";
    }

    @Override public List<String> choices() {
        return List.of(
                "Issue a warning.",
                "Apply a penalty (+discipline, -happiness).",
                "Let it slide (+happiness, -discipline)."
        );
    }

    @Override
    public String resolve(Dorm dorm, Policy policy, int choiceIndex) {
        switch (choiceIndex) {
            case 0 -> {
                dorm.getStats().applyImpact(0, -2 * policy.happinessFactor(), +5 * policy.disciplineFactor(), 0, 0);
                return "Warning issued. Clear message, moderate effect.";
            }
            case 1 -> {
                dorm.getStats().applyImpact(0, -6 * policy.happinessFactor(), +10 * policy.disciplineFactor(), 0, +1);
                return "Penalty applied. Discipline rises, but morale takes a hit.";
            }
            case 2 -> {
                dorm.getStats().applyImpact(0, +3, -8, 0, -1);
                return "You let it slide. Students feel relaxed, but rules weaken.";
            }
            default -> {
                return "No action taken.";
            }
        }
    }
}
