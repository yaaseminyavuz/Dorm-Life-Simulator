package model;

public class Student extends Person {
    private final int roomNo;

    public Student(String name, int roomNo) {
        super(name);
        this.roomNo = roomNo;
    }

    @Override
    public String toString() {
        return getName() + " (Room " + roomNo + ")";
    }


    public int getRoomNo() { return roomNo; }

    @Override
    public String role() {
        return "Student";
    }
}
