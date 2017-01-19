package com.company.controllers;

import com.company.Main;
import com.company.domain.Student;
import com.company.exeptions.AgeExeption;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

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

    @FXML
    private TextField colName;
    @FXML
    private TextField colAge;
    @FXML
    private TextField colCourse;
    @FXML
    private Label labelForName;
    @FXML
    private Label labelForAge;
    @FXML
    private Label labelForCourse;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        labelForName.visibleProperty().bind(isValidName.not());
        labelForAge.visibleProperty().bind(isValidAge.not());
        labelForCourse.visibleProperty().bind(isValidCourse.not());
    }

    @FXML
    public void okAdd(ActionEvent actionEvent) throws AgeExeption {
        if (isValid()) {
            Student student = new Student();
            student.setName(colName.getText());
            student.setAge(Integer.valueOf(colAge.getText()));
            student.setCourse(colCourse.getText());
            okConsumer.accept(student);
            ((Node) (actionEvent.getSource())).getScene().getWindow().hide();
        }
    }

    private boolean isValid() {
        Pattern p;
        Matcher m;
        if (colName.getText() != null) {
            p = Pattern.compile("^[a-zA-Z -]{1,50}$");
            m = p.matcher(colName.getText());
            isValidName.setValue(m.matches());
        }
        if (colAge.getText() != null) {
            p = Pattern.compile("^[0-9]{1,50}$");
            m = p.matcher(colAge.getText());
            isValidAge.setValue( m.matches());
        }
        if (colCourse.getText() != null) {
            p = Pattern.compile("^[a-zA-Z0-9 -]{1,50}$");
            m = p.matcher(colCourse.getText());
            isValidCourse.setValue( m.matches());
        }
        return (isValidName.getValue()&& isValidAge.getValue() && isValidCourse.getValue());
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
}
