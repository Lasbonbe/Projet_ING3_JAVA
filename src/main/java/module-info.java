module com.example.ing3_projet_java {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires java.management;

    opens Vue to javafx.fxml;
    exports Vue;
    exports Controler;
    opens Controler to javafx.fxml;
}