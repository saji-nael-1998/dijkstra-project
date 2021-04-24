
import java.util.LinkedList;
import java.util.PriorityQueue;

class Graph {
	private boolean[] visited;
	private double[] distance;
	private int[] previousVertex;
	Graph(int numOfCities) {
		visited = new boolean[numOfCities];
		distance = new double[numOfCities];
		previousVertex = new int[numOfCities];
		for (int i = 0; i < numOfCities; i++) {
			distance[i] = Double.MAX_VALUE;
			visited[i] = false;
		}
	}

	public double findShortestPath(Vertex from, Vertex to) {
		new Graph(distance.length);
		double dist = 0.0;
		if (from == to) {
			return dist;
		} else {

			EdgeComparator compare = new EdgeComparator();
			PriorityQueue<EdgeVertices> minHeap = new PriorityQueue<>(compare);
			distance[findIndex(from.getCity())] = 0;
			previousVertex[findIndex(from.getCity())] = -1;
			minHeap.add(new EdgeVertices(from, 0.0));
			while (!minHeap.isEmpty()) {
				Vertex v = minHeap.poll().getTargetNode();
				int index = findIndex(v.getCity());
				if (to.getCity().equals(v.getCity())) {
					dist = distance[findIndex(to.getCity())];
					return dist;
				}
				if (visited[index])
					continue;
				visited[index] = true;
				LinkedList<EdgeVertices> adjacent = Data.cities.get(index).getNeighboursCity();
				computeRelaxtion(adjacent, index, minHeap);
			}

			dist = distance[findIndex(to.getCity())];

		}

		return dist;
	}

	public void computeRelaxtion(LinkedList<EdgeVertices> adjacent, int index, PriorityQueue<EdgeVertices> minHeap) {
		for (EdgeVertices adj : adjacent) {
			double dist = adj.getDistanceBetweenVertices();
			Vertex vert = adj.getTargetNode();
			if (visited[findIndex(vert.getCity())] == false) {
				if (distance[index] + dist < distance[findIndex(vert.getCity())]) {
					distance[findIndex(vert.getCity())] = distance[index] + dist;
					adj.setDistanceBetweenVertices(distance[index] + dist);
					previousVertex[findIndex(vert.getCity())] = index;
					minHeap.add(adj);
				}
			}
		}
	}

	public int findIndex(String city) {
		for (int x = 0; x < Data.cities.size(); x++) {
			Vertex c = Data.cities.get(x);
			if (c.getCity().equals(city))
				return x;
		}
		return -1;
	}

	public int[] getPreviousVertex() {
		return previousVertex;
	}

}
