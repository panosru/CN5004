package com.cn5004ap.payroll.persistence;

import com.cn5004ap.payroll.common.Utils;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Table(name = "transactions")
public class TransactionEntity
    extends BaseEntity
{
    @Column(name = "gross_amount", nullable = false)
    private double gross_amount;


    @Column(name = "net_amount")
    private double net_amount;

    @Column(name = "insurance")
    private double insurance;

    @Column(name = "tax")
    private double tax;

    @Column(name = "date", nullable = false)
    private Date date;

    @Column(name = "type", nullable = false)
    private int type;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "employee_id", referencedColumnName = "id", nullable = false)
    private EmployeeEntity employee;

    public TransactionEntity()
    {}

    public TransactionEntity(
        double gross_amount,
        TransactionType type,
        Date date,
        EmployeeEntity employee)
    {
        this();
        setGrossAmount(gross_amount);
        setDate(date);
        setType(type);
        setEmployee(employee);
    }

    public TransactionEntity(
        double gross_amount,
        double net_amount,
        double insurance,
        double  tax,
        TransactionType type,
        Date date,
        EmployeeEntity employee)
    {
        this(gross_amount, type, date, employee);
        setNetAmount(net_amount);
        setInsurance(insurance);
        setTax(tax);
    }

    public double getGrossAmount()
    {
        return gross_amount;
    }

    public void setGrossAmount(double amount)
    {
        this.gross_amount = amount;
    }

    public double getNetAmount()
    {
        return net_amount;
    }

    public String getNetAmountFormatted()
    {
        if (0 < getNetAmount())
            return Utils.moneyFormat(getNetAmount(), 2);

        return "-";
    }

    public void setNetAmount(double net_amount)
    {
        this.net_amount = net_amount;
    }

    public double getInsurance()
    {
        return insurance;
    }

    public String getInsuranceFormatted()
    {
        if (0 < getInsurance())
            return Utils.moneyFormat(getInsurance(), 2);

        return "-";
    }

    public void setInsurance(double insurance)
    {
        this.insurance = insurance;
    }

    public double getTax()
    {
        return tax;
    }

    public String getTaxFormatted()
    {
        if (0 < getTax())
            return Utils.moneyFormat(getTax(), 2);

        return "-";
    }

    public void setTax(double tax)
    {
        this.tax = tax;
    }

    public TransactionType getType()
    {
        return switch (type) {
            case 1 -> TransactionType.SALARY;
            case 2 -> TransactionType.BONUS;
            case 3 -> TransactionType.DEDUCTION;
            default -> throw new IllegalStateException("Invalid transaction type");
        };
    }

    public void setType(TransactionType type)
    {
        this.type = switch (type) {
            case SALARY -> 1;
            case BONUS -> 2;
            case DEDUCTION -> 3;
        };
    }

    public Date getDate()
    {
        return date;
    }

    public String getDateFormatted()
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(getDate());
    }

    public void setDate(Date date)
    {
        this.date = date;
    }

    public EmployeeEntity getEmployee()
    {
        return employee;
    }

    public String getEmployeeFullName()
    {
        return employee.getFullName();
    }

    public void setEmployee(EmployeeEntity employee)
    {
        this.employee = employee;
    }

    public enum TransactionType
    {
        SALARY,
        BONUS,
        DEDUCTION
    }
}
