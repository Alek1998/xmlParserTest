package com.company.controllers;

import com.company.Main;
import com.company.domain.Student;
import com.company.utils.StudentUtils;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by alek.aleksanyan on 1/17/2017.
 */
public class MainController implements Initializable {
    private static final File USER_HOME_DIR = new File(System.getProperty("user.home"));
    private static final FileChooser.ExtensionFilter XML_EXTENSION_FILTER = new FileChooser.ExtensionFilter("XML files", "*.xml");
    private File openedFile;
    private AtomicInteger ID_GENERATOR = new AtomicInteger(0);
    private final ListProperty<Student> allStudents = new SimpleListProperty<>(FXCollections.observableArrayList());
    private final ListProperty<Student> studentsToSave = new SimpleListProperty<>(FXCollections.observableArrayList());

    @FXML
    private VBox root;
    @FXML
    private MenuItem menuItemExitApp;
    @FXML
    private TableView<Student> tableView;
    @FXML
    private TableColumn<Student, Integer> colId;
    @FXML
    private TableColumn<Student, String> colName;
    @FXML
    private TableColumn<Student, Integer> colAge;
    @FXML
    private TableColumn<Student, String> colCourse;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAge.setCellValueFactory(new PropertyValueFactory<>("age"));
        colCourse.setCellValueFactory(new PropertyValueFactory<>("course"));

        tableView.setItems(studentsToSave);
    }

    @FXML
    public void openFile(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(USER_HOME_DIR);
        fileChooser.getExtensionFilters().setAll(XML_EXTENSION_FILTER);
        openedFile = fileChooser.showOpenDialog(root.getScene().getWindow());
        if (openedFile != null) {
            this.allStudents.setAll(StudentUtils.xmlToStudentListParse(openedFile));
            this.studentsToSave.setAll(StudentUtils.xmlToStudentListParse(openedFile));
            ID_GENERATOR = new AtomicInteger(allStudents.size());
        }
    }

    @FXML
    public void saveFile(ActionEvent actionEvent) {
        if (studentsToSave.getSize() != allStudents.getSize() || studentsToSave.stream().anyMatch(student -> !allStudents.contains(student))) {
            if (openedFile != null) {
                StudentUtils.listStudentToXml(this.studentsToSave.get(), openedFile);
            }
        }
    }

    @FXML
    public void saveFileAs(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(USER_HOME_DIR);
        fileChooser.getExtensionFilters().setAll(XML_EXTENSION_FILTER);
        File newFile = fileChooser.showSaveDialog(root.getScene().getWindow());
        if (newFile != null) {
            StudentUtils.listStudentToXml(this.studentsToSave.get(), newFile);
        }
    }

    @FXML
    public void exitApp(ActionEvent actionEvent) {
        Main.exitApp();
    }

    private void dumpStudentsList(List<Student> students) {
        students.forEach(student -> {
            System.out.println("ID:" + student.getId());
            System.out.println("Name:" + student.getName());
            System.out.println("Age:" + student.getAge());
            System.out.println("Course:" + student.getCourse());
            System.out.println();
        });
    }

    @FXML
    public void addStudent(ActionEvent actionEvent) throws Exception {
        studentsToSave.add(new Student(ID_GENERATOR.incrementAndGet(), "",1, ""));
    }
}
