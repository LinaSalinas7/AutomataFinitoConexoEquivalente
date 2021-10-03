package model;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class MooreMachine extends Automaton {
	
	private HashMap<String, HashMap<String,String[]>> conections;
	//private Vector<HashSet<String>> partition;

	public MooreMachine(int numStates, String start, String[] s, String[] r, String[] states) {
		super(numStates, start, s, r, states);
		// TODO Auto-generated constructor stub
		conections = new HashMap<>();
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
			for(Map.Entry<String, String[]> e: conections.get(stk).entrySet()) {
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

	@Override
	public void secondParticion() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void isVisited(int visit) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String[][] matrixInformatión() {
		// TODO Auto-generated method stub
		return null;
	}
	

}
