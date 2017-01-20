package com.company.controllers;

import com.company.domain.Student;
import com.company.exeptions.AgeExeption;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by alek.aleksanyan on 1/19/2017.
 */
public class StudentsController implements Initializable {
    private Consumer<Student> okConsumer;
    private BooleanProperty isValidName = new SimpleBooleanProperty(true);
    private BooleanProperty isValidAge = new SimpleBooleanProperty(true);
    private BooleanProperty isValidCourse = new SimpleBooleanProperty(true);
    private Student currentStudent;

    @FXML
    private TextField textFieldName;
    @FXML
    private TextField textFieldAge;
    @FXML
    private TextField textFieldCourse;
    @FXML
    private Label labelForName;
    @FXML
    private Label labelForAge;
    @FXML
    private Label labelForCourse;
    @FXML
    private Label lblId;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        labelForName.visibleProperty().bind(isValidName.not());
        labelForAge.visibleProperty().bind(isValidAge.not());
        labelForCourse.visibleProperty().bind(isValidCourse.not());
    }

    public void prepareForm() {
        lblId.setText(String.valueOf(currentStudent.getId()));
        textFieldName.setText(currentStudent.getName());
        textFieldAge.setText(String.valueOf(currentStudent.getAge()));
        textFieldCourse.setText(currentStudent.getCourse());
    }

    @FXML
    public void okAdd(ActionEvent actionEvent) throws AgeExeption {
        if (isValid()) {
            Student student = new Student();
            student.setName(textFieldName.getText());
            student.setAge(Integer.valueOf(textFieldAge.getText()));
            student.setCourse(textFieldCourse.getText());
            okConsumer.accept(student);
            ((Node) (actionEvent.getSource())).getScene().getWindow().hide();
        }
    }

    @FXML
    public void okEdit(ActionEvent actionEvent) throws AgeExeption {
        if (isValid()) {
            currentStudent.setName(textFieldName.getText());
            currentStudent.setAge(Integer.valueOf(textFieldAge.getText()));
            currentStudent.setCourse(textFieldCourse.getText());
            currentStudent.setId(Integer.valueOf(lblId.getText()));
            okConsumer.accept(currentStudent);
            ((Node) (actionEvent.getSource())).getScene().getWindow().hide();
        }
    }

    private boolean isValid() {
        Pattern p;
        Matcher m;
        if (textFieldName.getText() != null) {
            p = Pattern.compile("^[a-zA-Z -]{1,50}$");
            m = p.matcher(textFieldName.getText());
            isValidName.setValue(m.matches());
        }
        if (textFieldAge.getText() != null) {
            p = Pattern.compile("^[0-9]{1,50}$");
            m = p.matcher(textFieldAge.getText());
            isValidAge.setValue(m.matches());
        }
        if (textFieldCourse.getText() != null) {
            p = Pattern.compile("^[a-zA-Z0-9 -]{1,50}$");
            m = p.matcher(textFieldCourse.getText());
            isValidCourse.setValue(m.matches());
        }
        return (isValidName.getValue() && isValidAge.getValue() && isValidCourse.getValue());
    }

    @FXML
    public void exitApp(ActionEvent actionEvent) {
        ((Node) (actionEvent.getSource())).getScene().getWindow().hide();
    }

    public Consumer<Student> getOkConsumer() {
        return okConsumer;
    }

    public void setOkConsumer(Consumer<Student> okConsumer) {
        this.okConsumer = okConsumer;
    }

    public Student getCurrentStudent() {
        return currentStudent;
    }

    public void setCurrentStudent(Student currentStudent) {
        this.currentStudent = currentStudent;
    }

}
