package ui;

import engine.GameEngine;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    public static final String SCREEN_GAME = "game";
    public static final String SCREEN_SUMMARY = "summary";

    private final CardLayout cardLayout = new CardLayout();
    private final JPanel root = new JPanel(cardLayout);

    private final GamePanel gamePanel;
    private final SummaryPanel summaryPanel;

    public MainFrame(GameEngine engine) {
        setTitle("DormLife Simulator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1120, 680);
        setLocationRelativeTo(null);

        // Basit modern hissiyat
        UIManager.put("Button.arc", 12);
        UIManager.put("Component.arc", 12);

        gamePanel = new GamePanel(engine, this);
        summaryPanel = new SummaryPanel(engine, this);

        root.add(gamePanel, SCREEN_GAME);
        root.add(summaryPanel, SCREEN_SUMMARY);

        setContentPane(root);
        showScreen(SCREEN_GAME);
    }

    public void showScreen(String screenName) {
        if (SCREEN_SUMMARY.equals(screenName)) {
            summaryPanel.refresh();
        }
        if (SCREEN_GAME.equals(screenName)) {
            gamePanel.refreshUI();
        }
        cardLayout.show(root, screenName);
    }
}
