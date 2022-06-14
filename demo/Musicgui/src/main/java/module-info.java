module com.example.musicgui {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.musicgui to javafx.fxml;
    exports com.example.musicgui;
}