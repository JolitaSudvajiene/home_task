package lt.code.academy;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lt.code.academy.answers.*;
import lt.code.academy.exam.ExamData;
import lt.code.academy.results.ExamResult;
import lt.code.academy.results.ExamResultStudent;
import lt.code.academy.results.ExamsResults;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class Grader {

    public ExamsResults compareAnswers(Results studentResults, ExamData correctAnswers) {
        ExamsResults studentScores = new ExamsResults(new ArrayList<>());

        //kiekvienam studento irasui- istraukiama studento info (23 eilute), tada istraukiami to studento atsakymai (24 eilute)
        // 25 eilute - paima irasu kieki ir kiekviena studento atsakyma tikrina su teisingu atsakymu is correctAnswers, kiek sutampa, deda i score
        studentResults.results().stream()
                .forEach(result -> {
                    List<StudentAnswer> studentAnswers = result.answers();
                    int score = (int) IntStream.range(0, studentAnswers.size())
                            .filter(i -> studentAnswers.get(i).answer().equalsIgnoreCase(correctAnswers.answers().get(i).answer()))
                            .count();
                    int grade = gradeExam(studentAnswers, score);
                            studentScores.results().add(new ExamResult(result.studentExam().id(), result.studentExam().test_title(),
                                    result.studentExam().type(), new ExamResultStudent(result.student().id(), result.student().name(),
                                    result.student().surname(), grade)));
                });
        return studentScores;
    }


    public void writeStudentScoresToJson(ExamsResults studentScores) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

        // Create a date stamp to use in the file name
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
        String timestamp = now.format(formatter);

        // Set the file path and name
        String filePath = "student_scores_" + timestamp + ".json";

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            objectMapper.writeValue(bw, studentScores);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int gradeExam(List<StudentAnswer> studentAnswers, int correctAnswers) {
        double examScore = (double) (correctAnswers * 10) / studentAnswers.size();
        return (int) Math.round(examScore);
    }
}
