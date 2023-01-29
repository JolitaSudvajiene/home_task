package lt.code.academy;

import com.fasterxml.jackson.databind.ObjectMapper;
import lt.code.academy.answers.Results;

import java.io.File;
import java.io.IOException;

public class ReadStudentsAnswers {

    Results studentsExamAnswers;

    public ReadStudentsAnswers() throws IOException {
        //inicializuojam ObjectMapper klase
        ObjectMapper objectMapper = new ObjectMapper();
        //nuskaitom studentu atsakymu json faila pagal recordus
        studentsExamAnswers = objectMapper.readValue(new File("students_test.json"), Results.class);
    }

    public Results getStudentsExamAnswers() {

        return studentsExamAnswers;
    }
}
