package com.company.enums;

import javafx.stage.FileChooser;

import java.util.*;

/**
 * Created by alek.aleksanyan on 1/27/2017.
 */
public enum FileExtension {
    XML(".xml", new FileChooser.ExtensionFilter("XML files", "*.xml", "*.XML")),
    CSV(".csv", new FileChooser.ExtensionFilter("CSV files", "*.csv", "*.CSV"))
    ;

    private String extension;
    private final FileChooser.ExtensionFilter extensionFilter;

    private static final Map<String, FileExtension> _valueOfMap = new HashMap<>(values().length);
    static {
        for (FileExtension fileExtension : values()) {
            _valueOfMap.put(fileExtension.extension, fileExtension);
        }
    }

    FileExtension(String extension, FileChooser.ExtensionFilter extensionFilter) {
        this.extension = extension;
        this.extensionFilter = extensionFilter;
    }

    public String getExtension() {
        return extension;
    }

    public FileChooser.ExtensionFilter getExtensionFilter() {
        return extensionFilter;
    }

    public static List<FileExtension> valuesAsList() {
        return Arrays.asList(values());
    }

    public static FileExtension getValueOf(String extension) {
        return _valueOfMap.get(extension.toLowerCase());
    }
}
