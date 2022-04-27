package com.cn5004ap.payroll.controller.employees;

import com.cn5004ap.payroll.App;
import com.cn5004ap.payroll.controller.BaseController;

public class UpdateController
    extends BaseController
{
    @Override
    public void initialize()
    {

    }

    public void read()
    {
        loadModule("employees/view");
    }

    public void list()
    {
        App.getUserData().clearSelectedEmployee();
        loadModule("employees/list");
    }
}
