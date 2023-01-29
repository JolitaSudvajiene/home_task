package lt.code.academy.answers;

import java.util.List;

//cia auksciausias json objektas, po juo listinami rezultatai:
// Studentas <Student>, egzaminas <StudentExam>, studento atsakymai List<StudentAnswer>
public record Results(List<Result> results) {
}
