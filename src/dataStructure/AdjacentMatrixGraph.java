package dataStructure;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

import dataStructure.IGraph;
import dataStructure.Vertex.Color;

public class AdjacentMatrixGraph<K extends Comparable, V> implements IGraph<K, V>{
	
	private boolean isDirected;
	private boolean isWeighted;
	private int numberOfVertices;
	private int numberOfEdges;
	 
	private List<Vertex<K, V>> vertexs;
	private List<ArrayList<Integer>> adjacentMatrix;
	private List<ArrayList<Double>> weightMatrix;
	private Map<K, Vertex<K, V>> graph;
	
	public AdjacentMatrixGraph(boolean isDirected, boolean isWeighted) {
			
			this.isDirected = isDirected;
			this.isWeighted = isWeighted;
			this.numberOfVertices = 0;
			this.numberOfEdges = 0;
			
			this.vertexs = new ArrayList<Vertex<K,V>>();
			this.adjacentMatrix = new ArrayList<ArrayList<Integer>>();
			this.weightMatrix = new ArrayList<ArrayList<Double>>();
			this.graph = new HashMap<K, Vertex<K, V>>();
	}

	@Override
	public boolean isDirected() {
		return this.isDirected;
	}

	@Override
	public boolean isWeighted() {
		return this.isWeighted;
	}

	@Override
	public void addVertex(K key, V value) {
		if (!isInGraph(key)) {
			Vertex<K,V> vertex = new Vertex<K,V>(key, value);
			graph.put(key, vertex);
			
			ArrayList<Integer> adj = new ArrayList<Integer>();
			ArrayList<Double> we = new ArrayList<Double>();
			
			for (int i = 0; i < adjacentMatrix.size(); i++) {
				adjacentMatrix.get(i).add(0);
				weightMatrix.get(i).add(INFINITY);
			}
			for (int i = 0; i < vertexs.size(); i++) {
				adj.add(0);
				we.add(INFINITY);
			}
			we.set(we.size() - 1, 0.0);
			adjacentMatrix.add(adj);
			weightMatrix.add(we);

			numberOfVertices++;
		}
	}

	@Override
	public void addEdge(Vertex<K, V> source, Vertex<K, V> destination, double weight) {
		if (isInGraph(source.getKey()) && isInGraph(destination.getKey())) {
			double w = 0.0;
			
			if (isWeighted) {
				w = weight;
			}else {
				w = 1.0;
			}
			
			adjacentMatrix.get(getIndexOf(source)).set(getIndexOf(destination), 1);
			weightMatrix.get(getIndexOf(source)).set(getIndexOf(destination), w);
			
			if (!isDirected) {
				adjacentMatrix.get(getIndexOf(destination)).set(getIndexOf(source), 1);
				weightMatrix.get(getIndexOf(destination)).set(getIndexOf(source), w);
			}
			numberOfEdges++;
		}
	}

	@Override
	public void removeVertex(Vertex<K, V> toRemove) {
		if (isInGraph(toRemove.getKey())) {
			int index = getIndexOf(toRemove);
			
			for (int i = 0; i < adjacentMatrix.size(); i++) {
				adjacentMatrix.get(i).remove(index);
				weightMatrix.get(i).remove(index);
			}
			
			adjacentMatrix.remove(index);
			weightMatrix.remove(index);
			
			vertexs.remove(toRemove);
			graph.remove(toRemove.getKey());
			numberOfVertices--;
		}
	}

	@Override
	public void removeEdge(Vertex<K, V> source, Vertex<K, V> destination) {
		if (isInGraph(source.getKey()) && isInGraph(destination.getKey())) {
			
			adjacentMatrix.get(getIndexOf(source)).set(getIndexOf(destination), 0);
			weightMatrix.get(getIndexOf(source)).set(getIndexOf(destination), INFINITY);
			
			if (!isDirected()) {
				adjacentMatrix.get(getIndexOf(destination)).set(getIndexOf(source), 0);
				weightMatrix.get(getIndexOf(destination)).set(getIndexOf(source), INFINITY);
			}
			numberOfEdges--;
		}
	}

