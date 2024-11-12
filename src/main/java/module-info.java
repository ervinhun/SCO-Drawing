module dk.easv.drawing {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.swing;
    requires org.apache.pdfbox;


    opens dk.easv.drawing to javafx.fxml;
    exports dk.easv.drawing;
}