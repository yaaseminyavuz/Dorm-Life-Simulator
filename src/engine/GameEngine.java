package engine;

import events.DormEvent;
import model.Dorm;
import policy.BalancedPolicy;
import policy.Policy;

public class GameEngine {
    private final Dorm dorm;
    private final EventGenerator generator = new EventGenerator();
    private Policy policy = new BalancedPolicy();
    private int day = 1;
    private final int maxDays;

    public GameEngine(Dorm dorm, int maxDays) {
        this.dorm = dorm;
        this.maxDays = maxDays;
    }

    public int getDay() { return day; }
    public int getMaxDays() { return maxDays; }
    public Dorm getDorm() { return dorm; }
    public Policy getPolicy() { return policy; }
    public void setPolicy(Policy policy) { this.policy = policy; }

    public boolean isOver() {
        return day > maxDays || dorm.getStats().getBudget() <= 0 || dorm.getStats().score() <= 0;
    }

    public DormEvent nextDayEvent() {
        return generator.nextEvent();
    }

    public void endDay() {
        day++;
    }
}
