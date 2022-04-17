package com.cn5004ap.payroll.controller;

import javafx.event.ActionEvent;

public class EmployeesController
    extends BaseController
{
    @Override
    public void initialize()
    { }

    public void page2(ActionEvent actionEvent)
    {
        loadModulePane("employees/page2");
    }

    public void goBack(ActionEvent actionEvent)
    {
        loadModulePane("employees/page1");
    }
}
