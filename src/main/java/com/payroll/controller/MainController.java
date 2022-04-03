package com.payroll.controller;

import com.payroll.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class MainController
    extends BaseController
{
    @FXML
    BorderPane rootPane;

    @Override
    public void initialize()
    {
        loadPane("employees");

        super.initialize();
    }

    public void logout(ActionEvent event)
        throws IOException
    {
        App.loadScene("login");
    }

    private void loadPane(String fxml)
    {
        // Remove existing right content
        rootPane.getChildren().remove(rootPane.getRight());

        try
        {
            // Load pane
            Node node = App.loadNode(String.format("main/%s", fxml));
            rootPane.setRight(node);
        }
        catch (final IOException e)
        {
            e.printStackTrace();
        }
    }

    public void loadEmployees(ActionEvent actionEvent)
    {
        loadPane("employees");
    }

    public void loadPayments(ActionEvent actionEvent)
    {
        loadPane("payments");
    }

    public void loadStatistics(ActionEvent actionEvent)
    {
        loadPane("statistics");
    }

    public void loadProfile(ActionEvent actionEvent)
    {
        loadPane("profile");
    }

    public void loadSettings(ActionEvent actionEvent)
    {
        loadPane("settings");
    }
}
