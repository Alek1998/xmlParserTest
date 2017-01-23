package com.company.controllers;

import com.company.domain.Student;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Created by alek.aleksanyan on 1/20/2017.
 */
public class StudentsSearchController implements Initializable {
    private final ListProperty<Student> searchStudents = new SimpleListProperty<>(FXCollections.observableArrayList());
    private BooleanProperty isValidName = new SimpleBooleanProperty(true);
    private BooleanProperty isValidAge = new SimpleBooleanProperty(true);
    private BooleanProperty isValidCourse = new SimpleBooleanProperty(true);

    @FXML
    private CheckBox checkBoxName;
    @FXML
    private CheckBox checkBoxAge;
    @FXML
    private CheckBox checkBoxCourse;
    @FXML
    private TextField textFieldName;
    @FXML
    private TextField textFieldAge;
    @FXML
    private TextField textFieldCourse;
    @FXML
    private CheckBox checkBoxAgeSmall;
    @FXML
    private CheckBox checkBoxAgeEqual;
    @FXML
    private CheckBox checkBoxAgeBig;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        textFieldName.disableProperty().bind(checkBoxName.selectedProperty().not());
        textFieldAge.disableProperty().bind(checkBoxAge.selectedProperty().not());
        textFieldCourse.disableProperty().bind(checkBoxCourse.selectedProperty().not());
        checkBoxAgeSmall.disableProperty().bind(checkBoxAge.selectedProperty().not());
        checkBoxAgeEqual.disableProperty().bind(checkBoxAge.selectedProperty().not());
        checkBoxAgeBig.disableProperty().bind(checkBoxAge.selectedProperty().not());
    }


    public ObservableList<Student> getSearchStudents() {
        return searchStudents.get();
    }

    public ListProperty<Student> searchStudentsProperty() {
        return searchStudents;
    }

    public void setSearchStudents(ObservableList<Student> searchForStudent) {
        this.searchStudents.set(searchForStudent);
    }

    public void exitApp(ActionEvent actionEvent) {
        ((Node) (actionEvent.getSource())).getScene().getWindow().hide();
    }

    public void okSearch(ActionEvent actionEvent) {
        if(isValid()){
        if (checkBoxName.isSelected()) {
            searchStudents.removeAll(searchStudents.stream().filter(s -> !s.getName().replaceAll("^\\s+", "").replaceAll("\\s+$", "").toLowerCase().contains(
                    textFieldName.getText().replaceAll("^\\s+", "").replaceAll("\\s+$", "").toLowerCase())).collect(Collectors.toList()));
        }
        if (checkBoxCourse.isSelected()) {
            searchStudents.removeAll(searchStudents.stream().filter(s -> !s.getCourse().replaceAll("^\\s+", "").replaceAll("\\s+$", "").toLowerCase().contains(
                    textFieldCourse.getText().replaceAll("^\\s+", "").replaceAll("\\s+$", "").toLowerCase())).collect(Collectors.toList()));
        }
        if (checkBoxAge.isSelected() &&checkBoxAgeSmall.isSelected()||checkBoxAgeBig.isSelected()||checkBoxAgeEqual.isSelected()) {
            Integer age = new Integer(Integer.valueOf(textFieldAge.getText()));
            searchStudents.removeAll(searchStudents.stream().filter(s-> !(
                            checkBoxAgeSmall.isSelected()&&s.getAge()<age||
                            checkBoxAgeEqual.isSelected()&&s.getAge()==age||
                            checkBoxAgeBig.isSelected()&&s.getAge()>age)
            ).collect(Collectors.toList()));
        }
            ((Node) (actionEvent.getSource())).getScene().getWindow().hide();
        }
    }
    private boolean isValid() {
//        Pattern p;
//        Matcher m;
//        if (textFieldName.getText() != null) {
//            p = Pattern.compile("^[a-zA-Z -]{1,50}$");
//            m = p.matcher(textFieldName.getText());
//            isValidName.setValue(m.matches());
//        }
//        if (textFieldAge.getText() != null) {
//            p = Pattern.compile("^[0-9]{1,50}$");
//            m = p.matcher(textFieldAge.getText());
//            isValidAge.setValue(m.matches());
//        }
//        if (textFieldCourse.getText() != null) {
//            p = Pattern.compile("^[a-zA-Z0-9 -]{1,50}$");
//            m = p.matcher(textFieldCourse.getText());
//            isValidCourse.setValue(m.matches());
//        }
//        return (isValidName.getValue() && isValidAge.getValue() && isValidCourse.getValue());
        return true;
    }
}
