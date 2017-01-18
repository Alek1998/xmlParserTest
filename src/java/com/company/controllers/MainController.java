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
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by alek.aleksanyan on 1/17/2017.
 */
public class MainController implements Initializable {
    private final ListProperty<Student> allStudents = new SimpleListProperty<>(FXCollections.observableArrayList());
    private final ListProperty<Student> studentsToPrint = new SimpleListProperty<>(FXCollections.observableArrayList());

    @FXML
    private VBox root;
    @FXML
    private MenuItem menuItemExitApp;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

//    private void blah() {
//        if(StudentUtils.listStudentToXml(allStudents,new File("C:\\Projects\\Homeworks\\xmlParserTest\\xmlParserTest\\outdata.xml"))){
//            System.out.println("normal");
//        }
//    }


    @FXML
    public void openFile(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        File file = fileChooser.showOpenDialog(root.getScene().getWindow());
        if (file != null) {
            this.allStudents.setAll(StudentUtils.xmlToStudentListParse(file));
        }
    }

    @FXML
    public void saveFileAs(ActionEvent actionEvent) {
     //   Main.exitApp();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        File file = fileChooser.showOpenDialog(root.getScene().getWindow());
        if(file!=null){
            StudentUtils.listStudentToXml(this.allStudents.get(),file);
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
}
