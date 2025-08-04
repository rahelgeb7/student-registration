package util;

import model.Student;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CSVUtil {

    private static final String CSV_HEADER = "ID,Name,Mobile,Course";
    private static final String CSV_SEPARATOR = ",";

    // Writes a list of students to a CSV file
    public static boolean writeStudentsToCsv(List<Student> students, String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            // Write the header
            writer.write(CSV_HEADER);
            writer.newLine();

            // Write student data
            for (Student student : students) {
                writer.write(String.format("%d,%s,%s,%s",
                    student.getId(),
                    student.getName(),
                    student.getMobile(),
                    student.getCourse()
                ));
                writer.newLine();
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Reads students from a CSV file
    public static List<Student> readStudentsFromCsv(String filePath) {
        List<Student> students = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean isHeader = true;
            while ((line = reader.readLine()) != null) {
                if (isHeader) {
                    isHeader = false;
                    continue; // Skip the header line
                }

                String[] parts = line.split(CSV_SEPARATOR, -1);
                if (parts.length >= 4) {
                    // ID is ignored since the database will generate it on import
                    String name = parts[1].trim();
                    String mobile = parts[2].trim();
                    String course = parts[3].trim();
                    students.add(new Student(name, mobile, course));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return students;
    }
}