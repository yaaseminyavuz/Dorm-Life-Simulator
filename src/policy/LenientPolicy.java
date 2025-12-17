package policy;

public class LenientPolicy implements Policy {
    @Override public String name() { return "Lenient"; }
    @Override public int happinessFactor() { return 1; }   // mutluluk kaybı düşük
    @Override public int disciplineFactor() { return 1; }  // disiplin kazanımı düşük (event içinde ayarlarız)
}
