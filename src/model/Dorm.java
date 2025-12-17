package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Dorm {
    private final Random rnd = new Random();
    private final String name;
    private final DormStats stats;
    private final List<Student> students = new ArrayList<>();
    private final List<DormStaff> staff = new ArrayList<>();
    public Student randomStudent() {
        if (students.isEmpty()) return null;
        return students.get(rnd.nextInt(students.size()));
    }

    public boolean hasStaffDuty(String duty) {
        return staff.stream().anyMatch(s -> s.getDuty().equalsIgnoreCase(duty));
    }

    public Dorm(String name, DormStats stats) {
        this.name = name;
        this.stats = stats;
    }

    public String getName() { return name; }
    public DormStats getStats() { return stats; }

    public void addStudent(Student s) { students.add(s); }
    public void addStaff(DormStaff s) { staff.add(s); }

    public List<Student> getStudents() { return Collections.unmodifiableList(students); }
    public List<DormStaff> getStaff() { return Collections.unmodifiableList(staff); }

    public String summary() {
        return "Dorm: " + name + "\n" +
                "Students: " + students.size() + ", Staff: " + staff.size() + "\n" +
                stats.toString();
    }
}
