package model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Stack;
import java.util.Vector;
import java.util.Map.Entry;

public abstract class Automaton {
	
	private HashMap<String, HashMap<String,String[]>> conections;
	private Vector<HashSet<String>> partition;

	
	protected int numStates; //num ststes the machine
	protected String start; 
	protected String S[];
	protected String R[];
	protected String states[];
	
	public Automaton(int numStates, String start, String[] s, String[] r, String[] states) {
		super();
		this.numStates = numStates;
		this.start = start;
		S = s;
		R = r;
		this.states = states;
		conections = new HashMap<>();
		partition = new Vector<>();
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
	
	public void stateVisited() {
		HashMap<String, Boolean> visit = new HashMap<>();
		for(int i=0; i <numStates; i++) {
			visit.put(states[i], false);
		}
		
		Stack<String> stack = new Stack<>();
		String stk;
		stack.add(start);
		
		while (!stack.isEmpty()) {
			stk=stack.pop();
			
			if(!visit.get(stk)) {
				visit.put(stk, true);				
				
			}
			
			String s;
			for(Entry<String, String[]> e: conections.get(stk).entrySet()) {
				s= e.getValue()[0];
				if(!visit.get(s)) {
					stack.push(s);	
				}
			}
			
		}
		for(int i = 0; i<numStates; i++) {
			if(!visit.get(states[i])) {
				conections.remove(states[i]);
			}
		}

	}
	
	public void confirm(int confirm) {
		
	}

}
