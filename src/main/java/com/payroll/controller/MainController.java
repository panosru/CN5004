package com.payroll.controller;

import com.payroll.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import org.controlsfx.control.BreadCrumbBar;

import java.io.IOException;

public class MainController extends BaseController
{
    @FXML
    private BreadCrumbBar<String> bread;

    public void logout(ActionEvent event)
        throws IOException
    {
        App.loadScene("login");
    }
}
