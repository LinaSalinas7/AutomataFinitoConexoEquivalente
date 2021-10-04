package model;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Stack;
import java.util.Vector;

public class MooreMachine extends Automaton {
	
	private HashMap<String, HashMap<String,String>> conections;
	private HashMap<String, String> result; // Salida de cada estado resultado
	private HashMap<String, HashSet<String>> firstPartition;
	private Vector<HashSet<String>> partition;

	public MooreMachine(int numStates, String start, String[] s, String[] r, String[] states) {
		super(numStates, start, s, r, states);
		// TODO Auto-generated constructor stub
		conections = new HashMap<>();
		result = new HashMap<>();
		firstPartition = new HashMap<>();
		partition = new Vector<>();
	}
	


	@Override
	public void addGraph(String q, String s, String eDestine, String cExit) {
		// TODO Auto-generated method stub
		result.put(q, cExit);
		HashMap<String, String> hashMap = conections.getOrDefault(q, new HashMap<>());
		hashMap.put(s, eDestine);
		conections.put(q, hashMap);
	}
	
	public void equivalentMinimun() {
		stateVisited();
		secondParticion();
		
		for(Map.Entry<String, HashSet<String>> entry : firstPartition.entrySet()) {
			partition.add(entry.getValue());
		}	
		boolean df= true;
		while (df) {
			int tamaño = partition.size();
			for(int j=0; j<partition.size(); j++) {
				isVisited(j);
			}
			if(tamaño == partition.size()) {
				df = false;
			} else {
				tamaño = partition.size();
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
			for(Map.Entry<String, String> e: conections.get(stk).entrySet()) {
				s = e.getValue();
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
		HashMap<String, HashSet<String>> hashMap = new HashMap<>();
		for (Map.Entry<String, String> entry : result.entrySet()) {
			HashSet<String> set = hashMap.getOrDefault(entry.getValue(), new HashSet<>());
			set.add(entry.getKey());
			hashMap.put(entry.getValue(), set);
		}
		firstPartition = hashMap;
	}

	@Override
	public void isVisited(int visit) {
		// TODO Auto-generated method stub
		int tamaño = partition.size();
		boolean isCreate = false;
		String last = "";
		LinkedList<String> queue = new LinkedList<>();

		for (String strings : partition.get(visit)) {
			if (!last.isEmpty()) {
				boolean exist = true;
				for (HashSet<String> c : partition) {
					for (int i = 0; i < S.length && exist; i++) {
						if (!c.contains(conections.get(last).get(S[i])) && c.contains(conections.get(strings).get(S[i]))) {
							exist = false;
						}
						if (c.contains(conections.get(last).get(S[i])) && !c.contains(conections.get(strings).get(S[i]))) {
							exist = false;
						}
					}
				}
				if (!exist && !isCreate) {
					partition.add(new HashSet<>());
					isCreate = true;
				}
				if (!exist && isCreate) {
					partition.get(tamaño).add(strings);
					queue.offer(strings);
				}
			}
			if (!isCreate) {
				last = strings;
			}
		}
		if (isCreate) {
			while (!queue.isEmpty()) {
				partition.get(visit).remove(queue.poll());
			}
			isVisited(tamaño);
		}		
	}
	
	@Override
	public String[][] matrixInformatión() {
		String matrix[][] = new String[partition.size()][S.length + 2];

		for (int i = 0; i < matrix.length; i++) {
			Arrays.fill(matrix[i], "");
		}
		HashMap<String, String> r = new HashMap<>();
		int cont = 0;
		for (HashSet<String> s :partition) {
			for (String st : s) {
				r.put(st, "P" + cont);
			}
			cont++;
		}
		for (int i = 0; i < matrix.length; i++) {
			matrix[i][0] = "P" + (i);
			String temp = "";
			for (int j = 1; j < matrix[0].length - 1; j++) {
				for (String s : partition.get(i)) {
					matrix[i][j] = r.get(conections.get(s).get(S[j - 1]));
					temp = s;
					break;
				}
			}
			matrix[i][S.length + 1] = result.get(temp);
		}
		return matrix;
	}
	
	public String[] arrSalida() {
		String rst[] = new String[S.length + 1];

		Arrays.fill(rst, "");

		for (int i = 1; i < S.length + 1; i++) {
			rst[i] = S[i - 1];
		}

		return rst;
	}
}
