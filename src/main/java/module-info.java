module com.jsulbaran.apps.personalfilemanager {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires java.desktop;
    requires java.logging;

    opens com.jsulbaran.apps.personalfilemanager to javafx.fxml;
    exports com.jsulbaran.apps.personalfilemanager;
}