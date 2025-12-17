package model;

public class DormStats {
    private int budget;       // 0..2000
    private int happiness;    // 0..100
    private int discipline;   // 0..100
    private int cleanliness;  // 0..100
    private int safety;       // 0..100

    public DormStats(int budget, int happiness, int discipline, int cleanliness, int safety) {
        this.budget = budget;
        this.happiness = happiness;
        this.discipline = discipline;
        this.cleanliness = cleanliness;
        this.safety = safety;
        clampAll();
    }

    public int getBudget() { return budget; }
    public int getHappiness() { return happiness; }
    public int getDiscipline() { return discipline; }
    public int getCleanliness() { return cleanliness; }
    public int getSafety() { return safety; }

    public void applyImpact(int dBudget, int dHappiness, int dDiscipline, int dCleanliness, int dSafety) {
        budget += dBudget;
        happiness += dHappiness;
        discipline += dDiscipline;
        cleanliness += dCleanliness;
        safety += dSafety;
        clampAll();
    }

    private void clampAll() {
        budget = clamp(budget, 0, 2000);
        happiness = clamp(happiness, 0, 100);
        discipline = clamp(discipline, 0, 100);
        cleanliness = clamp(cleanliness, 0, 100);
        safety = clamp(safety, 0, 100);
    }

    private int clamp(int v, int min, int max) {
        return Math.max(min, Math.min(max, v));
    }

    public int score() {
        // Basit dönem puanı: dengeli başarıyı ödüllendir
        return (happiness + discipline + cleanliness + safety) / 4;
    }

    @Override
    public String toString() {
        return "Budget=" + budget +
                ", Happiness=" + happiness +
                ", Discipline=" + discipline +
                ", Cleanliness=" + cleanliness +
                ", Safety=" + safety +
                ", Score=" + score();
    }
}
