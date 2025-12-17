package events;

import model.Dorm;
import policy.Policy;

import java.util.List;

public class FireDrillEvent extends DormEvent {
    @Override public String title() { return "Fire Drill"; }
    @Override public Severity severity() { return Severity.LOW; }

    @Override public String description() {
        return "You can run a fire drill today. It costs some budget but improves safety.";
    }

    @Override public List<String> choices() {
        return List.of(
                "Run a full drill (-budget, +safety).",
                "Run a short drill (medium effect).",
                "Skip (no cost)."
        );
    }

    @Override
    public String resolve(Dorm dorm, Policy policy, int choiceIndex) {
        switch (choiceIndex) {
            case 0 -> {
                dorm.getStats().applyImpact(-120, -2, +2 * policy.disciplineFactor(), 0, +12);
                return "Full drill completed. Safety increases significantly.";
            }
            case 1 -> {
                dorm.getStats().applyImpact(-60, -1, +1 * policy.disciplineFactor(), 0, +7);
                return "Short drill done. A nice safety boost.";
            }
            case 2 -> {
                dorm.getStats().applyImpact(0, 0, -2, 0, -4);
                return "You skipped it. No cost, but safety habits weaken.";
            }
            default -> {
                return "No action taken.";
            }
        }
    }
}
