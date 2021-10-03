package model;

import java.util.ArrayList;
import java.util.HashMap;


public abstract class Automaton {
	
	private HashMap<String, HashMap<String,String[]>> conections;
	

	
	protected int numStates; //num ststes the machine
	protected String start; 
	protected String S[];
	protected String R[];
	protected String states[];
	private ArrayList<String> partition;

	
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
	
	public abstract void stateVisited();
	
	public abstract void addGraph(String q, String s, String eDestine, String cExit);
	
	public abstract void secondParticion();
	
	public abstract void isVisited(int visit);
	
	public abstract String [][] matrixInformatión();

	public HashMap<String, HashMap<String, String[]>> getConections() {
		return conections;
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

}
