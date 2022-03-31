package com.payroll.controller;

import com.payroll.App;
import javafx.event.ActionEvent;

import java.io.IOException;

public class MainController
{
    public void initialize()
    {
    }

    public void logout(ActionEvent event)
        throws IOException
    {
        App.loadScene("view/login.fxml");
    }
}
