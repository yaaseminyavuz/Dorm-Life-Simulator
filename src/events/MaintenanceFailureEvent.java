package events;

import model.Dorm;
import policy.Policy;

import java.util.List;

public class MaintenanceFailureEvent extends DormEvent {
    @Override public String title() { return "Maintenance Failure"; }
    @Override public Severity severity() { return Severity.HIGH; }

    @Override public String description() {
        return "A water leak is reported in the bathroom corridor.";
    }

    @Override public List<String> choices() {
        return List.of(
                "Call repair immediately (-budget, +cleanliness).",
                "Temporary fix with staff (small cost, small effect).",
                "Delay (cheap now, worse later)."
        );
    }

    @Override
    public String resolve(Dorm dorm, Policy policy, int choiceIndex) {
        switch (choiceIndex) {
            case 0 -> {
                dorm.getStats().applyImpact(-150, +2, 0, +10, +1);
                return "Repair team fixed it quickly. Cleanliness recovers.";
            }
            case 1 -> {
                dorm.getStats().applyImpact(-60, 0, +2 * policy.disciplineFactor(), +4, 0);
                return "Staff applied a temporary fix. Not perfect, but it helps.";
            }
            case 2 -> {
                dorm.getStats().applyImpact(0, -6 * policy.happinessFactor(), -2, -10, -1);
                return "You delayed repairs. The leak spreads; morale drops.";
            }
            default -> {
                return "No action taken.";
            }
        }
    }
}
