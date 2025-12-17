package policy;

public class BalancedPolicy implements Policy {
    @Override public String name() { return "Balanced"; }
    @Override public int happinessFactor() { return 1; }
    @Override public int disciplineFactor() { return 1; }
}
