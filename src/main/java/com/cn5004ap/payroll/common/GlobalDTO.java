package com.cn5004ap.payroll.common;

public class GlobalDTO
{
    private String currentEmployeeID;

    public GlobalDTO()
    {
    }

    public String getCurrentEmployeeID()
    {
        return currentEmployeeID;
    }

    public void setCurrentEmployeeID(String currentEmployeeID)
    {
        this.currentEmployeeID = currentEmployeeID;
    }
}
