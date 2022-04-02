package com.payroll.controller;

import com.payroll.App;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

public abstract class BaseController
{
    @FXML
    protected Button closeBtn;

    @FXML
    private Pane rootPane;

    public void closeApp(ActionEvent actionEvent)
    {
        App.getStage().close(); // close the application
        Platform.exit(); // close GUI thread
        System.exit(0); // close JVM | 0 = normal exit
    }

    public void initialize()
    {
        App.enableDrag(rootPane);
    }
}
