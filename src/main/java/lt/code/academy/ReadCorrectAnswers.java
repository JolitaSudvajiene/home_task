package lt.code.academy;

import com.fasterxml.jackson.databind.ObjectMapper;
import lt.code.academy.exam.ExamData;

import java.io.File;
import java.io.IOException;

public class ReadCorrectAnswers {

    ExamData correctAnswers;

    //nuskaito answers.json faila ir deserializuoja pagal ExamData recorda
    public ReadCorrectAnswers() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        correctAnswers = objectMapper.readValue(new File("answers.json"), ExamData.class);
    }

    //grazinta uzpildytus recordus, ju lista
    public ExamData getCorrectAnswers() {
        return correctAnswers;
    }
}
