package com.cn5004ap.payroll.controller.employees;

import com.cn5004ap.payroll.controller.BaseController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class AddController
    extends BaseController
{
    @FXML
    private DataController dataController;

    @Override
    public void initialize()
    {
        dataController.backBtn.setVisible(false);
        dataController.saveBtn.setOnAction(this::save);
    }

    public void save(ActionEvent event)
    {
        System.out.println(dataController.firstName.getText());
    }
}
