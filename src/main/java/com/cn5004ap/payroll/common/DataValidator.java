package com.cn5004ap.payroll.common;

import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.validation.Constraint;
import io.github.palexdev.materialfx.validation.Severity;
import javafx.beans.binding.BooleanExpression;
import javafx.scene.control.Tooltip;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static io.github.palexdev.materialfx.validation.Validated.INVALID_PSEUDO_CLASS;

public final class DataValidator
{
    private final MFXTextField field;

    private final Set<Constraint> constraintList = new LinkedHashSet<>();

    private final String ERROR_STYLE_CLASS = "data-error";

    private DataValidator(MFXTextField field)
    {
        this.field = field;
    }

    @Contract("_ -> new")
    public static @NotNull DataValidator forField(MFXTextField field)
    {
        return new DataValidator(field);
    }

    public DataValidator lengthConstraint(int length, String message, Severity severity)
    {
        addConstraint(constraintBuilder(
            field.textProperty().length().greaterThanOrEqualTo(length),
            String.format(message, length),
            severity
        ));

        return this;
    }

    public DataValidator addConstraint(Constraint constraint)
    {
        constraintList.add(constraint);

        return this;
    }

    @Contract("_ -> this")
    public DataValidator addConstraints(Constraint @NotNull ... constraints)
    {
        for (Constraint constraint : constraints)
            addConstraint(constraint);

        return this;
    }

    public void build()
    {
        for (Constraint constraint : constraintList)
            field.getValidator().constraint(constraint);

        field.delegateFocusedProperty().addListener((observable, oldValue, newValue) -> {
            if (oldValue && !newValue) {
                List<Constraint> constraintList = field.validate();
                if (!constraintList.isEmpty()) {
                    field.pseudoClassStateChanged(INVALID_PSEUDO_CLASS, true);
                    field.setTooltip(new Tooltip(
                        constraintList.stream()
                            .map(Constraint::getMessage)
                            .map(s -> String.format("\u2023 %s.", s))
                            .collect(Collectors.joining("\n"))
                    ));
                    field.getStyleClass().add(ERROR_STYLE_CLASS);
                }
                else
                {
                    field.getStyleClass().remove(ERROR_STYLE_CLASS);
                    field.setTooltip(null);
                }
            }
        });
    }

    private Constraint constraintBuilder(BooleanExpression condition, String message, Severity severity)
    {
        return Constraint.Builder.build()
                   .setSeverity(severity)
                   .setMessage(message)
                   .setCondition(condition)
                   .get();
    }
}
