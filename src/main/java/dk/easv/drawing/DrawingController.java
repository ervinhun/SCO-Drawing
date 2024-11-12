package dk.easv.drawing;

import dk.easv.drawing.be.Shapes;
import dk.easv.drawing.bll.DrawingLogic;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.regex.Pattern;

public class DrawingController {
    @FXML
    private ChoiceBox<String> cbShape;

    @FXML
    private TextField txtSize;

    @FXML
    private Button btnAdd;

    @FXML
    private ListView<Shapes> lstShapes;

    @FXML
    private Canvas canvas;

    @FXML
    private ChoiceBox<String> cbPattern;

    @FXML
    private Button btnDraw;

    @FXML
    private Button btnClear;

    public DrawingController() {

    }


    @FXML
    public void initialize() {
        assert cbShape != null;
        cbShape.getItems().addAll("Circle", "Rectangle", "Square", "Triangle");
        cbShape.getSelectionModel().select(0);

        assert cbPattern != null;
        cbPattern.getItems().addAll("Grid", "Cross", "Random");
        cbPattern.getSelectionModel().select(0);
        txtSize.setText(String.valueOf(20));

        lstShapes.setOnMouseClicked(this::handleDoubleClick);
    }

    private void handleDoubleClick(MouseEvent event) {
        if (!lstShapes.getItems().isEmpty()) {
            Shapes shape = lstShapes.getSelectionModel().getSelectedItem();
            lstShapes.getItems().remove(shape);
        }
    }

    @FXML
    private void ButtonAddClicked() {
        if (checkForValidSize(txtSize.getText()))
            lstShapes.getItems().add(new Shapes(Integer.parseInt(txtSize.getText()), cbShape.getValue()));
        else
            System.out.println("Size is not Valid");
    }

    @FXML
    private void btnDrawClicked() {
        DrawingLogic drawingLogic = new DrawingLogic(lstShapes.getItems(), cbPattern.getValue(), canvas);
    }
    @FXML
    private void btnClearClicked() {
        lstShapes.getItems().clear();
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }

    private boolean checkForValidSize(String size) {
        if (size.isEmpty() || !Pattern.matches("[0-9]+", size) )
            return false;
        else {
            int numSize = Integer.parseInt(size);
            if (numSize < 0 || numSize > canvas.getHeight())
                return false;
            else
                return true;
        }
    }
}