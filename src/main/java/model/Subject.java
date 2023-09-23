package model;

public class Subject {
    public String teacher;
    public int minScore;
    public boolean local = true;

    public Subject(String teacher, int minScore) {
        this.teacher = teacher;
        this.minScore = minScore;
    }

    public Subject(String teacher, int minScore, boolean local) {
        this.teacher = teacher;
        this.minScore = minScore;
        this.local = local;
    }
}
