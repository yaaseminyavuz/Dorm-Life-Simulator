package policy;

public interface Policy {
    String name();

    // Policy event etkisini “çarpan” gibi ayarlasın (basit ve gösterişli)
    int happinessFactor();   // örn Strict: - etkileri büyütür
    int disciplineFactor();
}
