package lt.code.academy.exam;

import java.util.List;

public record ExamData(Exam exam, List<CorrectAnswer> answers) {}
