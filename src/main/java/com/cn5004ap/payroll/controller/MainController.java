package com.cn5004ap.payroll.controller;

import com.cn5004ap.payroll.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class MainController
    extends BaseController
{
    @FXML
    BorderPane rootPane;

    @FXML
    VBox mainMenu;

    @FXML
    Button employeesBtn;

    @FXML
    Button paymentsBtn;

    @FXML
    Button statisticsBtn;

    @FXML
    Button profileBtn;

    @FXML
    Button settingsBtn;

    @Override
    public void initialize()
    {
        loadEmployees(null);

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
        setActiveButton(employeesBtn);
        loadPane("employees");
    }

    public void loadPayments(ActionEvent actionEvent)
    {
        setActiveButton(paymentsBtn);
        loadPane("payments");
    }

    public void loadStatistics(ActionEvent actionEvent)
    {
        setActiveButton(statisticsBtn);
        loadPane("statistics");
    }

    public void loadProfile(ActionEvent actionEvent)
    {
        setActiveButton(profileBtn);
        loadPane("profile");
    }

    public void loadSettings(ActionEvent actionEvent)
    {
        setActiveButton(settingsBtn);
        loadPane("settings");
    }

    private void setActiveButton(Button button)
    {
        mainMenu.getChildren().forEach(
            b -> b.getStyleClass().removeIf(
                style -> style.equals("active")
            )
        );

        button.getStyleClass().add("active");
    }
}
