package com.payroll.controller;

import com.payroll.App;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;

public abstract class BaseController
{
    @FXML
    private Pane rootPane;

    public void initialize()
    {
        App.enableDrag(rootPane);
    }
}
