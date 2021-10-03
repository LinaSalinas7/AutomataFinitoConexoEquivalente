package dataStructure;

import java.util.ArrayList;
import java.util.List;

public class Vertex<K extends Comparable,V> {
	
	public enum Color{WHITE,GRAY,BLACK};
	
	private V value;
	private K key;
	private double initialDistance;
	private double finalDistance;
	private Color vertexColor;
	
	private Vertex<K,V> predecessor;
	
	private List<Edge> adjacentVertexs;
	
	public Vertex(K key, V value) {
		this.value = value;
		this.key = key;
		
		this.initialDistance = 0.0;
		this.finalDistance = 0.0;
		this.vertexColor = Color.WHITE;
		
		this.adjacentVertexs = new ArrayList<Edge>();
		
	}
	
	public V getValue() {
		return this.value;
	}
	
	public K getKey() {
		return this.key;
	}
	
	public double getInitialDistance() {
		return this.initialDistance;
	}
	
	public double getFinalDistance() {
		return this.finalDistance;
	}
	
	public Color getColor() {
		return vertexColor;
	}
	
	public Vertex<K, V> getPredecessor(){
		return this.predecessor;
	}
	
	public void setInitialDistance(double initialDistance) {
		this.initialDistance = initialDistance;
	}
	
	public void setFinalDistance(double finalDistance) {
		this.finalDistance = finalDistance;
	}
	
	public void setColor(Color verteColor) {
		this.vertexColor = verteColor;
	}
	
	public void setPredecessor(Vertex<K, V> predecessor) {
		this.predecessor = predecessor;
	}
	
	public List<Vertex<K, V>> getAdjacentVertexs(){
		ArrayList<Vertex<K, V>> list = new ArrayList<Vertex<K,V>>();
		
		for (int i = 0; i < adjacentVertexs.size(); i++) {
			list.add(adjacentVertexs.get(i).getDestination());
		}
		
		return list;
	}
	
	public List<Edge> getAdjacenVertexs(){
		return this.adjacentVertexs;
	}
	
	public void addEdge(Vertex<K,V> destination, double weight) {
		Edge edge = new Edge(weight, this, destination);
		
		adjacentVertexs.add(edge);
	}
	
	public Edge findEdge(Vertex<K,V> vertex){
		Edge edge = null;
		boolean stop = false;
		
		for (int i = 0; i < adjacentVertexs.size() && !stop; i++) {
			if (adjacentVertexs.get(i).getDestination().key.compareTo(vertex.key) == 0) {
				edge = adjacentVertexs.get(i);
				stop = true;
			}
		}
		return edge;
	}
	
	public void removeEdge(Vertex<K,V> vertex) {
		boolean stop = false;
		
		for (int i = 0; i < adjacentVertexs.size() && !stop; i++) {
			if (findEdge(vertex).getDestination().key.compareTo(vertex.key) == 0) {
				adjacentVertexs.remove(i);
				stop = true;
			}
		}
	}

}
	