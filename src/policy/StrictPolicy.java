package policy;

public class StrictPolicy implements Policy {
    @Override public String name() { return "Strict"; }
    @Override public int happinessFactor() { return 2; }    // mutluluk kaybı daha büyük
    @Override public int disciplineFactor() { return 2; }   // disiplin artışı daha büyük
}
