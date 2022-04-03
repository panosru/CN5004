module com.payroll {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.jetbrains.annotations;
    requires org.apache.commons.io;
    requires org.apache.commons.lang3;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires org.hibernate.orm.core;
    requires jakarta.persistence;
    requires jakarta.transaction;
    requires java.naming;
    requires java.sql;
    requires java.logging;
    requires java.prefs;
    requires bcrypt;

    opens com.payroll to javafx.fxml;
    exports com.payroll;
    opens com.payroll.persistence to org.hibernate.orm.core;
    exports com.payroll.persistence;
    opens com.payroll.controller to javafx.fxml;
    exports com.payroll.controller;
}