	@Override
	public boolean areAdjacent(Vertex<K, V> vertex1, Vertex<K, V> vertex2) {
		boolean areAdjacent = false;
		
		if (isInGraph(vertex1.getKey()) && isInGraph(vertex2.getKey())) {
			if (vertexAdjacent(vertex1).contains(vertex2) || vertexAdjacent(vertex2).contains(vertex1)) {
				areAdjacent = true;
			}
		}
		return areAdjacent;
	}

	@Override
	public boolean isInGraph(K key) {
		return graph.containsKey(key);
	}

	@Override
	public int getNumberOfVertexs() {
		return numberOfVertices;
	}

	@Override
	public int getNumberOfEdges() {
		return numberOfEdges;
	}

	@Override
	public double getEdgeWeight(Vertex<K, V> source, Vertex<K, V> destination) {
		double w = 0;
		int indX = getIndexOf(source);
		int indY = getIndexOf(destination);
		
		if (isInGraph(source.getKey()) && isInGraph(destination.getKey())) {
			w = weightMatrix.get(indX).get(indY);
		}
		return w;
	}

	@Override
	public void setEdgeWeight(Vertex<K, V> source, Vertex<K, V> destination, double weight) {
		int indX = getIndexOf(source);
		int indY = getIndexOf(destination);
		
		if (isInGraph(source.getKey()) && isInGraph(destination.getKey()) && isDirected) {
			weightMatrix.get(indX).set(indY, weight);

			if (!isDirected()) {
				weightMatrix.get(indY).set(indX, weight);
			}
		}
	}

	@Override
	public List<Vertex<K, V>> vertexAdjacent(Vertex<K, V> vertex) {
		List<Vertex<K, V>> list = new ArrayList<Vertex<K,V>>();
		int index = getIndexOf(vertex);
		
		for (int i = 0; i < adjacentMatrix.get(index).size(); i++) {
			if (adjacentMatrix.get(index).get(i) == 1) {
				list.add(vertexs.get(i));
			}
		}
		return list;
	}

	@Override
	public List<Vertex<K, V>> getVertices() {
		return vertexs;
	}

	private int getIndexOf(Vertex<K, V> vertex) {
		int index = -1;
		boolean stop = false;
		
		for (int i = 0; i < vertexs.size() && !stop; i++) {
			if (vertexs.get(i).getKey().compareTo(vertex.getKey()) == 0) {
				index = i;
				stop = true;
			}
		}
		return index;
	}
	
	
	
	public void bfs(Vertex<K,V> s) {
		for (Vertex<K,V> u : vertexs) {
			u.setColor(Color.WHITE);
			u.setInitialDistance(INFINITY);
			u.setPredecessor(null);
		}
		s.setColor(Color.GRAY);
		s.setInitialDistance(0);
		s.setPredecessor(null);
		Queue<Vertex<K,V>> q = new LinkedList<>();
		q.offer(s);
		while (!q.isEmpty()) {
			Vertex<K,V> u = q.poll();
			List<Vertex<K,V>> adjacent = vertexAdjacent(u);
			for (int i = 0; i < adjacent.size(); i++) {
				Vertex<K,V> v = adjacent.get(i);
				if (v.getColor() == Color.WHITE) {
					v.setColor(Color.GRAY);
					v.setInitialDistance(u.getInitialDistance() + 1);
					v.setPredecessor(u);
					q.offer(v);
				}
			}
			u.setColor(Color.BLACK);
		}
	}
	
	
	public void dfs() {
		for (Vertex<K,V> u : vertexs) {
			u.setColor(Color.WHITE);
			u.setPredecessor(null);
		}
		
		int time = 0;
		for (Vertex<K,V> u : vertexs) {
			if (u.getColor().compareTo(Color.WHITE) == 0) {
				time = dfsVisit(u, time);
			}
		}
	}

