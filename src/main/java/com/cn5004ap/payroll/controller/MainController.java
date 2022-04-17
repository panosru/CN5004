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



    public void loadEmployees(ActionEvent actionEvent)
    {
        setActiveButton(employeesBtn);
        loadModule("employees/index");
    }

    public void loadPayments(ActionEvent actionEvent)
    {
        setActiveButton(paymentsBtn);
        loadModule("payments/index");
    }

    public void loadStatistics(ActionEvent actionEvent)
    {
        setActiveButton(statisticsBtn);
        loadModule("statistics/index");
    }

    public void loadProfile(ActionEvent actionEvent)
    {
        setActiveButton(profileBtn);
        loadModule("profile/index");
    }

    public void loadSettings(ActionEvent actionEvent)
    {
        setActiveButton(settingsBtn);
        loadModule("settings/index");
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
