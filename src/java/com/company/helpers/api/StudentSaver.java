package com.company.helpers.api;

import com.company.domain.Student;

import java.io.File;
import java.util.List;

/**
 * Created by alek.aleksanyan on 1/27/2017.
 */
public interface StudentSaver {
    boolean save(List<Student> students, File outputFile);
}
