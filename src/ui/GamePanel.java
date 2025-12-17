package ui;

import engine.GameEngine;
import events.DormEvent;
import events.Severity;
import model.Student;
import policy.BalancedPolicy;
import policy.LenientPolicy;
import policy.StrictPolicy;

import javax.swing.*;
import java.awt.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class GamePanel extends JPanel {

    private final GameEngine engine;
    private final MainFrame frame;

    private final JLabel dayLabel = new JLabel();
    private final JLabel policyLabel = new JLabel();
    private final JLabel scoreLabel = new JLabel();
    private final JLabel budgetLabel = new JLabel();

    private final JLabel severityLabel = new JLabel(); // renkli badge

    private final JTextArea eventArea = new JTextArea();
    private final JTextArea logArea = new JTextArea();

    private final JButton c1 = new JButton();
    private final JButton c2 = new JButton();
    private final JButton c3 = new JButton();
    private final JButton nextDayBtn = new JButton("Next Day ▶");

    private DormEvent currentEvent;

    private final JProgressBar happinessBar = new JProgressBar(0, 100);
    private final JProgressBar disciplineBar = new JProgressBar(0, 100);
    private final JProgressBar cleanlinessBar = new JProgressBar(0, 100);
    private final JProgressBar safetyBar = new JProgressBar(0, 100);

    public GamePanel(GameEngine engine, MainFrame frame) {
        this.engine = engine;
        this.frame = frame;

        setLayout(new BorderLayout(12, 12));
        setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

        add(buildTop(), BorderLayout.NORTH);
        add(buildCenter(), BorderLayout.CENTER);
        add(buildBottom(), BorderLayout.SOUTH);

        setupTextAreasAndBars();
        setupButtons();

        refreshUI();
        appendLog("Game started.");
        loadNextEvent(); // burada severity renklenir
    }

    private JPanel buildTop() {
        JPanel top = new JPanel(new BorderLayout());

        // LEFT INFO BAR
        JPanel left = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));

        dayLabel.setFont(new Font("Arial", Font.BOLD, 13));

        severityLabel.setFont(new Font("Arial", Font.BOLD, 12));
        severityLabel.setOpaque(true); // ARKAPLAN RENGİ GÖRÜNSÜN DİYE ŞART
        severityLabel.setBorder(BorderFactory.createEmptyBorder(3, 10, 3, 10));

        left.add(dayLabel);
        left.add(new JLabel("|"));
        left.add(policyLabel);
        left.add(new JLabel("|"));
        left.add(scoreLabel);
        left.add(new JLabel("|"));
        left.add(budgetLabel);
        left.add(new JLabel("|"));
        left.add(severityLabel);

        // RIGHT CONTROLS
        JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));

        JComboBox<String> policyBox = new JComboBox<>(new String[]{"Strict", "Balanced", "Lenient"});
        policyBox.setSelectedItem(engine.getPolicy().name());
        policyBox.addActionListener(e -> {
            String p = (String) policyBox.getSelectedItem();
            if ("Strict".equals(p)) engine.setPolicy(new StrictPolicy());
            if ("Balanced".equals(p)) engine.setPolicy(new BalancedPolicy());
            if ("Lenient".equals(p)) engine.setPolicy(new LenientPolicy());
            refreshUI();
            appendLog("Policy changed to " + engine.getPolicy().name());
        });

        JButton summaryBtn = new JButton("Dorm Summary");
        summaryBtn.addActionListener(e -> frame.showScreen(MainFrame.SCREEN_SUMMARY));

        right.add(new JLabel("Policy:"));
        right.add(policyBox);
        right.add(summaryBtn);

        top.add(left, BorderLayout.WEST);
        top.add(right, BorderLayout.EAST);
        return top;
    }

    private JPanel buildCenter() {
        JPanel center = new JPanel(new GridLayout(1, 3, 12, 12));

        // EVENT CARD
        JPanel eventCard = new JPanel(new BorderLayout(8, 8));
        eventCard.setBorder(BorderFactory.createTitledBorder("Event Card"));
        eventCard.add(new JScrollPane(eventArea), BorderLayout.CENTER);

        // STATS CARD
        JPanel statsCard = new JPanel(new GridLayout(8, 1, 6, 6));
        statsCard.setBorder(BorderFactory.createTitledBorder("Dorm Stats"));
        statsCard.add(new JLabel("Happiness"));
        statsCard.add(happinessBar);
        statsCard.add(new JLabel("Discipline"));
        statsCard.add(disciplineBar);
        statsCard.add(new JLabel("Cleanliness"));
        statsCard.add(cleanlinessBar);
        statsCard.add(new JLabel("Safety"));
        statsCard.add(safetyBar);

        // LOG CARD
        JPanel logCard = new JPanel(new BorderLayout(6, 6));
        logCard.setBorder(BorderFactory.createTitledBorder("Event Log"));
        logCard.add(new JScrollPane(logArea), BorderLayout.CENTER);

        center.add(eventCard);
        center.add(statsCard);
        center.add(logCard);
        return center;
    }

    private JPanel buildBottom() {
        JPanel bottom = new JPanel(new BorderLayout(8, 8));

        JPanel choices = new JPanel(new GridLayout(1, 3, 8, 8));
        choices.add(c1);
        choices.add(c2);
        choices.add(c3);

        nextDayBtn.addActionListener(e -> {
            if (engine.isOver()) {
                showGameOver();
                return;
            }
            engine.endDay();
            refreshUI();
            loadNextEvent();
        });

        bottom.add(choices, BorderLayout.CENTER);
        bottom.add(nextDayBtn, BorderLayout.EAST);
        return bottom;
    }

    private void setupTextAreasAndBars() {
        eventArea.setEditable(false);
        eventArea.setLineWrap(true);
        eventArea.setWrapStyleWord(true);
        eventArea.setFont(new Font("Consolas", Font.PLAIN, 14));

        logArea.setEditable(false);
        logArea.setLineWrap(true);
        logArea.setWrapStyleWord(true);
        logArea.setFont(new Font("Consolas", Font.PLAIN, 12));

        for (JProgressBar b : new JProgressBar[]{happinessBar, disciplineBar, cleanlinessBar, safetyBar}) {
            b.setStringPainted(true);
        }
    }

    private void setupButtons() {
        c1.addActionListener(e -> applyChoice(0));
        c2.addActionListener(e -> applyChoice(1));
        c3.addActionListener(e -> applyChoice(2));
    }

    private String dormFlavorText() {
        Student s = engine.getDorm().randomStudent();
        if (s == null) return "";
        return "\n\nContext: Room " + s.getRoomNo() + " (" + s.getName() + ") is involved.";
    }

    private void loadNextEvent() {
        currentEvent = engine.nextDayEvent();

        // DEBUG: Konsolda severity ne geliyor gör (istersen sonra sil)
        System.out.println("DEBUG severity = " + currentEvent.severity());

        String text =
                "EVENT: " + currentEvent.title() +
                        "\nSEVERITY: " + currentEvent.severity() +
                        "\n\n" + currentEvent.description() +
                        dormFlavorText();

        eventArea.setText(text);

        List<String> ch = currentEvent.choices();
        c1.setText("A) " + ch.get(0));
        c2.setText("B) " + ch.get(1));
        c3.setText("C) " + ch.get(2));

        // ✅ RENK BURADA DEĞİŞİR
        updateSeverityBadge(currentEvent.severity());

        appendLog("New event: " + currentEvent.title() + " [" + currentEvent.severity() + "]");
    }

    private void applyChoice(int idx) {
        if (currentEvent == null) return;

        String outcome = currentEvent.resolve(engine.getDorm(), engine.getPolicy(), idx);
        refreshUI();

        appendLog("Choice " + (idx == 0 ? "A" : idx == 1 ? "B" : "C") + " → " + outcome);

        JOptionPane.showMessageDialog(this,
                outcome + "\n\nNow: " + engine.getDorm().getStats(),
                "Outcome", JOptionPane.INFORMATION_MESSAGE);

        if (engine.isOver()) {
            showGameOver();
        }
    }

    public void refreshUI() {
        dayLabel.setText("Day: " + engine.getDay() + "/" + engine.getMaxDays());
        policyLabel.setText("Policy: " + engine.getPolicy().name());
        scoreLabel.setText("Score: " + engine.getDorm().getStats().score());

        int budget = engine.getDorm().getStats().getBudget();
        budgetLabel.setText("Budget: " + budget);
        budgetLabel.setForeground(budget < 200 ? new Color(180, 0, 0) : Color.BLACK);

        var s = engine.getDorm().getStats();
        happinessBar.setValue(s.getHappiness());
        happinessBar.setString(String.valueOf(s.getHappiness()));
        disciplineBar.setValue(s.getDiscipline());
        disciplineBar.setString(String.valueOf(s.getDiscipline()));
        cleanlinessBar.setValue(s.getCleanliness());
        cleanlinessBar.setString(String.valueOf(s.getCleanliness()));
        safetyBar.setValue(s.getSafety());
        safetyBar.setString(String.valueOf(s.getSafety()));
    }

    private void updateSeverityBadge(Severity sev) {
        severityLabel.setText("SEV: " + sev);
        severityLabel.setOpaque(true); // tekrar garanti

        if (sev == Severity.LOW) {
            severityLabel.setBackground(new Color(210, 255, 210));   // yeşil
        } else if (sev == Severity.MEDIUM) {
            severityLabel.setBackground(new Color(255, 245, 190));   // sarı
        } else { // HIGH
            severityLabel.setBackground(new Color(255, 210, 210));   // kırmızı
        }

        // bazı Look&Feel'larda anlık boyanma için:
        severityLabel.repaint();
    }

    private void appendLog(String msg) {
        String t = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        logArea.append("[" + t + "] " + msg + "\n");
        logArea.setCaretPosition(logArea.getDocument().getLength());
    }

    private void showGameOver() {
        nextDayBtn.setEnabled(false);
        c1.setEnabled(false);
        c2.setEnabled(false);
        c3.setEnabled(false);

        int score = engine.getDorm().getStats().score();
        String rank = (score >= 85) ? "S-Rank Dorm Manager"
                : (score >= 70) ? "A-Rank"
                : (score >= 55) ? "B-Rank"
                : "Needs Improvement";

        JOptionPane.showMessageDialog(this,
                "=== GAME OVER ===\nFinal stats:\n" + engine.getDorm().getStats() + "\n\nRank: " + rank,
                "Game Over", JOptionPane.INFORMATION_MESSAGE);

        appendLog("GAME OVER. Final score: " + score + " (" + rank + ")");
    }
}
