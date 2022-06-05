package com.cn5004ap.payroll.controller.employees;

import com.cn5004ap.payroll.App;
import com.cn5004ap.payroll.common.DataValidator;
import com.cn5004ap.payroll.common.MaskField;
import com.cn5004ap.payroll.common.Utils;
import com.cn5004ap.payroll.controller.BaseController;
import com.cn5004ap.payroll.persistence.EmployeeEntity;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.validation.Severity;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.ToggleGroup;
import javafx.util.StringConverter;
import org.jetbrains.annotations.NotNull;

import java.util.function.UnaryOperator;
import java.util.regex.Pattern;


public class DataController
    extends BaseController
{
    @FXML
    public MFXTextField firstName;

    @FXML
    public MFXTextField lastName;

    @FXML
    public MFXTextField email;

    @FXML
    public MFXTextField address;

    @FXML
    public MaskField phone;

    @FXML
    public MaskField ssn;

    @FXML
    public ToggleGroup gender;

    @FXML
    public MFXDatePicker birthDate;

    @FXML
    public MaskField iban;

    @FXML
    public MFXTextField department;

    @FXML
    public MFXTextField title;

    @FXML
    public MFXTextField salary;

    @FXML
    public Label insuranceCalc;

    @FXML
    public Label taxCalc;

    @FXML
    public Label netSalaryCalc;

    @FXML
    public Button backToListBtn;

    @FXML
    public Button backBtn;

    @FXML
    public Button saveBtn;

    @FXML
    public Label validationLabel;

    public void initialize()
    {
        saveBtn.setDisable(true);

        setupFieldsConstraints();
        salaryValidator();
        resetSalaryCalculations();

        salary.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.isEmpty())
                calculateSalary(Double.parseDouble(newValue));
            else
                resetSalaryCalculations();
        });

        checkIfFormIsValid();
    }

    private void salaryValidator()
    {
        Pattern validEditingState = Pattern.compile("-?(([1-9][0-9]*)|0)?(\\.[0-9]*)?");

        UnaryOperator<TextFormatter.Change> filter = c -> {
            String text = c.getControlNewText();
            if (validEditingState.matcher(text).matches()) {
                return c ;
            } else {
                return null ;
            }
        };

        StringConverter<Double> converter = new StringConverter<>()
        {
            @Override
            public Double fromString(String s)
            {
                if (s.isEmpty() || "-".equals(s) || ".".equals(s) || "-.".equals(s))
                {
                    return 0.0;
                }
                else
                {
                    return Double.valueOf(s);
                }
            }

            @Override
            public String toString(Double d)
            {
                return d.toString();
            }
        };

        TextFormatter<Double> textFormatter = new TextFormatter<>(converter, 0.0, filter);
        salary.delegateSetTextFormatter(textFormatter);

        textFormatter.valueProperty()
            .addListener((ObservableValue<? extends Double> obs, Double oldValue, Double newValue)
                             -> System.out.println("User entered value: "+ newValue));
    }

    private void calculateSalary(double salary)
    {
        double insurance = EmployeeEntity.getInsuranceExpense(salary);
        double tax       = EmployeeEntity.getTaxExpense(salary);
        insuranceCalc.setText(Utils.moneyFormat(insurance));
        taxCalc.setText(Utils.moneyFormat(tax));
        netSalaryCalc.setText(Utils.moneyFormat(EmployeeEntity.getNetSalary(salary, insurance, tax)));
    }

    private void resetSalaryCalculations()
    {
        insuranceCalc.setText("-");
        taxCalc.setText("-");
        netSalaryCalc.setText("-");
    }

    private void setupFieldsConstraints()
    {
        DataValidator.forField(firstName)
            .lengthConstraint(3, DataValidator.LengthConstraint.MIN)
            .build();

        DataValidator.forField(lastName)
            .lengthConstraint(3, DataValidator.LengthConstraint.MIN)
            .build();

        DataValidator.forField(phone).lengthConstraint(10, DataValidator.LengthConstraint.EXACT)
            .build();

        DataValidator.forField(email)
            .lengthConstraint(6, DataValidator.LengthConstraint.MIN)
            .build();

        DataValidator.forField(address).notEmpty().build();

        DataValidator.forField(iban).lengthConstraint(27, DataValidator.LengthConstraint.EXACT)
            .build();

        DataValidator.forField(birthDate).notEmpty().build();

        DataValidator.forField(ssn).lengthConstraint(9, DataValidator.LengthConstraint.EXACT)
            .build();

        DataValidator.forField(department).notEmpty().build();

        DataValidator.forField(title).notEmpty().build();

        DataValidator.forField(salary)
            .addConstraints(DataValidator.constraintBuilder(
                Bindings.createBooleanBinding(
                    () -> Double.parseDouble(salary.getText().isEmpty()?"0":salary.getText()) > 0,
                    salary.textProperty()
                ),
                "Salary must be more than 0",
                Severity.ERROR
            ))
            .build();
    }

    private void checkIfFormIsValid()
    {
        listenField(firstName);
        listenField(lastName);
        listenField(phone);
        listenField(email);
        listenField(address);
        listenField(iban);
        listenField(birthDate);
        listenField(ssn);
        listenField(department);
        listenField(title);
        listenField(salary);

        // Listen for gender RadioButton toggle
        gender.selectedToggleProperty().addListener(
            (group, male, female) ->
                saveBtn.setDisable(!isValid()));
    }

    private void listenField(@NotNull MFXTextField field)
    {
        field.textProperty().addListener((observable, oldValue, newValue) -> saveBtn.setDisable(!isValid()));
    }

    private @NotNull Boolean isValid()
    {
        if (!firstName.isValid())
            return false;

        if (!lastName.isValid())
            return false;

        if (!phone.isValid())
            return false;

        if (!email.isValid())
            return false;

        if (!address.isValid())
            return false;

        if (!iban.isValid())
            return false;

        if (null == gender.getSelectedToggle())
            return false;

        if (!birthDate.isValid())
            return false;

        if (!ssn.isValid())
            return false;

        if (!department.isValid())
            return false;

        if (!title.isValid())
            return false;

        return salary.isValid();
    }

    public void back(ActionEvent event)
    {
        loadModule("employees/view");
    }

    public void backToList(ActionEvent event)
    {
        App.getUserData().clearSelectedEmployee();
        loadModule("employees/list");
    }
}
