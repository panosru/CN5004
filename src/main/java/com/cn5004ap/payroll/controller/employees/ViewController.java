package com.cn5004ap.payroll.controller.employees;

import com.cn5004ap.payroll.App;
import com.cn5004ap.payroll.common.Utils;
import com.cn5004ap.payroll.controller.BaseController;
import com.cn5004ap.payroll.persistence.EmployeeEntity;
import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import org.kordamp.ikonli.javafx.FontIcon;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.Period;
import java.util.Date;

public class ViewController
    extends BaseController
{
    @FXML
    private Label iban;

    @FXML
    private Label net_salary;

    @FXML
    private Label insurance;

    @FXML
    private Label tax_expense;

    @FXML
    private Label gross_salary;

    @FXML
    private Label gender;

    @FXML
    private Label birth_date;

    @FXML
    private Hyperlink phone;

    @FXML
    private Hyperlink address;

    @FXML
    private Hyperlink email;

    @FXML
    private Label ssn;

    @FXML
    private Label termination_date;

    @FXML
    private Label employment_date;

    @FXML
    private Label employment_years;

    @FXML
    private Label title;

    @FXML
    private Label department;

    @FXML
    private Label fullName;

    @FXML
    private FontIcon status;

    @FXML
    private FontIcon birthday;

    private EmployeeEntity employee;

    private final Date today = new Date();

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyy");

    @Override
    public void initialize()
    {
        employee = App.getUserData().getSelectedEmployee();
        fullName.setText(employee.getFullName());

        status.setIconLiteral(employee.isActive() ? "fas-user-check" : "fas-user-alt-slash");
        status.getStyleClass().add(employee.isActive() ? "active" : "terminated");

        // Check if today is employee birthday
        SimpleDateFormat birthDateFormat = new SimpleDateFormat("MM-dd");
        if (!birthDateFormat.format(employee.getBirthDate()).equals(birthDateFormat.format(today)))
            birthday.setVisible(false);

        department.setText(employee.getDepartment());
        title.setText(employee.getTitle());
        employment_date.setText(dateFormat.format(employee.getEmploymentDate()));
        termination_date.setText(employee.isActive()
            ? "-"
            : dateFormat.format(employee.getTerminationDate()));


        String terminationPeriodStr = "";
        if (!employee.isActive())
        {
            Period terminationPeriod = employee.getTerminationPeriod();
            terminationPeriodStr = String.format(
                " (terminated since %d years and %d months)",
                terminationPeriod.getYears(),
                terminationPeriod.getMonths()
            );
        }

        employment_years.setText(String.format(
            "%s%s", employee.getEmploymentPeriod().getYears(), terminationPeriodStr
        ));



        ssn.setText(employee.getSsn());
        email.setText(employee.getEmail());
        address.setText(employee.getAddress());
        phone.setText(employee.getPhone());
        birth_date.setText(dateFormat.format(employee.getBirthDate()));
        gender.setText(Utils.capitalise(employee.getGenderEnum().name()));
        gross_salary.setText(EmployeeEntity.salaryPrettify(employee.getGrossSalary()));
        insurance.setText(EmployeeEntity.salaryPrettify(employee.getInsuranceExpense()));
        tax_expense.setText(EmployeeEntity.salaryPrettify(employee.getTaxExpense()));
        net_salary.setText(EmployeeEntity.salaryPrettify(employee.getNetSalary()));
        iban.setText(employee.getIban());
    }

    public void sendEmail()
        throws URISyntaxException, IOException
    {
        if (null != App.getDesktop() && App.getDesktop().isSupported(Desktop.Action.MAIL))
        {
            URI mailto = new URI(String.format(
                "mailto:%s?subject=%s",
                employee.getEmail(), URLEncoder.encode("HR Department", StandardCharsets.UTF_8)
            ));
            App.getDesktop().mail(mailto);
        }
    }

    public void callNumber()
        throws URISyntaxException, IOException
    {
        if (null != App.getDesktop() && App.getDesktop().isSupported(Desktop.Action.APP_OPEN_URI))
        {
            URI call = new URI(String.format("tel:%s", employee.getPhone()));
            App.getDesktop().browse(call);
        }
    }

    public void openMap()
        throws URISyntaxException, IOException
    {
        if (null != App.getDesktop() && App.getDesktop().isSupported(Desktop.Action.APP_OPEN_URI))
        {
            URI map = new URI(String.format(
                "maps://?q=%s",
                URLEncoder.encode(employee.getAddress(), StandardCharsets.UTF_8)
            ));
            App.getDesktop().browse(map);
        }
    }

    public void list()
    {
        App.getUserData().clearSelectedEmployee();
        loadModule("employees/list");
    }

    public void update()
    {
        loadModule("employees/update");
    }
}
