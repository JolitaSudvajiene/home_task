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
        //Inicializuoja studentu atsakymu recordus
        ExamsResults studentScores = new ExamsResults(new ArrayList<>());
        //stream kiekvieno studento rezultata ima ir atsakymus lygina su teisingais atsakymais
        studentResults.results().stream()
                .forEach(result -> {
                    //pasiima studento atsakymus - i lista
                    List<StudentAnswer> studentAnswers = result.answers();
                    //palygina atsakymus su teisingais ir suskaiciuoja teisingu ats kieki
                    int score = (int) IntStream.range(0, studentAnswers.size())
                            .filter(i -> studentAnswers.get(i).answer().equalsIgnoreCase(correctAnswers.answers().get(i).answer()))
                            .count();
                    //iskviecia metoda kuris ivertina rezultata ir suskaiciuoja pazymi
                    int grade = gradeExam(studentAnswers, score);
                    //kuria nauja irasa rezultatu ir prideda egzamino informacija bei studento info ir pazymy
                    studentScores.results().add(new ExamResult(result.studentExam().id(), result.studentExam().test_title(),
                            result.studentExam().type(), new ExamResultStudent(result.student().id(), result.student().name(),
                            result.student().surname(), grade)));
                });
        return studentScores;
    }


    //su bufferedWriter iraso i faila, taip pat cia panadojama data failo pavadinime todel failo pavadinimas nesikartoja
    public void writeStudentScoresToJson(ExamsResults studentScores) {
        ObjectMapper objectMapper = new ObjectMapper();
        //ijungiamas formatavimas failo kurimui - space'ai json faile
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

        //panaudoja LocalDateTime, now() metodas uzfiksuoja dabartini laika
        LocalDateTime now = LocalDateTime.now();
        //nustatomas datos formatas
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
        //suformatuojama data
        String timestamp = now.format(formatter);

        //Failo pavadinimas
        String filePath = "student_scores_" + timestamp + ".json";

        //Exception hadling'as
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filePath))) {
            objectMapper.writeValue(bufferedWriter, studentScores);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //suskaiciuoja pazymy pagal formule
    //integeri castinam i double ir tada atagl i integer ir apvalinam
    public int gradeExam(List<StudentAnswer> studentAnswers, int correctAnswers) {
        double examScore = (double) (correctAnswers * 10) / studentAnswers.size();
        return (int) Math.round(examScore);
    }
}
