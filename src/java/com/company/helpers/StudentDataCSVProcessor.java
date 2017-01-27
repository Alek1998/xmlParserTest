package com.company.helpers;

import com.company.domain.Student;
import com.company.helpers.api.StudentDataProcessor;
import com.company.helpers.api.StudentLoader;
import com.company.helpers.api.StudentSaver;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by alek.aleksanyan on 1/27/2017.
 */
public class StudentDataCSVProcessor implements StudentDataProcessor {
    @Override
    public boolean save(List<Student> students, File outputFile) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
            writer.write("id,name,age,course");
            writer.newLine();
            for (Student currentStudent : students) {
                StringBuilder builder = new StringBuilder();
                writer.write(
                        builder.append(currentStudent.getId()).
                                append(",").append(currentStudent.getName()).
                                append(",").append(currentStudent.getAge()).
                                append(",").append(currentStudent.getCourse()).
                                toString()
                );
                writer.newLine();
            }
        } catch (Exception ex) {
            return false;
        }
        return true;
    }

    @Override
    public List<Student> load(File sourceFile) {
        List<Student> students = new ArrayList<>();
        String line;
        String cvsSplitBy = ",";
        boolean firstIter = true;
        Map<String, Integer> map = new TreeMap<String, Integer>();
        try (BufferedReader br = new BufferedReader(new FileReader(sourceFile))) {
            while ((line = br.readLine()) != null) {
                String[] country = line.split(cvsSplitBy);
                if (firstIter) {
                    firstIter = false;
                    map.put(country[0].toLowerCase(), 0);
                    map.put(country[1].toLowerCase(), 1);
                    map.put(country[2].toLowerCase(), 2);
                    map.put(country[3].toLowerCase(), 3);
                } else {
                    students.add(
                            new Student(
                                    Integer.valueOf(country[map.get("id")]),
                                    country[map.get("name")],
                                    Integer.valueOf(country[map.get("age")]),
                                    country[map.get("course")]
                            )
                    );
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return students;
    }
}
