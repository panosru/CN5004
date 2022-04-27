package com.cn5004ap.payroll.controller;

import com.cn5004ap.payroll.common.Multiton;
import com.cn5004ap.payroll.persistence.SettingsEntity;
import com.cn5004ap.payroll.persistence.SettingsRepository;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.validation.Constraint;
import io.github.palexdev.materialfx.validation.Severity;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.util.Optional;

public class SettingsController
    extends BaseController
{
    @FXML
    private MFXButton save;

    @FXML
    private MFXTextField tax;

    @FXML
    private MFXTextField insurance;

    private static final SettingsEntity taxEntity;

    private static final SettingsEntity insuranceEntity;

    private static final SettingsRepository settingsRepository = Multiton.getInstance(SettingsRepository.class);

    static
    {
        taxEntity = settingsRepository.getSettingByKey("tax");
        insuranceEntity = settingsRepository.getSettingByKey("insurance");
    }

    @Override
    public void initialize()
    {
        // Relly on default values if not stored in DB
        tax.setText(Double.toString(getTaxPercent()));
        insurance.setText(Double.toString(getInsurancePercent()));

        //TODO: Set a check for digits only for tax and insurance

        // Setting fields constraints
        //Constraint digitsOnly = Constraint.Builder.build()
        //    .setSeverity(Severity.ERROR)
        //    .setMessage("Only digits are allowed");
    }

    public static double getTaxPercent()
    {
        return Double.parseDouble(taxEntity.getValue());
    }

    public static double getInsurancePercent()
    {
        return Double.parseDouble(insuranceEntity.getValue());
    }

    public void onSave()
    {
        if (!tax.getText().isBlank())
        {
            taxEntity.setValue(tax.getText());
            settingsRepository.save(taxEntity);
        }

        if (!insurance.getText().isBlank())
        {
            insuranceEntity.setValue(insurance.getText());
            settingsRepository.save(insuranceEntity);
        }
    }
}
