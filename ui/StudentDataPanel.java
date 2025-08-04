package ui;

import service.StudentService;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StudentDataPanel extends JPanel {
    private StudentService studentService;

    public StudentDataPanel() {
        this.studentService = new StudentService();
        setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));

        JButton exportButton = new JButton("Export Students to CSV");
        exportButton.setFont(new Font("Arial", Font.BOLD, 16));
        exportButton.setPreferredSize(new Dimension(250, 50));
        
        JButton importButton = new JButton("Import Students from CSV");
        importButton.setFont(new Font("Arial", Font.BOLD, 16));
        importButton.setPreferredSize(new Dimension(250, 50));

        add(exportButton);
        add(importButton);

        exportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Export Students");
                fileChooser.setFileFilter(new FileNameExtensionFilter("CSV Files (*.csv)", "csv"));
                int userSelection = fileChooser.showSaveDialog(StudentDataPanel.this);

                if (userSelection == JFileChooser.APPROVE_OPTION) {
                    String filePath = fileChooser.getSelectedFile().getAbsolutePath();
                    if (!filePath.endsWith(".csv")) {
                        filePath += ".csv";
                    }
                    studentService.exportStudentsToCsv(filePath);
                    JOptionPane.showMessageDialog(StudentDataPanel.this, "Students exported successfully!", "Export Complete", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        importButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Import Students");
                fileChooser.setFileFilter(new FileNameExtensionFilter("CSV Files (*.csv)", "csv"));
                int userSelection = fileChooser.showOpenDialog(StudentDataPanel.this);

                if (userSelection == JFileChooser.APPROVE_OPTION) {
                    String filePath = fileChooser.getSelectedFile().getAbsolutePath();
                    studentService.importStudentsFromCsv(filePath);
                    JOptionPane.showMessageDialog(StudentDataPanel.this, "Students imported successfully!", "Import Complete", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
    }
}