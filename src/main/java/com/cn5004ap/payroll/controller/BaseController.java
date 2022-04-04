package com.cn5004ap.payroll.controller;

import com.cn5004ap.payroll.App;
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
