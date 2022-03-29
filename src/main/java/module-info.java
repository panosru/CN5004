module com.payroll {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.jetbrains.annotations;
    requires org.apache.commons.io;
    requires org.apache.commons.lang3;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;

    opens com.payroll to javafx.fxml;
    exports com.payroll;
    opens com.payroll.controller to javafx.fxml;
    exports com.payroll.controller;
}
