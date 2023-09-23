package model;

public class University {
    public static boolean canStudy(Student student, Subject subject){
        if (!checkLocation(student, subject)){
            return false;
        }
        if (subject.minScore > student.score){
            return false;
        }
        return true;
    }

    private static boolean checkLocation(Student student, Subject subject){
        return ((subject.local && student.canStudyLocally) || (!subject.local && student.canStudyDistantly));
    }
}
