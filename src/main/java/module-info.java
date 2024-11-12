module dk.easv.drawing {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens dk.easv.drawing to javafx.fxml;
    exports dk.easv.drawing;
}