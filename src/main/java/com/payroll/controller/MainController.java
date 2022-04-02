package com.payroll.controller;

import com.payroll.App;
import javafx.event.ActionEvent;

import java.io.IOException;

public class MainController extends Controller
{
    public void logout(ActionEvent event)
        throws IOException
    {
        App.loadScene("login");
    }
}
