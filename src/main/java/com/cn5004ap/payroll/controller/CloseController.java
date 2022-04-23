package com.cn5004ap.payroll.controller;

import com.cn5004ap.payroll.App;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class CloseController
{
    @FXML
    protected Button closeBtn;

    public void closeApp()
    {
        //TODO: Check for remember me functionality, otherwise logout
        App.getStage().close(); // close the application
        Platform.exit(); // close GUI thread
        System.exit(0); // close JVM | 0 = normal exit
    }
}
