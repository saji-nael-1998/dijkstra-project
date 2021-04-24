import java.util.LinkedList;

public class Vertex {
	private String city;
	private int x;
	private int y;
	private LinkedList<EdgeVertices> neighboursCity;

	public Vertex(String city, int x, int y) {
		super();
		this.city = city;
		this.x = x;
		this.y = y;
		neighboursCity = new LinkedList<>();
	}

	public String getCity() {
		return city;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public LinkedList<EdgeVertices> getNeighboursCity() {
		return neighboursCity;
	}

	public void addneighboursCity(Vertex nCity, double dist) {
		EdgeVertices edge = new EdgeVertices(nCity, dist);
		neighboursCity.add(edge);
	}
}
