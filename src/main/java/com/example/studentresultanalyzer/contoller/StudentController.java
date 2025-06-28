package com.example.studentresultanalyzer.controller;

import com.example.studentresultanalyzer.model.Student;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.*;

@Controller
public class StudentController {

    // Load the upload form when user visits root URL
    @GetMapping("/")
    public String uploadForm() {
        return "upload"; // this will render upload.html
    }

    // Handle form submission and Excel processing
    @PostMapping("/analyze")
    public String analyzeExcel(@RequestParam("file") MultipartFile file, Model model) {
        List<Student> students = new ArrayList<>();

        try {
            InputStream inputStream = file.getInputStream();
            Workbook workbook = WorkbookFactory.create(inputStream);
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rows = sheet.iterator();

            // Skip header row
            if (rows.hasNext()) rows.next();

            while (rows.hasNext()) {
                Row row = rows.next();

                // First column = name
                String name = row.getCell(0).getStringCellValue();
                Student student = new Student(name);

                // Next columns = marks
                for (int i = 1; i < row.getLastCellNum(); i++) {
                    Cell cell = row.getCell(i);
                    double mark = cell.getNumericCellValue();
                    student.getMarks().add(mark);
                }

                students.add(student);
            }

            workbook.close();
        } catch (Exception e) {
            model.addAttribute("error", "Error reading Excel file: " + e.getMessage());
            return "upload";
        }

        // Pass list of students to result.html
        model.addAttribute("students", students);
        return "result";
    }
}
