module com.example.ing3_projet_java {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires java.management;
    requires java.desktop;
    requires eu.iamgio.animated;


    opens Vue to javafx.fxml;
    opens Modele to javafx.base;
    exports Vue;
    exports Controller;
    opens Controller to javafx.fxml;
    exports Vue.Calendar;
    opens Vue.Calendar to javafx.fxml;
}