package com.cn5004ap.payroll.controller;

import com.cn5004ap.payroll.App;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class MainController
    extends BaseController
{
    @FXML
    private VBox mainMenu;

    @FXML
    private Button employeesBtn;

    @FXML
    private Button paymentsBtn;

    @FXML
    private Button statisticsBtn;

    @FXML
    private Button profileBtn;

    @FXML
    private Button settingsBtn;

    @Override
    public void initialize()
    {
        loadEmployees();

        super.initialize();
    }

    public void logout()
        throws IOException
    {
        App.loadScene("login");
    }



    public void loadEmployees()
    {
        setActiveButton(employeesBtn);
        loadModule("employees/index");
    }

    public void loadPayments()
    {
        setActiveButton(paymentsBtn);
        loadModule("payments/index");
    }

    public void loadStatistics()
    {
        setActiveButton(statisticsBtn);
        loadModule("statistics/index");
    }

    public void loadProfile()
    {
        setActiveButton(profileBtn);
        loadModule("profile/index");
    }

    public void loadSettings()
    {
        setActiveButton(settingsBtn);
        loadModule("settings/index");
    }

    private void setActiveButton(@NotNull Button button)
    {
        mainMenu.getChildren().forEach(
            b -> b.getStyleClass().removeIf(
                style -> style.equals("active")
            )
        );

        button.getStyleClass().add("active");
    }
}
