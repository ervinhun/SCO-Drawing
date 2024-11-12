package dk.easv.drawing;

import dk.easv.drawing.be.Shapes;
import dk.easv.drawing.bll.DrawingLogic;
import dk.easv.drawing.dal.CanvasSaver;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

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

    @FXML
    private ChoiceBox<String> cbLine;

    @FXML
    private CheckBox ckFill;

    @FXML
    private ChoiceBox<String> cbColor;

    @FXML
    private Label lblCount;

    @FXML
    private Button btnSave;

    public DrawingController() {

    }


    @FXML
    public void initialize() {
        assert cbShape != null;
        cbShape.getItems().addAll("Circle", "Rectangle", "Square", "Triangle", "Star");
        cbShape.getSelectionModel().select(0);

        assert cbPattern != null;
        cbPattern.getItems().addAll("Grid", "Cross", "Random");
        cbPattern.getSelectionModel().select(0);
        txtSize.setText(String.valueOf(20));

        lstShapes.setOnMouseClicked(this::handleDoubleClick);

        assert cbLine != null;
        for (int i = 1; i <= 10 ; i++)
            cbLine.getItems().add(String.valueOf(i) + " px");
        cbLine.getSelectionModel().select(1);

        cbColor.getItems().addAll("Black", "Red", "Green", "Blue");
        cbColor.getSelectionModel().select(0);

    }

    private void handleDoubleClick(MouseEvent event) {
        if (!lstShapes.getItems().isEmpty()) {
            Shapes shape = lstShapes.getSelectionModel().getSelectedItem();
            lstShapes.getItems().remove(shape);
        }
    }

    @FXML
    private void ButtonAddClicked() {
        String lineSize = String.valueOf(cbLine.getItems().indexOf(cbLine.getValue())+1);

        if (checkForValidSize(txtSize.getText()) && checkForValidSize(lineSize)) {
            System.out.println(cbLine.getId()+1);
            lstShapes.getItems().add(
                    new Shapes(Integer.parseInt(txtSize.getText()), cbShape.getValue(),
                            Integer.parseInt(lineSize), (ckFill.isSelected()), cbColor.getValue()));
            lblCount.setVisible(true);
            lblCount.setText("Count: " + String.valueOf(lstShapes.getItems().size()));
        }
        else
            System.out.println("Size or line width is not Valid");
    }

    @FXML
    private void btnDrawClicked() {
        DrawingLogic drawingLogic = new DrawingLogic(lstShapes.getItems(), cbPattern.getValue(), canvas);
        btnSave.setDisable(false);
    }
    @FXML
    private void btnClearClicked() {
        lstShapes.getItems().clear();
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        lblCount.setVisible(false);
        btnSave.setDisable(true);
    }

    @FXML
    private void checkBoxFilled() {
       // cbColor.setVisible(ckFill.isSelected());
    }

    @FXML
    private void btnSaveClicked() throws IOException {
        CanvasSaver.saveCanvasAsPng(canvas, "myCanvasImage.png");
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