package com.company.helpers;

import com.company.enums.FileExtension;
import com.company.helpers.api.StudentDataProcessor;

/**
 * Created by alek.aleksanyan on 1/27/2017.
 */
public final class StudentDataProcessorProvider {
    private StudentDataProcessorProvider() { /* NoInstance */ }

    public static StudentDataProcessor getStudentDataProcessor(FileExtension fileExtension) {
        switch (fileExtension) {
            case CSV:
                return new StudentDataCSVProcessor();
            case XML:
                return new StudentDataXMLProcessor();
            default:
                return null;
        }
    }
}
