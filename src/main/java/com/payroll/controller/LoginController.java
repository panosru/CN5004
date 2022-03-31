package com.payroll.controller;

import com.payroll.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;

public class LoginController
{
    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    @FXML
    private Button loginBtn;

    @FXML
    private Label errorMessage;

    public void initialize()
    { }

    public void userLogin(ActionEvent event)
        throws IOException
    {
        if (username.getText().equals("panosru") && password.getText().equals("123"))
        {
            App.loadScene("view/main.fxml");
        }
        else if (username.getText().isEmpty() || password.getText().isEmpty())
        {
            errorMessage.setText("Fill the data");
        }
        else
        {
            errorMessage.setText("Something went wrong!");
        }
    }
}
