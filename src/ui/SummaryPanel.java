package ui;

import engine.GameEngine;

import javax.swing.*;
import java.awt.*;

public class SummaryPanel extends JPanel {

    private final GameEngine engine;
    private final JTextArea area = new JTextArea();

    public SummaryPanel(GameEngine engine, MainFrame frame) {
        this.engine = engine;

        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

        JLabel title = new JLabel("Dorm Summary");
        title.setFont(new Font("Arial", Font.BOLD, 18));

        area.setEditable(false);
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        area.setFont(new Font("Consolas", Font.PLAIN, 13));

        JButton back = new JButton("← Back to Game");
        back.addActionListener(e -> frame.showScreen(MainFrame.SCREEN_GAME));

        add(title, BorderLayout.NORTH);
        add(new JScrollPane(area), BorderLayout.CENTER);
        add(back, BorderLayout.SOUTH);

        refresh();
    }

    public void refresh() {
        var dorm = engine.getDorm();
        var sb = new StringBuilder();
        sb.append(dorm.summary()).append("\n\n");

        sb.append("Students:\n");
        dorm.getStudents().forEach(s -> sb.append(" - ").append(s).append("\n"));

        sb.append("\nStaff:\n");
        dorm.getStaff().forEach(st -> sb.append(" - ").append(st).append("\n"));

        area.setText(sb.toString());
        area.setCaretPosition(0);
    }
}
