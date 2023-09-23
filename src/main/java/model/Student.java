package model;

public class Student {
    public boolean canStudyLocally = true;
    public boolean canStudyDistantly = true;
    public int score;

    public Student(int score){
        this.score = score;
    }

    public Student(boolean canStudyLocally, boolean canStudyDistantly, int score) {
        this.canStudyLocally = canStudyLocally;
        this.canStudyDistantly = canStudyDistantly;
        this.score = score;
    }
}
