package events;

import model.Dorm;
import policy.Policy;

import java.util.List;

public class InternetOutageEvent extends DormEvent {

    @Override
    public String title() { return "Internet Outage"; }

    @Override
    public Severity severity() { return Severity.HIGH; }

    @Override
    public String description() {
        return "Dorm internet is down. Students complain because of homework and deadlines.";
    }

    @Override
    public List<String> choices() {
        return List.of(
                "Call ISP emergency support (-budget, +happiness).",
                "Temporary hotspot plan (-budget, small +happiness).",
                "Wait and do nothing (no cost, -happiness, -discipline)."
        );
    }

    @Override
    public String resolve(Dorm dorm, Policy policy, int choiceIndex) {
        switch (choiceIndex) {
            case 0 -> {
                dorm.getStats().applyImpact(-200, +8, +1 * policy.disciplineFactor(), 0, 0);
                return "Emergency support restored the internet quickly. Students are relieved.";
            }
            case 1 -> {
                dorm.getStats().applyImpact(-90, +3, 0, 0, 0);
                return "You provided temporary hotspots. Not perfect, but it saved the day.";
            }
            case 2 -> {
                dorm.getStats().applyImpact(0, -10 * policy.happinessFactor(), -3, 0, 0);
                return "You waited. Students got angry and productivity dropped.";
            }
            default -> {
                return "No action taken.";
            }
        }
    }
}
