module s25.cs151.application {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires org.apache.commons.lang3;

    opens s25.cs151.application to javafx.fxml;
    exports s25.cs151.application;
    exports Controller;
    opens Controller to javafx.fxml;
    exports Model;
    opens Model to javafx.fxml;
}