package com.company.controllers;

import com.company.Main;
import com.company.domain.Student;
import com.company.utils.StudentUtils;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Comparator;
import java.util.Optional;
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
    private final ListProperty<Student> studentsToReset = new SimpleListProperty<>(FXCollections.observableArrayList());
    private BooleanProperty isOpenFile = new SimpleBooleanProperty(false);
    private BooleanProperty isStudentListChange = new SimpleBooleanProperty(false);
    private BooleanProperty isFiltred=new SimpleBooleanProperty(false);

    @FXML
    private VBox root;
    @FXML
    private TableView<Student> tableView;
    @FXML
    private MenuItem menuItemSave;
    @FXML
    private MenuItem menuItemSaveAs;
    @FXML
    private TableColumn<Student, Integer> colId;
    @FXML
    private TableColumn<Student, String> colName;
    @FXML
    private TableColumn<Student, Integer> colAge;
    @FXML
    private TableColumn<Student, String> colCourse;
    @FXML
    private Button buttonEdit;
    @FXML
    private MenuItem menuItemSearch;
    @FXML
    private  MenuItem menuItemResetFilter;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAge.setCellValueFactory(new PropertyValueFactory<>("age"));
        colCourse.setCellValueFactory(new PropertyValueFactory<>("course"));
        tableView.setItems(studentsToSave);
        menuItemSave.disableProperty().bind(isOpenFile.and(isStudentListChange).not());
        menuItemSaveAs.disableProperty().bind(isOpenFile.or(isStudentListChange).not());
        buttonEdit.disableProperty().bind(tableView.getSelectionModel().selectedItemProperty().isNull());
        menuItemSearch.disableProperty().bind(isOpenFile.or(isStudentListChange).not());
        menuItemResetFilter.disableProperty().bind(isFiltred.not());

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
            allStudents.stream().sorted(Comparator.comparingInt(Student::getId).reversed()).findFirst().ifPresent(student -> {
                ID_GENERATOR = new AtomicInteger(student.getId());
            });
            isOpenFile.setValue(true);
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

    @FXML
    public void addStudent(ActionEvent actionEvent) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/addFormView.fxml"));
        Parent addNewRoot = loader.load();
        Stage stage=new Stage();
        stage.setTitle("new");
        Scene scene = new Scene(addNewRoot);
        stage.setScene(scene);
        ((StudentsController) loader.getController()).setOkConsumer(student -> {
            student.setId(ID_GENERATOR.incrementAndGet());
            studentsToSave.add(student);
            isStudentListChange.setValue(true);
        });
        stage.show();
        stage.requestFocus();
    }
    @FXML
    public void editStudent(ActionEvent actionEvent) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/editFormView.fxml"));
        Parent editRoot = loader.load();

        Stage stage=new Stage();
        stage.setTitle("Edit");
        Scene scene = new Scene(editRoot);
        stage.setScene(scene);

        ((StudentsController) loader.getController()).setOkConsumer(student -> {
            studentsToSave.remove(student);
            studentsToSave.add(student);
            isStudentListChange.setValue(true);
        });
        ((StudentsController) loader.getController()).setCurrentStudent(tableView.getSelectionModel().getSelectedItem());
        ((StudentsController) loader.getController()).prepareForm();
        stage.show();
        stage.requestFocus();
    }

    public  void  searchStudents(ActionEvent actionEvent) throws IOException {
         FXMLLoader loader=new FXMLLoader(getClass().getResource("/views/searchStudentsView.fxml"));
         Parent searchRoot=loader.load();
         studentsToReset.clear();
         studentsToReset.addAll(studentsToSave);
        ((StudentsSearchController)loader.getController()).setSearchStudents(studentsToSave);
         isFiltred.set(true);
         Stage  stage=new Stage();
         stage.setTitle("Search");
         Scene scene=new Scene(searchRoot);
         stage.setScene(scene);
         stage.show();
    }

    public void resetFilter(ActionEvent actionEvent) {
     studentsToSave.clear();
     studentsToSave.addAll(studentsToReset);
    }
}
