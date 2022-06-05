package com.cn5004ap.payroll.controller.employees;

import com.cn5004ap.payroll.App;
import com.cn5004ap.payroll.common.ModalFactory;
import com.cn5004ap.payroll.common.Multiton;
import com.cn5004ap.payroll.common.Utils;
import com.cn5004ap.payroll.controller.BaseController;
import com.cn5004ap.payroll.persistence.EmployeeEntity;
import com.cn5004ap.payroll.persistence.EmployeeRepository;
import io.github.palexdev.materialfx.controls.MFXRadioButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.util.Map;

public class UpdateController
    extends BaseController
{
    @FXML
    private DataController dataController;

    private EmployeeEntity employee;

    private EmployeeRepository employeeRepository;

    @Override
    public void initialize()
    {
        employeeRepository = Multiton.getInstance(EmployeeRepository.class);
        employee = App.getUserData().getSelectedEmployee();

        dataController.firstName.setText(employee.getFirstName());
        dataController.lastName.setText(employee.getLastName());
        dataController.phone.setPlainText(employee.getPhone());
        dataController.email.setText(employee.getEmail());
        dataController.address.setText(employee.getAddress());
        dataController.iban.setPlainText(employee.getIban());

        switch (employee.getGenderEnum())
        {
            case MALE -> dataController.gender.selectToggle(dataController.gender.getToggles().get(0));
            case FEMALE -> dataController.gender.selectToggle(dataController.gender.getToggles().get(1));
        }

        dataController.birthDate.setValue(Utils.convertDateToLocal(employee.getBirthDate()));
        dataController.ssn.setPlainText(employee.getSsn());
        dataController.department.setText(employee.getDepartment());
        dataController.title.setText(employee.getTitle());
        dataController.salary.setText(Double.toString(employee.getGrossSalary()));

        dataController.saveBtn.setOnAction(this::update);
    }

    public void update(ActionEvent event)
    {
        String currentFullName = employee.getFullName();

        employee.setFirstName(dataController.firstName.getText());
        employee.setLastName(dataController.lastName.getText());
        employee.setPhone(dataController.phone.getPlainText());
        employee.setEmail(dataController.email.getText());
        employee.setAddress(dataController.address.getText());
        employee.setIban(dataController.iban.getPlainText());
        employee.setGender(((MFXRadioButton)dataController.gender.getSelectedToggle()).getText().substring(0, 1));
        employee.setBirthDate(Utils.convertLocalDateToDate(dataController.birthDate.getValue()));
        employee.setSsn(dataController.ssn.getPlainText());
        employee.setDepartment(dataController.department.getText());
        employee.setTitle(dataController.title.getText());
        employee.setGrossSalary(Double.parseDouble(dataController.salary.getText()));

        ModalFactory.Modal modal = ModalFactory.create(
            ModalFactory.Type.SIMPLE,
            "Update employee: " + currentFullName,
            "Are you sure you want to update the current employee with the new data?",
            ModalFactory.Level.INFO
        );
        modal.setActions(
            Map.entry(ModalFactory.Modal.ActionButtons.cancel(), e -> modal.close()),
            Map.entry(ModalFactory.Modal.ActionButtons.confirm(), e -> {
                employeeRepository.save(employee);
                modal.close();
            })
        );
        modal.show();
    }
}
