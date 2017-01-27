package com.company.utils;

import java.io.File;

/**
 * Created by alek.aleksanyan on 1/27/2017.
 */
public final class FileUtils {
    private FileUtils() { /* NoInstance */ }

    public static String getExtension(File file) {
        return file.getName().substring(file.getName().lastIndexOf('.'));
    }
}
