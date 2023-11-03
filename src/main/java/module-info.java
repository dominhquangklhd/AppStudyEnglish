module app {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.sql;
    requires jlayer;
    requires java.desktop;

    //opens com.example.test.Controller to javafx.fxml;
    //exports com.example.test.Model;
    //exports com.example.test.View;
    opens app to javafx.fxml;
    exports app;
    exports app.Controller;
    opens app.Controller to javafx.fxml;
}