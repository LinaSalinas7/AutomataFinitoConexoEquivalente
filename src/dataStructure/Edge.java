package dataStructure;

public class Edge implements Comparable<Edge>{
	
	private double weight;
	private Vertex destination;
	private Vertex source;

	public Edge(double weight, Vertex source, Vertex destination) {
		this.weight = weight;
		
		this.source = source;
		this.destination = destination;
	}
	
	public double getWeight() {
		return this.weight;
	}
	
	public Vertex getSource() {
		return this.source;
	}
	
	public Vertex getDestination() {
		return this.destination;
	}
	
	public void setWeight(double weight) {
		this.weight = weight;
	}

	@Override
	public int compareTo(Edge o) {
		return Double.compare(this.weight, o.weight);
	}

}
