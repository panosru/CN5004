module com.cn5004ap.payroll {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.prefs;
    requires jakarta.persistence;
    requires org.jetbrains.annotations;
    requires org.hibernate.orm.core;
    requires bcrypt;

    opens com.cn5004ap.payroll.controller to javafx.fxml;
    opens com.cn5004ap.payroll.persistence to org.hibernate.orm.core;

    exports com.cn5004ap.payroll;
    exports com.cn5004ap.payroll.controller;
    exports com.cn5004ap.payroll.persistence;
}
