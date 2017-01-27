package com.company.utils;

import com.company.domain.Student;
import com.company.enums.FileExtension;
import com.company.helpers.StudentDataProcessorProvider;

import java.io.File;
import java.util.List;

/**
 * Created by alek.aleksanyan on 1/17/2017.
 */
public final class StudentUtils {
    private StudentUtils() { /* NoInstance */ }

    public static List<Student> loadStudents(File sourceFile) {
        FileExtension fileExtension = FileExtension.getValueOf(FileUtils.getExtension(sourceFile));
        if (fileExtension != null) {
            return StudentDataProcessorProvider.getStudentDataProcessor(fileExtension).load(sourceFile);
        } else {
            throw new IllegalArgumentException(String.format("File type %s is not supported!", FileUtils.getExtension(sourceFile)));
        }
    }

    public static boolean saveStudents(List<Student> students, File outputFile) {
        FileExtension fileExtension = FileExtension.getValueOf(FileUtils.getExtension(outputFile));
        if (fileExtension != null) {
            return StudentDataProcessorProvider.getStudentDataProcessor(fileExtension).save(students, outputFile);
        } else {
            throw new IllegalArgumentException(String.format("File type %s is not supported!", FileUtils.getExtension(outputFile)));
        }
    }
}
