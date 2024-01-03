module com.example.project12 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.example.project12 to javafx.fxml;
    exports com.example.project12;
}