package com.company;

import com.company.domain.Student;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.util.stream.Stream;

import static com.company.utils.StudentUtils.csvToStudentlistParse;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/views/mainView.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Home page");
        primaryStage.show();
        primaryStage.requestFocus();
        primaryStage.setOnCloseRequest(event -> {
            event.consume();
            exitApp();
        });
    }

    public static void exitApp() {
        Platform.exit();
    }

}
