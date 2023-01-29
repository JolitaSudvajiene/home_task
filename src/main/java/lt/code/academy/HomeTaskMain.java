package lt.code.academy;

import lt.code.academy.answers.Results;
import lt.code.academy.exam.ExamData;
import lt.code.academy.results.ExamsResults;

import java.io.IOException;

public class HomeTaskMain {

    public static void main(String[] args) throws IOException {

        HomeTaskMain homeTaskMain = new HomeTaskMain();

        Results studentsAnswers = homeTaskMain.getStudentAnswers();

        ExamData correctExamAnswers = homeTaskMain.getCorrectExamAnswers();

        ExamsResults grade = homeTaskMain.getGrade(studentsAnswers, correctExamAnswers);
        homeTaskMain.writeResultsJson(grade);


    }

    private Results getStudentAnswers() throws IOException {
        //Nuskaito ReadStudentsAnswers klase, iskviesta per konstruktoriu
        ReadStudentsAnswers readStudentsAnswers = new ReadStudentsAnswers();
        return readStudentsAnswers.getStudentsExamAnswers();
    }

    private ExamData getCorrectExamAnswers() throws IOException {
        ReadCorrectAnswers readCorrectAnswers = new ReadCorrectAnswers();

        return readCorrectAnswers.getCorrectAnswers();
    }

    private ExamsResults getGrade(Results studentAnswers, ExamData correctExamAnswers) {
        Grader grader = new Grader();

        return grader.compareAnswers(studentAnswers, correctExamAnswers);
    }

    private void writeResultsJson(ExamsResults results) {
        Grader grader = new Grader();

        grader.writeStudentScoresToJson(results);
    }

}
