package model;

import java.util.ArrayList;
import java.util.HashMap;


public abstract class Automaton {
	
	private HashMap<String, HashMap<String,String[]>> conections;
	

	
	protected int numStates; //numero estado de la maquina
	protected String start; //estado inicial
	protected String S[]; //Alfabeto
	protected String R[]; //Alfabeto de salida
	protected String states[];
	private ArrayList<String> partition;

	/*
	 *Metodo constructor 
	 */
	
	public Automaton(int numStates, String start, String[] s, String[] r, String[] states) {
		super();
		this.numStates = numStates;
		this.start = start;
		S = s;
		R = r;
		this.states = states;
		conections = new HashMap<>();
		partition = new ArrayList<>();
	}
	
	/*
	 * Metodos get y set
	 * */

	public int getNumStates() {
		return numStates;
	}

	public void setNumStates(int numStates) {
		this.numStates = numStates;
	}

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String[] getS() {
		return S;
	}

	public void setS(String[] s) {
		S = s;
	}

	public String[] getR() {
		return R;
	}

	public void setR(String[] r) {
		R = r;
	}

	public String[] getStates() {
		return states;
	}

	public void setStates(String[] states) {
		this.states = states;
	}
	
	public void setConections(HashMap<String, HashMap<String, String[]>> conections) {
		this.conections = conections;
	}

	public ArrayList<String> getPartition() {
		return partition;
	}

	public void setPartition(ArrayList<String> partition) {
		this.partition = partition;
	}
	
	public HashMap<String, HashMap<String, String[]>> getConections() {
		return conections;
	}
	
	/*
	 * Metodo que se encarga de recorrer el grafo y eliminar estados no son accesibles
	 * */
	
	public abstract void stateVisited();
	
	/*
	 * Metodo encargado de agregar al grafo las conexiones entre estados
	 * @param i String que representa el nombre de cada estado
	 * @param src String que representa algun caracter del alfabeto
	 * @param dst String que representa el estado De destino
	 * @param sali String que representa algunc caracter del alfabeto de salida
	 */
	
	public abstract void addGraph(String q, String s, String eDestine, String cExit);
	
	/*
	 * Metodo que realiza la segunda partición de la maquina
	 * */
	
	public abstract void secondParticion();
	
	
	public abstract void isVisited(int visit);
	
	/*
	 * metodo que retorna una matriz con la información
	 */
	
	public abstract String [][] matrixInformatión();

	
}
