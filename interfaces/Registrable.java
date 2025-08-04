package interfaces;

import model.Student;
import java.util.List;

public interface Registrable {
 void addStudent(Student student);
 void updateStudent(Student student);
 void deleteStudent(int id);
 List<Student> getAllStudents();
}

