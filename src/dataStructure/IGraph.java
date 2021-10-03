package dataStructure;

import java.util.List;
import dataStructure.Vertex;

public interface IGraph<K extends Comparable,V> {
	
	public static final double INFINITY = Double.POSITIVE_INFINITY;
	
	public boolean isDirected();
	public boolean isWeighted();
	public 	void addVertex(K key, V value);
	public void addEdge(Vertex<K,V> source, Vertex<K,V> destination, double weight);
	public void removeVertex(Vertex<K,V> toRemove);
	public void removeEdge(Vertex<K,V> source, Vertex<K, V> destination);
	public boolean areAdjacent(Vertex<K,V> vertex1, Vertex<K,V> vertex2);
	public boolean isInGraph(K key);
	public int getNumberOfVertexs();
	public int getNumberOfEdges();
	public double getEdgeWeight(Vertex<K,V> source, Vertex<K,V> destination);
	public void setEdgeWeight(Vertex<K,V> source, Vertex<K,V> destination, double weight);
	public List<Vertex<K, V>> vertexAdjacent(Vertex<K,V> vertex);
	public List<Vertex<K,V>> getVertices();
	
}
