package com.cn5004ap.payroll.common;

import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.validation.Constraint;
import io.github.palexdev.materialfx.validation.Severity;
import javafx.beans.binding.BooleanExpression;
import javafx.beans.property.SimpleStringProperty;
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

    public final String[] UPPER_CHARS = "A B C D E F G H I J K L M N O P Q R S T U V W X Y Z".split(" ");

    public final String[] LOWER_CHARS = "a b c d e f g h i j k l m n o p q r s t u v w x y z".split(" ");

    public final String[] DIGITS = "0 1 2 3 4 5 6 7 8 9".split(" ");

    public final String[] SPECIAL_CHARS = "! @ # & ( ) – [ { } ]: ; ' , ? / * ~ $ ^ + = < > -".split(" ");

    private DataValidator(MFXTextField field)
    {
        this.field = field;
    }

    @Contract("_ -> new")
    public static @NotNull DataValidator forField(MFXTextField field)
    {
        return new DataValidator(field);
    }

    public static Constraint constraintBuilder(BooleanExpression condition, String message, Severity severity)
    {
        return Constraint.Builder.build()
                   .setSeverity(severity)
                   .setMessage(message)
                   .setCondition(condition)
                   .get();
    }

    /**
     * Add a constraint to the field.
     * @param args (int) length | LengthConstraint | message | severity
     * @return this
     */
    @Contract("_ -> this")
    public DataValidator lengthConstraint(Object @NotNull ... args)
    {
        // First argument, if it does not exist,
        // then a run-time exception will be thrown
        Integer length = (Integer) args[0];

        // Default value for lengthConstraint = LengthConstraint.MAX
        LengthConstraint lengthConstraint = LengthConstraint.MAX;

        // Empty value for message
        String message = "";

        // Default severity level is ERROR
        Severity severity = Severity.ERROR;

        // Replace defaults if replacements exist
        try
        {
            lengthConstraint = (LengthConstraint) args[1];
            message = (String) args[2];
            severity = (Severity) args[3];
        }
        catch (Exception ignored)
        { }

        if (message.isEmpty())
            message  = String.format(
                "%s must be %%s %%d characters long",
                field.getFloatingText()
            );

        switch (lengthConstraint)
        {
            case EXACT -> addConstraint(constraintBuilder(
                (field instanceof MaskField)
                ? (new SimpleStringProperty(((MaskField) field).getPlainText())).length().isEqualTo(length)
                : field.textProperty().length().isEqualTo(length),
                String.format(message, "exactly", length),
                severity
            ));

            case MIN -> addConstraint(constraintBuilder(
                (field instanceof MaskField)
                ? (new SimpleStringProperty(((MaskField) field).getPlainText())).length().greaterThanOrEqualTo(length)
                : field.textProperty().length().greaterThanOrEqualTo(length),
                String.format(message, "at least", length),
                severity
            ));

            case MAX -> addConstraint(constraintBuilder(
                (field instanceof MaskField)
                ? (new SimpleStringProperty(((MaskField) field).getPlainText())).length().lessThanOrEqualTo(length)
                : field.textProperty().length().lessThanOrEqualTo(length),
                String.format(message, "maximum", length),
                severity
            ));
        }

        return this;
    }

    public DataValidator notEmpty()
    {
        lengthConstraint(1, LengthConstraint.MIN, String.format(
            "%s must not be empty.", field.getFloatingText()
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

    public enum LengthConstraint
    {
        MIN,
        MAX,
        EXACT
    }
}
