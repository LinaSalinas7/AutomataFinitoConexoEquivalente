package ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

public class AutomatonFiniteController {

    @FXML
    private BorderPane borderPane;

    @FXML
    private GridPane gridPane;

    @FXML
    private TextField stateQ;

    @FXML
    private TextField inputAlphabetS;

    @FXML
    private Button generate;

    @FXML
    private MenuItem mealy;

    @FXML
    private MenuItem mooreMachine;

    @FXML
    private GridPane matrix;

    @FXML
    void generate(ActionEvent event) {

    }

    @FXML
    void initMealyMachine(ActionEvent event) {

    }

    @FXML
    void initMooreMachine(ActionEvent event) {

    }

}
