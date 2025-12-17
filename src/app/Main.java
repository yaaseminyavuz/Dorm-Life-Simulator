package app;

import engine.GameEngine;
import model.Dorm;
import model.DormStats;
import model.DormStaff;
import model.Student;
import ui.MainFrame;

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {

        Dorm dorm = new Dorm("DormLife Simulator", new DormStats(900, 70, 60, 65, 55));
        dorm.addStudent(new Student("Yasemin", 101));
        dorm.addStudent(new Student("Elif", 102));
        dorm.addStudent(new Student("Mert", 103));
        dorm.addStaff(new DormStaff("Ayse", "Manager"));
        dorm.addStaff(new DormStaff("Kemal", "Security"));

        GameEngine engine = new GameEngine(dorm, 10);

        SwingUtilities.invokeLater(() -> {
            MainFrame frame = new MainFrame(engine);
            frame.setVisible(true);
        });
    }
}
