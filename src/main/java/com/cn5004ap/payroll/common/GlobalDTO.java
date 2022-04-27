package com.cn5004ap.payroll.common;

import com.cn5004ap.payroll.persistence.EmployeeEntity;

public class GlobalDTO
{
    private EmployeeEntity selectedEmployee;

    public GlobalDTO()
    {
    }

    public EmployeeEntity getSelectedEmployee()
    {
        return selectedEmployee;
    }

    public void setSelectedEmployee(EmployeeEntity selectedEmployee)
    {
        this.selectedEmployee = selectedEmployee;
    }
}
