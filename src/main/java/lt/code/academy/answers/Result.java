package lt.code.academy.answers;

import java.util.List;
//saugo vieno studento informacija ir pateiktus atsakymus
public record Result(Student student, StudentExam studentExam, List<StudentAnswer> answers) {
}
