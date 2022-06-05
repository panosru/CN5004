package com.cn5004ap.payroll.controller;

import com.cn5004ap.payroll.common.Multiton;
import com.cn5004ap.payroll.common.TransactionsTable;
import com.cn5004ap.payroll.persistence.TransactionEntity;
import com.cn5004ap.payroll.persistence.TransactionRepository;
import io.github.palexdev.materialfx.controls.MFXPaginatedTableView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

public class TransactionsController
    extends BaseController
{
    @FXML
    private MFXPaginatedTableView<TransactionEntity> table;

    private final TransactionRepository transactionRepository = Multiton.getInstance(TransactionRepository.class);

    @Override
    public void initialize()
    {
        table.setRowsPerPage(21);
        (new TransactionsTable(table, true)).setup();
        table.getSelectionModel().setAllowsMultipleSelection(false);

        ObservableList<TransactionEntity> transactions = FXCollections.observableArrayList(
            transactionRepository.findAll()
        );

        table.setItems(transactions);
    }
}
