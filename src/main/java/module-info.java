module com.example.lera {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.lera to javafx.fxml;
    exports com.example.lera;
}