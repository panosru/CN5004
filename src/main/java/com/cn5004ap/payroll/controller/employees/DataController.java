package com.cn5004ap.payroll.controller.employees;

import com.cn5004ap.payroll.App;
import com.cn5004ap.payroll.common.DataValidator;
import com.cn5004ap.payroll.controller.BaseController;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.controls.MFXFilterComboBox;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.validation.Constraint;
import io.github.palexdev.materialfx.validation.Severity;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;


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
    public MFXTextField phone;

    @FXML
    public MFXTextField ssn;

    @FXML
    public ToggleGroup gender;

    @FXML
    public MFXDatePicker birthDate;

    @FXML
    public MFXTextField iban;

    @FXML
    public MFXFilterComboBox<String> department;

    @FXML
    public MFXFilterComboBox<String> title;

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
        setupFirstNameConstraints();
    }

    private void setupFirstNameConstraints()
    {
        Constraint lengthConstraint = Constraint
            .Builder.build()
                    .setSeverity(Severity.ERROR)
                    .setMessage("First name must be at least 8 characters long")
                    .setCondition(firstName.textProperty().length().greaterThanOrEqualTo(8))
                    .get();

        Constraint lengthConstraint2 = Constraint
            .Builder.build()
                    .setSeverity(Severity.ERROR)
                    .setMessage("First name must be at least 10 characters long")
                    .setCondition(firstName.textProperty().length().greaterThanOrEqualTo(10))
                    .get();

        DataValidator.forField(firstName).addConstraints(lengthConstraint, lengthConstraint2).build();
        DataValidator.forField(lastName)
            .lengthConstraint(12, "Last name must be at least %d characters long", Severity.ERROR)
            .build();
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
