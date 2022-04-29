package com.cn5004ap.payroll.controller.employees;

import com.cn5004ap.payroll.controller.BaseController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class UpdateController
    extends BaseController
{
    @FXML
    private DataController dataController;

    @Override
    public void initialize()
    {
        dataController.saveBtn.setOnAction(this::update);
    }

    public void update(ActionEvent event)
    {
        System.out.println(dataController.firstName.getText());
    }
}
