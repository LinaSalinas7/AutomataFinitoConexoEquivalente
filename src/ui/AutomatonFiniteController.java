package ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.control.ScrollPane;
import model.Automaton;
import model.MealyMachine;
import model.MooreMachine;

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
    private ComboBox<String> typeMachine;

    @FXML
    private ScrollPane pane1;

    @FXML
    private ScrollPane pane2;

    
    private Automaton automaton;
    private MealyMachine mealy;
    private MooreMachine moore;
    private int rows;
    private int columns;
    private String[] arr;
    
    @FXML
    private void initialize() {
    	typeMachine.getItems().addAll("MOORE", "MEALY");
    	
    }

    @FXML
    void generate(ActionEvent event) {
    	rows = Integer.parseInt(stateQ.getText());
    	arr = inputAlphabetS.getText().split(",");
    	columns = inputAlphabetS.getLength();
    	if(rows <= 27) {
    		GridPane matrix1 = new GridPane();
    		
    		matrix1.setHgap(columns);
    		matrix1.setVgap(rows);
    		
    		pane1.setContent(matrix1);
    		
    	}
    	

    }
    
    public void miniMachine() {
    	
    }
    
    public void readMoore() {
    	
    }
    
    public void readMealy() {
    	
    }


}
