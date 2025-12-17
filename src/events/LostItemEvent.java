package events;

import model.Dorm;
import policy.Policy;

import java.util.List;

public class LostItemEvent extends DormEvent {

    @Override
    public String title() { return "Lost Item"; }

    @Override
    public Severity severity() { return Severity.LOW; }

    @Override
    public String description() {
        return "A student reports a lost item (keys / student card). Tension may rise if handled badly.";
    }

    @Override
    public List<String> choices() {
        return List.of(
                "Open a lost-and-found record (+discipline, small +happiness).",
                "Check security camera (if any) (-budget, +safety).",
                "Tell them to handle it themselves (-happiness)."
        );
    }

    @Override
    public String resolve(Dorm dorm, Policy policy, int choiceIndex) {
        // küçük event: etkiler küçük ama “yurt hissi” çok iyi
        switch (choiceIndex) {
            case 0 -> {
                dorm.getStats().applyImpact(0, +2, +4 * policy.disciplineFactor(), 0, 0);
                return "You created a lost-and-found record. The dorm feels more organized.";
            }
            case 1 -> {
                dorm.getStats().applyImpact(-40, +1, +1, 0, +4);
                return "You checked cameras and improved safety awareness. Small budget cost.";
            }
            case 2 -> {
                dorm.getStats().applyImpact(0, -3 * policy.happinessFactor(), -1, 0, 0);
                return "You refused to help. Student morale dropped slightly.";
            }
            default -> {
                return "No action taken.";
            }
        }
    }
}
