package com.cn5004ap.payroll.common;

import com.cn5004ap.payroll.persistence.TransactionEntity;
import io.github.palexdev.materialfx.controls.MFXPaginatedTableView;
import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import io.github.palexdev.materialfx.enums.SortState;
import io.github.palexdev.materialfx.filter.DoubleFilter;
import io.github.palexdev.materialfx.filter.EnumFilter;
import io.github.palexdev.materialfx.filter.StringFilter;
import javafx.geometry.Pos;

import java.util.Comparator;

public class TransactionsTable
{
    private final MFXPaginatedTableView<TransactionEntity> table;

    private boolean showEmployee = false;

    public TransactionsTable(MFXPaginatedTableView<TransactionEntity> table)
    {
        this.table = table;
    }

    public TransactionsTable(MFXPaginatedTableView<TransactionEntity> table, boolean showEmployee)
    {
        this(table);
        this.showEmployee = showEmployee;
    }

    public void setup()
    {
        MFXTableColumn<TransactionEntity> employeeColumn = new MFXTableColumn<>(
            "Employee", true, Comparator.comparing(TransactionEntity::getEmployeeFullName)) {{
            setPrefWidth(180);
        }};
        MFXTableColumn<TransactionEntity> dateColumn = new MFXTableColumn<>(
            "Date", true, Comparator.comparing(TransactionEntity::getDate)) {{
            setPrefWidth(140);
            setSortState(SortState.DESCENDING);
        }};
        MFXTableColumn<TransactionEntity> grossAmountColumn = new MFXTableColumn<>(
            "Gross Amount", true, Comparator.comparing(TransactionEntity::getGrossAmount)) {{
            setPrefWidth(140);
        }};
        MFXTableColumn<TransactionEntity> netAmountColumn = new MFXTableColumn<>(
            "Net Amount", true, Comparator.comparing(TransactionEntity::getNetAmount)) {{
            setPrefWidth(140);
        }};
        MFXTableColumn<TransactionEntity> insuranceColumn = new MFXTableColumn<>(
            "Insurance", true, Comparator.comparing(TransactionEntity::getInsurance)) {{
            setPrefWidth(80);
        }};
        MFXTableColumn<TransactionEntity> taxColumn = new MFXTableColumn<>(
            "Tax", true, Comparator.comparing(TransactionEntity::getTax)) {{
            setPrefWidth(80);
        }};
        MFXTableColumn<TransactionEntity> typeColumn = new MFXTableColumn<>(
            "Type", true, Comparator.comparing(TransactionEntity::getType)) {{
            setPrefWidth(100);
        }};

        employeeColumn.setRowCellFactory(transaction -> new MFXTableRowCell<>(TransactionEntity::getEmployeeFullName));
        dateColumn.setRowCellFactory(transaction -> new MFXTableRowCell<>(TransactionEntity::getDateFormatted));
        grossAmountColumn.setRowCellFactory(transaction -> new MFXTableRowCell<>(
            TransactionEntity::getGrossAmount,
            amount -> Utils.moneyFormat(amount, 2)
        ) {{
            setAlignment(Pos.CENTER_RIGHT);
        }});
        netAmountColumn.setRowCellFactory(transaction -> new MFXTableRowCell<>(
            TransactionEntity::getNetAmountFormatted
        ) {{
            setAlignment(Pos.CENTER_RIGHT);
        }});
        insuranceColumn.setRowCellFactory(transaction -> new MFXTableRowCell<>(
            TransactionEntity::getInsuranceFormatted
        ) {{
            setAlignment(Pos.CENTER_RIGHT);
        }});
        taxColumn.setRowCellFactory(transaction -> new MFXTableRowCell<>(
            TransactionEntity::getTaxFormatted
        ) {{
            setAlignment(Pos.CENTER_RIGHT);
        }});
        typeColumn.setRowCellFactory(transaction -> new MFXTableRowCell<>(TransactionEntity::getType) {{
            setAlignment(Pos.CENTER);
        }});

        if (showEmployee)
        {
            table.getTableColumns().add(employeeColumn);
            table.getFilters().add(
                new StringFilter<>("Employee", TransactionEntity::getEmployeeFullName)
            );
        }

        table.getTableColumns().addAll(
            dateColumn,
            grossAmountColumn,
            netAmountColumn,
            insuranceColumn,
            taxColumn,
            typeColumn
        );

        table.getFilters().addAll(
            new DoubleFilter<>("Gross Amount", TransactionEntity::getGrossAmount),
            new DoubleFilter<>("Net Amount", TransactionEntity::getNetAmount),
            new DoubleFilter<>("Insurance", TransactionEntity::getInsurance),
            new DoubleFilter<>("Tax", TransactionEntity::getTax),
            new EnumFilter<>("Type", TransactionEntity::getType, TransactionEntity.TransactionType.class)
        );
    }
}
