package ui;

import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
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
    private int rows;
    private int columns;
    private String[] arr;
    
    private GridPane gridP2;
    
    private TextField[][] tf;
    
    private String[] arrStimulus;
    
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
    		
    		try {
                tf = new TextField[columns + 1][rows];

                fillHeaders(matrix1);

                for (int i = 1; i < rows + 1; i++) {
                    TextField ta = new TextField((char) (i + 64) + "");
                    ta.setEditable(false);
                    ta.setStyle(ta.getStyle() + "\n-fx-background-color: rgba(125, 156, 205, 0.84)");
                    ta.setPrefWidth(45);
                    matrix1.add(ta, 0, i);
                    for (int j = 1; j < columns + 1; j++) {
                        ta = new TextField("");
                        ta.setPrefWidth(45);
                        ta.setPromptText("A" + (typeMachine.getValue().equals("MEALY") ? ",a" : ""));
                        tf[j - 1][i - 1] = ta;
                        matrix1.add(ta, j, i);
                    }
                    if (typeMachine.getValue().equals("MOORE")) {
                        ta = new TextField("");
                        ta.setPrefWidth(45);
                        ta.setStyle(ta.getStyle() + "\n-fx-background-color:  rgb(43,120,10)");
                        ta.setPromptText("a");
                        tf[columns][i - 1] = ta;
                        matrix1.add(ta, columns + 1, i);
                    }
                }
            } catch (NegativeArraySizeException | IllegalArgumentException e) {
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setContentText(e.getMessage());
                a.show();
            }
        } else {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("Although the model lets you manage many states, the GUI only supports a maximum of 26 states for visual reasons");
            a.show();
        }
    		
    	}
    
    public void miniMachine() {
    	
    	gridP2 = new GridPane();
        gridP2.setHgap(3);
        gridP2.setVgap(3);

        fillHeaders(gridP2);
        if(typeMachine.getValue().equals("MOORE")) {
            readMoore();
        } else {
            readMealy();
        }
        pane2.setContent(gridP2);
    	
    }
    
    public void readMoore() {
    	
    	String[][] matrix = readTextFields("MOORE");

        MooreMachine<Character, Character, Character> mooreMachine = new MooreMachine<>('A', matrix[matrix.length - 1][0].charAt(0));

        for (int j = 0; j < matrix[0].length; j++) {
            char temp = (char) (j + 65);
            mooreMachine.insertState(temp, matrix[matrix.length - 1][j].charAt(0));
        }
        for (int i = 0; i < matrix.length - 1; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                char temp = (char) (j + 65);
                mooreMachine.relate(temp, matrix[i][j].charAt(0), arrStimulus[i].charAt(0));
            }
        }

        mooreMachine = mooreMachine.minimize();
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        int minStates = mooreMachine.getOrder();
        a.setContentText("The Moore machine was minimized.\nThe equivalent minimized automaton contains " + minStates + " states");
        a.show();

        ArrayList<Character> minStatesList = mooreMachine.getVertices();
        for (int i = 1; i < minStates + 1; i++) {
            char current = fillGridPaneWithRepresentativeStateForTheRow(minStatesList, i);
            TextField ta;
            ta = new TextField("" + mooreMachine.getResponse(current));
            ta.setStyle(ta.getStyle() + "\n-fx-background-color:  rgba(70, 214, 70, 0.75)");
            ta.setEditable(false);
            ta.setPrefWidth(45);
            gridP2.add(ta, columns + 1, i);
            for (int j = 1; j < columns + 1; j++) {
                ta = new TextField("" + mooreMachine.stateTransitionFunction(current, arrStimulus[j - 1].charAt(0)));
                ta.setEditable(false);
                ta.setPrefWidth(45);
                gridP2.add(ta, j, i);
            }
        }
    	
    }
    
    public void readMealy() {
    	
    	String[][] matrix = readTextFields("MEALY");
        MealyMachine<Character, Character, Character> mealyMachine = new MealyMachine<>('A');

        for (int j = 1; j < matrix[0].length; j++) {
            char temp = (char) (j + 65);
            mealyMachine.insertState(temp);
        }
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                char temp = (char) (j + 65);
                String[] cell = matrix[i][j].split(",");
                mealyMachine.relate(temp, cell[0].charAt(0), arrStimulus[i].charAt(0), cell[1].charAt(0));
            }
        }

        mealyMachine = mealyMachine.minimize();
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        int minStates = mealyMachine.getOrder();
        a.setContentText("The Mealy machine was minimized.\nThe equivalent minimized automaton contains " + minStates + " states");
        a.show();

        ArrayList<Character> minStatesList = mealyMachine.getVertices();
        for (int i = 1; i < minStates + 1; i++) {
            char current = fillGridPaneWithRepresentativeStateForTheRow(minStatesList, i);
            TextField ta;
            for (int j = 1; j < columns + 1; j++) {
                char stimulus = arrStimulus[j - 1].charAt(0);
                String cell = "" + mealyMachine.stateTransitionFunction(current, stimulus);
                cell += "," + mealyMachine.getResponse(current, stimulus);
                ta = new TextField(cell);
                ta.setEditable(false);
                ta.setPrefWidth(45);
                gridP2.add(ta, j, i);
            }
        }
    	
    }
    
    private void fillHeaders(GridPane grid) {
        for (int i = 1; i < columns + 1; i++) {
            TextField ta = new TextField(arrStimulus[i - 1]);
            ta.setEditable(false);
            ta.setStyle(ta.getStyle() + "\n-fx-background-color: rgba(205, 125, 125, 0.84)");
            ta.setPrefWidth(45);
            grid.add(ta, i, 0);
        }
    }
    
    private char fillGridPaneWithRepresentativeStateForTheRow(ArrayList<Character> minStatesList, int i) {
        char current = minStatesList.get(i - 1);
        TextField ta = new TextField(current + "");
        ta.setEditable(false);
        ta.setPrefWidth(45);
        ta.setStyle(ta.getStyle() + "\n-fx-background-color: rgba(125, 156, 205, 0.84)");
        gridP2.add(ta, 0, i);
        return current;
    }
    
    private String[][] readTextFields(String type) {
        String[][] matrix = new String[type.equals("MOORE") ? columns + 1 : columns][rows];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                matrix[i][j] = tf[i][j].getText();
            }
        }
        return matrix;
    }


}