	private int dfsVisit(Vertex<K,V> u, int time) {
		time++;
		u.setInitialDistance(time);
		u.setColor(Color.GRAY);
		List<Vertex<K,V>> adjacent = vertexAdjacent(u);
		for (int i = 0; i < adjacent.size(); i++) {
			Vertex<K,V> v = adjacent.get(i);
			if (v.getColor().compareTo(Color.WHITE) == 0) {
				v.setPredecessor(u);
				time = dfsVisit(v, time);
			}
		}
		u.setColor(Color.BLACK);
		time++;
		u.setFinalDistance(time);
		return time;
	}
	
	
	
	
	private void initSingleSource(Vertex<K,V> s) {
		for (Vertex<K,V> u : vertexs) {
			u.setInitialDistance(INFINITY);
			u.setPredecessor(null);
		}
		s.setInitialDistance(0);
	}

	public void dijkstra(Vertex<K, V> s) {
		initSingleSource(s);
		PriorityQueue<Vertex<K,V>> queue = new PriorityQueue<>();
		queue.add(s);
		while (!queue.isEmpty()) {
			Vertex<K,V> u = queue.poll();
			List<Vertex<K,V>> adjacent = vertexAdjacent(u);
			for (Vertex<K,V> v : adjacent) {

				double weight = getEdgeWeight(u, v);

				
				double distanceFromU = u.getInitialDistance() + weight;
				if (distanceFromU < v.getInitialDistance()) {
					queue.remove(v);
					v.setInitialDistance(distanceFromU);
					v.setPredecessor(u);
					queue.add(v);

				}
			}
		}
	}
	
	
	
	private double[][] getWeightsMatrix() {
		double[][] weights = new double[vertexs.size()][vertexs.size()];
		for (int i = 0; i < weights.length; i++) {
			for (int j = 0; j < weights.length; j++) {
				weights[i][j] = weightMatrix.get(i).get(j);
			}
		}
		return weights;
	}
	
	public double[][] floydwarshall() {
		double[][] weights = getWeightsMatrix();
		for (int k = 0; k < vertexs.size(); k++) {
			for (int i = 0; i < vertexs.size(); i++) {
				for (int j = 0; j < vertexs.size(); j++) {
					weights[i][j] = Math.min(weights[i][j], weights[i][k] + weights[k][j]);
				}
			}
		}
		return weights;
	}
	
	
	
	public void prim(Vertex<K,V> r) {
		for (Vertex<K,V> u : vertexs) {
			u.setInitialDistance(INFINITY);
			u.setColor(Color.WHITE);
		}
		r.setInitialDistance(0);
		r.setPredecessor(null);
		PriorityQueue<Vertex<K,V>> queue = new PriorityQueue<>();
		for (Vertex<K,V> u : vertexs) {
			queue.add(u);
		}
		while (!queue.isEmpty()) {
			Vertex<K,V> u = queue.poll();
			List<Vertex<K,V>> adjacent = vertexAdjacent(u);
			for (Vertex<K,V> v : adjacent) {
				if ((v.getColor().compareTo(Color.WHITE) == 0) && getEdgeWeight(u, v) < v.getInitialDistance()) {
					queue.remove(v);
					v.setInitialDistance(getEdgeWeight(u, v));
					queue.add(v);
					v.setPredecessor(u);
				}
			}
			u.setColor(Color.BLACK);
		}
	}
	
	
	
	private ArrayList<Edge> getEdges() {
		ArrayList<Edge> edges = new ArrayList<>();
		for (int i = 0; i < adjacentMatrix.size(); i++) {
			for (int j = 0; j < adjacentMatrix.get(i).size(); j++) {
				if(adjacentMatrix.get(i).get(j) == 1) {
					edges.add(new Edge(weightMatrix.get(i).get(j), vertexs.get(i), vertexs.get(j)));
				}
			}
		}
		return edges;
	}
	
	public ArrayList<Edge> kruskal() { 
		ArrayList<Edge> result = new ArrayList<>();
		int e = 0;
		int i = 0;

		ArrayList<Edge> edges = getEdges();
		Collections.sort(edges);

		UnionFind uf = new UnionFind(vertexs.size());

		i = 0;
		while (e < vertexs.size() - 1 && i < edges.size()) {
			Edge edge = edges.get(i);
			i++;

			int x = uf.find(getIndexOf(edge.getSource()));
			int y = uf.find(getIndexOf(edge.getDestination()));

			if (x != y) {
				result.add(edge);
				e++;
				uf.union(x, y);
			}
		}
		return result;
	}
	
	
	
	

}
