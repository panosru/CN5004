module com.cn5004ap.payroll {
    requires java.prefs;
    requires javafx.base;
    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;
    requires org.kordamp.ikonli.core;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.ikonli.fontawesome5;
    requires jakarta.persistence;
    requires org.jetbrains.annotations;
    requires org.hibernate.orm.core;
    requires org.hibernate.validator;
    requires bcrypt;
    requires MaterialFX;
    requires org.json.simple;

    opens com.cn5004ap.payroll.controller to javafx.fxml;
    opens com.cn5004ap.payroll.persistence to org.hibernate.orm.core;
    opens com.cn5004ap.payroll.view to org.kordamp.ikonli.javafx;

    exports com.cn5004ap.payroll;
    exports com.cn5004ap.payroll.common;
    exports com.cn5004ap.payroll.controller;
    exports com.cn5004ap.payroll.persistence;
    exports com.cn5004ap.payroll.controller.employees;
    opens com.cn5004ap.payroll.controller.employees to javafx.fxml;
}
