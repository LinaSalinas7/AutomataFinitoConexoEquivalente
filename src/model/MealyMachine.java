package model;

import java.util.HashMap;
import java.util.HashSet; 
import java.util.Vector;

public class MealyMachine extends Automaton {
		
	private HashMap<String, HashMap<String,String[]>> conections;
	private Vector<HashSet<String>> partition;
	
	public MealyMachine(int numStates, String start, String[] s, String[] r, String[] states) {
		super(numStates, start, s, r, states);
		// TODO Auto-generated constructor stub
		conections=new HashMap<>();
		partition=new Vector<>();
		
	}

	public void add(String q, String s, String eDestine, String cExit) {
		String a[] = {eDestine, cExit};
		HashMap<String, String[]> t = conections.getOrDefault(q, new HashMap<>());
		t.put(s, a);
		conections.put(q, t);
	}
	
	public void equivalentMinimun() {
		stateVisited();
		
		boolean m = true;
		while (m) {
			int sizeA = partition.size();
			for(int i = 0; i < sizeA; i++) {
				confirm(i);
			}
		}
	}
	
	/*public void firstPartition() {
		
		for(int i = 0; i< partition.get(numStates).size(); i++) {
			
			
		}
	}*/
	

}
