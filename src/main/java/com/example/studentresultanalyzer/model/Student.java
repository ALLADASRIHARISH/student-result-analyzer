package com.example.studentresultanalyzer.model;

import java.util.ArrayList;
import java.util.List;

public class Student {
    private String name;
    private List<Double> marks = new ArrayList<>();

    public Student(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public List<Double> getMarks() {
        return marks;
    }

    public double getTotal() {
        return marks.stream().mapToDouble(Double::doubleValue).sum();
    }

    public double getAverage() {
        return marks.isEmpty() ? 0 : getTotal() / marks.size();
    }

    public String getGrade() {
        double avg = getAverage();
        if (avg >= 90) return "A+";
        else if (avg >= 80) return "A";
        else if (avg >= 70) return "B";
        else if (avg >= 60) return "C";
        else if (avg >= 50) return "D";
        else return "F";
    }

    public boolean isPass() {
        return marks.stream().allMatch(m -> m >= 35);
    }
}
