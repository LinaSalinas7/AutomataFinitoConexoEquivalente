package dataStructure;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

import dataStructure.IGraph;
import dataStructure.Vertex.Color;

public class AdjacentListGraph<K extends Comparable,V> implements IGraph<K,V>{
	
	private boolean isDirected;
	private boolean isWeighted;
	private int numberOfVertexs;
	private int numberOfEdges;
	
	private List<Vertex<K, V>> vertexs;
	private Map<K, Vertex<K, V>> graph;
	
	
	public AdjacentListGraph(boolean isDirected, boolean isWeighted) {
		
		this.isDirected = isDirected;
		this.isWeighted = isWeighted;
		this.numberOfVertexs = 0;
		this.numberOfEdges = 0;
		
		this.vertexs = new ArrayList<Vertex<K,V>>();
		this.graph = new HashMap<K, Vertex<K, V>>();
	}


	@Override
	public boolean isDirected() {
		return isDirected;
	}


	@Override
	public boolean isWeighted() {
		return isWeighted;
	}


	@Override
	public void addVertex(K key, V value) {
		if (!isInGraph(key)) {
			Vertex<K,V> vertex = new Vertex<K,V>(key, value);
			this.graph.put(key, vertex);
			this.vertexs.add(vertex);
			this.numberOfVertexs++;
		}
	}


	@Override
	public void addEdge(Vertex<K,V> source, Vertex<K,V> destination, double weight) {
		if (isInGraph(source.getKey()) && isInGraph(destination.getKey())) {
			if (source.findEdge(destination) != null) {
				double w = 0.0;
				
				if (isWeighted) {
					w = weight;
				}else {
					w = 1.0;
				}
				
				if (this.isDirected) {
					source.addEdge(destination, w);
					numberOfEdges++;
				}else {
					source.addEdge(destination, w);
					destination.addEdge(source, w);
					numberOfEdges ++;
				}
			}
		}
	}


	@Override
	public void removeVertex(Vertex<K,V> toRemove) {
		if (isInGraph(toRemove.getKey())) {
			
			toRemove.getAdjacentVertexs().clear();
			graph.remove(toRemove.getKey());
			vertexs.remove(toRemove);
			numberOfVertexs--;
		}
	}


	@Override
	public void removeEdge(Vertex<K,V> source, Vertex<K, V> destination) {
		source.removeEdge(destination);
		
		if (!isDirected) {
			destination.removeEdge(source);
		}
	}


	@Override
	public boolean areAdjacent(Vertex<K,V> vertex1, Vertex<K,V> vertex2) {
		boolean areAdjacent = false;
		
		if (isInGraph(vertex1.getKey()) && isInGraph(vertex2.getKey())) {
			if (vertex1.findEdge(vertex2) != null || vertex2.findEdge(vertex1) != null) {
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
		return this.numberOfVertexs;
	}


	@Override
	public int getNumberOfEdges() {
		return this.numberOfEdges;
	}


	@Override
	public double getEdgeWeight(Vertex<K,V> source, Vertex<K,V> destination) {
		double weight = 0.0;
		
		if (isWeighted) {
			weight = source.findEdge(destination).getWeight();
		}
		
		return weight;
	}


	@Override
	public void setEdgeWeight(Vertex<K,V> source, Vertex<K,V> destination, double weight) {
		if (isWeighted) {
			source.findEdge(destination).setWeight(weight);
		}
	}


	@Override
	public List<Vertex<K, V>> vertexAdjacent(Vertex<K,V> vertex) {
		return graph.get(vertex.getKey()).getAdjacentVertexs();
	}


	@Override
	public List<Vertex<K,V>> getVertices() {
		List<Vertex<K,V>> list = new ArrayList<Vertex<K,V>>(graph.values());
		return list;
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
	
	
	
	public void bfs(Vertex<K,V> vertex) {
		
		for (Vertex<K,V> u : vertexs) {
			u.setColor(Color.WHITE);
			u.setInitialDistance(INFINITY);
			u.setPredecessor(null);
		}
		vertex.setColor(Color.GRAY);
		vertex.setInitialDistance(0);
		vertex.setPredecessor(null);
		
		Queue<Vertex<K,V>> q = new LinkedList<>();
		q.offer(vertex);
		
		while (!q.isEmpty()) {
			Vertex<K, V> u = q.poll();
			
			for (int i = 0; i < u.getAdjacentVertexs().size(); i++) {
				Vertex<K, V> v = u.getAdjacentVertexs().get(i);
				if (v.getColor().compareTo(Color.WHITE) == 0) {
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
			if (u.getColor().compareTo(Color.WHITE) == 0)
				time = dfsVisit(u, time);
		}
	}

	private int dfsVisit(Vertex<K, V> u, int time) {
		time++;
		u.setInitialDistance(time);
		u.setColor(Color.GRAY);
		for (int i = 0; i < u.getAdjacentVertexs().size(); i++) {
			Vertex<K, V> v = u.getAdjacentVertexs().get(i);
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
	
	
	
	private void initSingleSource(Vertex<K, V> s) {
		for (Vertex<K,V> u : vertexs) {
			u.setInitialDistance(INFINITY);
			u.setPredecessor(null);
		}
		s.setInitialDistance(0);
	}

	public void dijkstra(Vertex<K,V> s) {
		initSingleSource(s);
		
		PriorityQueue<Vertex<K, V>> queue = new PriorityQueue<>();
		queue.add(s);
		
		while (!queue.isEmpty()) {
			Vertex<K, V> u = queue.poll();
			
			for (Edge e : u.getAdjacenVertexs()) {
				Vertex<K, V> v = e.getDestination();
				double weight = e.getWeight();
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
			Arrays.fill(weights[i], INFINITY);
		}
		
		for (int i = 0; i < vertexs.size(); i++) {
			weights[i][i] = 0;
			Vertex<K, V> u = vertexs.get(i);
			
			for (Edge e : u.getAdjacenVertexs()) {
				Vertex<K, V> v = e.getDestination();
				double weight = e.getWeight();
				weights[i][getIndexOf(v)] = weight;
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
	
	
	
	public void prim(Vertex<K,V> vertex) {
		
		for (Vertex<K,V> u : vertexs) {
			u.setInitialDistance(INFINITY);
			u.setColor(Color.WHITE);
		}
		
		vertex.setInitialDistance(0);
		vertex.setPredecessor(null);
		
		PriorityQueue<Vertex<K, V>> queue = new PriorityQueue<>();
		
		for (Vertex<K,V> u : vertexs) {
			queue.add(u);
		}
		
		while (!queue.isEmpty()) {
			Vertex<K, V> u = queue.poll();
			
			for (Edge e : u.getAdjacenVertexs()) {
				Vertex<K, V>v = e.getDestination();
				
				if ((v.getColor().compareTo(Color.WHITE) == 0) && e.getWeight() < v.getInitialDistance()) {
					queue.remove(v);
					v.setInitialDistance(e.getWeight());
					queue.add(v);
					v.setPredecessor(u);
				}
			}
			u.setColor(Color.BLACK);
		}
	}
	
	
	
	private ArrayList<Edge> getEdges() {
		ArrayList<Edge> edges = new ArrayList<>();
		
		for (int i = 0; i < vertexs.size(); i++) {
			Vertex<K, V> v = vertexs.get(i);
			
			for (int j = 0; j < v.getAdjacenVertexs().size(); j++) {
				edges.add(v.getAdjacenVertexs().get(j));
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
