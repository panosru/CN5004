package com.cn5004ap.payroll.controller.employees;

import com.cn5004ap.payroll.App;
import com.cn5004ap.payroll.common.ModalFactory;
import com.cn5004ap.payroll.common.Multiton;
import com.cn5004ap.payroll.controller.BaseController;
import com.cn5004ap.payroll.persistence.EmployeeEntity;
import com.cn5004ap.payroll.persistence.EmployeeRepository;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXPaginatedTableView;
import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import io.github.palexdev.materialfx.filter.DoubleFilter;
import io.github.palexdev.materialfx.filter.EnumFilter;
import io.github.palexdev.materialfx.filter.StringFilter;
import javafx.collections.FXCollections;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.Period;
import java.util.Comparator;
import java.util.Map;

public class ListController
    extends BaseController
{
    @FXML
    private MFXPaginatedTableView<EmployeeEntity> table;

    @FXML
    private MFXButton showBtn;

    @FXML
    private MFXButton hireBtn;

    @FXML
    private MFXButton updateBtn;

    @FXML
    private MFXButton terminateBtn;

    @FXML
    private MFXButton activateBtn;

    private final EmployeeRepository employeeRepository = Multiton.getInstance(EmployeeRepository.class);

    private ModalFactory.Modal terminateConfirmModal;

    private ModalFactory.Modal activateConfirmModal;

    @Override
    public void initialize()
    {
        table.setRowsPerPage(19);

        setupTable();

        table.getSelectionModel().setAllowsMultipleSelection(false);

        ObservableList<EmployeeEntity> employees = FXCollections.observableArrayList(
            employeeRepository.findAll()
        );

        table.setItems(employees);
        //table.autosizeColumnsOnInitialization();

        if (null == terminateConfirmModal)
            //modal = ModalFactory.create(ModalFactory.Type.SIMPLE, "Title", "Text content", ModalFactory.Level.INFO);
            terminateConfirmModal = ModalFactory.create(
                ModalFactory.Type.FXML,
                null,
                "employees/modals/brief-info",
                ModalFactory.Level.WARNING,
                Map.entry(ModalFactory.Modal.ActionButtons.confirm(), event -> terminate(getTableSelection())),
                Map.entry(ModalFactory.Modal.ActionButtons.cancel(), event -> terminateConfirmModal.close()));

        if (null == activateConfirmModal)
            activateConfirmModal = ModalFactory.create(
                ModalFactory.Type.FXML,
                null,
                "employees/modals/brief-info",
                ModalFactory.Level.WARNING,
                Map.entry(ModalFactory.Modal.ActionButtons.confirm(), event -> activate(getTableSelection())),
                Map.entry(ModalFactory.Modal.ActionButtons.cancel(), event -> activateConfirmModal.close()));
    }

    private void setupTable()
    {
        MFXTableColumn<EmployeeEntity> firstNameColumn = new MFXTableColumn<>(
            "Name", true, Comparator.comparing(EmployeeEntity::getFirstName)) {{
            setPrefWidth(80);
        }};
        MFXTableColumn<EmployeeEntity> lastNameColumn = new MFXTableColumn<>(
            "Surname", true, Comparator.comparing(EmployeeEntity::getLastName)) {{
            setPrefWidth(80);
        }};
        MFXTableColumn<EmployeeEntity> departmentColumn = new MFXTableColumn<>(
            "Department", true, Comparator.comparing(EmployeeEntity::getDepartment)) {{
            setPrefWidth(200);
        }};
        MFXTableColumn<EmployeeEntity> titleColumn = new MFXTableColumn<>(
            "Title", true, Comparator.comparing(EmployeeEntity::getTitle)) {{
            setPrefWidth(200);
        }};
        MFXTableColumn<EmployeeEntity> salaryColumn = new MFXTableColumn<>(
            "Salary", true, Comparator.comparing(EmployeeEntity::getGrossSalary));
        MFXTableColumn<EmployeeEntity> statusColumn = new MFXTableColumn<>(
            "Active", true, Comparator.comparing(EmployeeEntity::getStatus));

        firstNameColumn.setRowCellFactory(employee -> new MFXTableRowCell<>(EmployeeEntity::getFirstName));
        lastNameColumn.setRowCellFactory(employee -> new MFXTableRowCell<>(EmployeeEntity::getLastName));
        departmentColumn.setRowCellFactory(employee -> new MFXTableRowCell<>(EmployeeEntity::getDepartment));
        titleColumn.setRowCellFactory(employee -> new MFXTableRowCell<>(EmployeeEntity::getTitle));
        salaryColumn.setRowCellFactory(employee -> new MFXTableRowCell<>(
            EmployeeEntity::getGrossSalary,
            salary -> EmployeeEntity.salaryPrettify(salary, 0)
        ) {{
            setAlignment(Pos.CENTER_RIGHT);
        }});
        statusColumn.setRowCellFactory(employee -> new MFXTableRowCell<>(EmployeeEntity::getStatusUnicode) {{
            setAlignment(Pos.CENTER);
        }});

        table.getTableColumns().addAll(
            firstNameColumn,
            lastNameColumn,
            departmentColumn,
            titleColumn,
            salaryColumn,
            statusColumn
        );

        table.getFilters().addAll(
            new StringFilter<>("Name", EmployeeEntity::getFirstName),
            new StringFilter<>("Surname", EmployeeEntity::getLastName),
            new StringFilter<>("Department", EmployeeEntity::getDepartment),
            new StringFilter<>("Title", EmployeeEntity::getTitle),
            new DoubleFilter<>("Salary", EmployeeEntity::getGrossSalary),
            new EnumFilter<>("Status", EmployeeEntity::getStatus, EmployeeEntity.Status.class),
            new EnumFilter<>("Gender", EmployeeEntity::getGenderEnum, EmployeeEntity.Gender.class)
        );

        table.getSelectionModel().selectionProperty().addListener(
            (MapChangeListener<? super Integer, ? super EmployeeEntity>) e -> {
                if (e.wasAdded())
                {
                    showBtn.setDisable(false);
                    updateBtn.setDisable(false);

                    if (getTableSelection().isActive())
                    {
                        enableTerminateBtn();
                        disableActivateBtn();
                    }
                    else
                    {
                        enableActivateBtn();
                        disableTerminateBtn();
                    }
                }
                else
                {
                    showBtn.setDisable(true);
                    updateBtn.setDisable(true);
                    disableTerminateBtn();
                    disableActivateBtn();
                }
            });
    }

    private void enableTerminateBtn()
    {
        toggleTerminateBtn(true);
    }

    private void disableTerminateBtn()
    {
        toggleTerminateBtn(false);
    }

    private void toggleTerminateBtn(boolean bool)
    {
        terminateBtn.setDisable(!bool);
        terminateBtn.setVisible(bool);
        terminateBtn.setManaged(bool);
    }

    private void enableActivateBtn()
    {
        toggleActivateBtn(true);
    }

    private void disableActivateBtn()
    {
        toggleActivateBtn(false);
    }

    private void toggleActivateBtn(boolean bool)
    {
        activateBtn.setDisable(!bool);
        activateBtn.setVisible(bool);
        activateBtn.setManaged(bool);
    }

    public void show()
    {
        storeSelectedEmployeeToSession();
        loadModule("employees/view");
    }

    public void hire()
    {

    }

    public void update()
    {
        storeSelectedEmployeeToSession();
        loadModule("employees/update");
    }

    public void terminate()
    {
        terminateConfirmModal.setTitle(String.format("Terminate %s?", getTableSelection().getFullName()));
        Period employmentPeriod = getTableSelection().getEmploymentPeriod();
        briefInfoFeed(terminateConfirmModal.getDialogContent().getCenter(), employmentPeriod);
        terminateConfirmModal.show();
    }

    private void terminate(@NotNull EmployeeEntity employee)
    {
        employee.terminate();
        employeeRepository.save(employee);
        table.getSelectionModel().clearSelection();
        table.update();
        terminateConfirmModal.close();
    }

    public void activate()
    {
        activateConfirmModal.setTitle(String.format("Activate %s?", getTableSelection().getFullName()));
        Period terminationPeriod = getTableSelection().getTerminationPeriod();
        Node content = activateConfirmModal.getDialogContent().getLeft();
        ((Label) content.lookup("#yearsLabel")).setText("Terminated since:");
        briefInfoFeed(activateConfirmModal.getDialogContent().getCenter(), terminationPeriod);
        activateConfirmModal.show();
    }

    private void activate(@NotNull EmployeeEntity employee)
    {
        employee.activate();
        employeeRepository.save(employee);
        table.getSelectionModel().clearSelection();
        table.update();
        activateConfirmModal.close();
    }

    private void briefInfoFeed(@NotNull Node content, @NotNull Period period)
    {
        ((Label) content.lookup("#name")).setText(getTableSelection().getFirstName());
        ((Label) content.lookup("#surname")).setText(getTableSelection().getLastName());
        ((Label) content.lookup("#department")).setText(getTableSelection().getDepartment());
        ((Label) content.lookup("#title")).setText(getTableSelection().getTitle());
        ((Label) content.lookup("#years")).setText(String.format(
            "%s years and %d months", period.getYears(), period.getMonths()));
    }

    private void storeSelectedEmployeeToSession()
    {
        App.getUserData().setSelectedEmployee(getTableSelection());
    }

    private boolean hasTableSelection()
    {
        return !table.getSelectionModel().getSelectedValues().isEmpty();
    }

    private @Nullable EmployeeEntity getTableSelection()
    {
        if (hasTableSelection())
            return table.getSelectionModel().getSelectedValues().get(0);

        return null;
    }
}
