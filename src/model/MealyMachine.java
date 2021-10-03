package model;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Stack;
import java.util.Vector;

public class MealyMachine extends Automaton {
		
	private HashMap<String, HashMap<String,String[]>> conections;
	private Vector<HashSet<String>> partition;
	
	public MealyMachine(int numStates, String start, String[] s, String[] r, String[] states) {
		super(numStates, start, s, r, states);
		// TODO Auto-generated constructor stub
		conections=new HashMap<>();
		//partition=new Vector<>();
		
	}

	
	public void addGraph(String q, String s, String eDestine, String cExit) {
		String a[] = {eDestine, cExit};
		HashMap<String, String[]> t = conections.getOrDefault(q, new HashMap<>());
		t.put(s, a);
		conections.put(q, t);
	}
	
	
	public void equivalentMinimun() {
		stateVisited();
		secondParticion();
		
		boolean m = true;
		while (m) {
			int sizeA = partition.size();
			for(int i = 0; i < sizeA; i++) {
				isVisited(i);
			}
			if (sizeA == partition.size()) {
				m = false;
			} else {
				sizeA = partition.size();
			}
		}
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
		HashMap<String, HashSet<String>> hashMap = new HashMap<>();
		for(Map.Entry<String, HashMap<String, String[]>> pos : conections.entrySet()) {
			String keyString = "";
			for (Map.Entry<String, String[]> pos2 : pos.getValue().entrySet()) {
				keyString += pos2.getValue()[1] + " ";
			}
			keyString = keyString.trim();
			HashSet<String> set = hashMap.getOrDefault(keyString, new HashSet<>());
			set.add(pos.getKey());
			hashMap.put(keyString, set);
		}
	}

	@Override

	public void isVisited(int visit) {
		int l = partition.size();
		boolean isCreate = false;
		String last = "";
		LinkedList<String> queue = new LinkedList<>();
		
		for (String strings : partition.get(visit)) {
			if(!last.isEmpty()) {
				boolean exist = true;
				for (HashSet<String> m : partition) {
					for(int i=0; i< S.length && exist; i++) {
						if(!m.contains(conections.get(last).get(S[i])[0]) 
								&& m.contains(conections.get(strings).get(S[i])[0])){
							exist=false;
							
						}
						if (m.contains(conections.get(last).get(S[i])[0]) 
								&& !m.contains(conections.get(strings).get(S[i])[0])) {
							exist= true;
						}
					}
				}
				if(!exist && !isCreate) {
					partition.add(new HashSet<>());
					isCreate = true;
				}
				if(!exist && isCreate) {
					partition.get(l).add(strings);
					queue.offer(strings);
				}
				
			}
			if(isCreate) {
				while (!queue.isEmpty()) {
					partition.get(visit).remove(queue.poll());
				}
				isVisited(l);
			}
		}
		
	}

	@Override
	public String[][] matrixInformatión() {
		String matrix[][] = new String[partition.size()][S.length + 1];
		HashMap<String, String> r = new HashMap<>();

		for (int i = 0; i < matrix.length; i++) {
			Arrays.fill(matrix[i], "");
		}

		int conta = 0;
		for (HashSet<String> s : partition) {
			for (String st : s) {
				r.put(st, "P" + conta);
			}
			conta++;
		}

		for (int i = 0; i < matrix.length; i++) {
			matrix[i][0] = "P" + (i);
			for (int j = 1; j < matrix[0].length; j++) {
				for (String s : partition.get(i)) {
					matrix[i][j] = r.get(conections.get(s).get(S[j - 1])[0]) + " " + conections.get(s).get(S[j - 1])[1];
					break;
				}
			}
		}
		return matrix;
	}

}
