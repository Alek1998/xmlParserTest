package com.company.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by alek.aleksanyan on 1/20/2017.
 */
public class StudentsSearchController implements Initializable {




    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void exitApp(ActionEvent actionEvent) {
        ((Node) (actionEvent.getSource())).getScene().getWindow().hide();
    }

    public void okSearch(ActionEvent actionEvent) {

    }
}
