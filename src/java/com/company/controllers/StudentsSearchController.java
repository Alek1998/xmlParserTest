package com.company.controllers;

import com.company.domain.Student;
import com.company.utils.FilterUtils;
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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;
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
    @FXML
    private Label labelForName;
    @FXML
    private Label labelForAge;
    @FXML
    private Label labelForCourse;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        textFieldName.disableProperty().bind(checkBoxName.selectedProperty().not());
        textFieldAge.disableProperty().bind(checkBoxAge.selectedProperty().not());
        textFieldCourse.disableProperty().bind(checkBoxCourse.selectedProperty().not());
        checkBoxAgeSmall.disableProperty().bind(checkBoxAge.selectedProperty().not());
        checkBoxAgeEqual.disableProperty().bind(checkBoxAge.selectedProperty().not());
        checkBoxAgeBig.disableProperty().bind(checkBoxAge.selectedProperty().not());

        labelForName.visibleProperty().bind(isValidName.not());
        labelForAge.visibleProperty().bind(isValidAge.not());
        labelForCourse.visibleProperty().bind(isValidCourse.not());
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
        if (isValid()) {
            if (checkBoxName.isSelected()) {
                searchStudents.removeAll(searchStudents.stream().filter(s -> !FilterUtils.stringChecked(s.getName(), textFieldName.getText())).collect(Collectors.toList()));
            }
            if (checkBoxCourse.isSelected()) {
                searchStudents.removeAll(searchStudents.stream().filter(s -> !FilterUtils.stringChecked(s.getCourse(), textFieldCourse.getText())).collect(Collectors.toList()));
            }
            if (checkBoxAge.isSelected() && checkBoxAgeSmall.isSelected() || checkBoxAgeBig.isSelected() || checkBoxAgeEqual.isSelected()) {
                Integer age = new Integer(Integer.valueOf(textFieldAge.getText()));
                searchStudents.removeAll(searchStudents.stream().filter(s -> !(FilterUtils.numberChecked(s.getAge(), age, checkBoxAgeSmall.isSelected(), checkBoxAgeEqual.isSelected(), checkBoxAgeBig.isSelected()))).collect(Collectors.toList()));
            }
            ((Node) (actionEvent.getSource())).getScene().getWindow().hide();
        }
    }

    private boolean isValid() {

        if (checkBoxName.isSelected() && textFieldName.getText() != null) {
            isValidName.setValue(Pattern.compile("^[a-zA-Z -]{1,50}$").matcher(textFieldName.getText()).matches());
        }
        if (checkBoxAge.isSelected() && textFieldAge.getText() != null) {
            isValidAge.setValue(Pattern.compile("^[0-9]{1,50}$").matcher(textFieldAge.getText()).matches());
        }
        if (checkBoxCourse.isSelected() && textFieldCourse.getText() != null) {
            isValidCourse.setValue(Pattern.compile("^[a-zA-Z0-9 -]{1,50}$").matcher(textFieldCourse.getText()).matches());
        }
        return (isValidName.getValue() && isValidAge.getValue() && isValidCourse.getValue());
    }
}
