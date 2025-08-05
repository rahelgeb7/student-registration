package service;

import DAO.StudentDAO;
import interfaces.Registrable;
import model.Student;
import util.CSVUtil;

import java.util.List;

public class StudentService implements Registrable {
    private StudentDAO dao = new StudentDAO();
    
    public StudentService() {
        this.dao = new StudentDAO();
    }

    @Override
    public void addStudent(Student student) {
        try {
            dao.addStudent(student);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateStudent(Student student) {
        try {
            dao.updateStudent(student);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteStudent(int id) {
        try {
            dao.deleteStudent(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Student> getAllStudents() {
        try {
            return dao.getAllStudents();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void exportStudentsToCsv(String filePath) {
        try {
            List<Student> students = dao.getAllStudents();
            boolean success = CSVUtil.writeStudentsToCsv(students, filePath);
            if (!success) {
                System.err.println("Failed to export students to CSV.");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void importStudentsFromCsv(String filePath) {
        try {
            List<Student> students = CSVUtil.readStudentsFromCsv(filePath);
            if (!students.isEmpty()) {
                dao.addMultipleStudents(students);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}