import java.io.File;
import java.util.LinkedList;
import java.util.Scanner;

public class Data {
	// read cities from file
	static LinkedList<Vertex> cities;

	// read distance from file
	Data() {
		cities = new LinkedList<>();
		readCities();
		readDistance();
	}

	public void readCities() {
		try {
			File file = new File("Palestinian Cities.txt");
			Scanner reader = new Scanner(file);
			while (reader.hasNextLine()) {
				// read line
				String line = reader.nextLine();
				// split line
				String splitLine[] = line.split(",");
				String city = splitLine[0];
				int x = Integer.parseInt(splitLine[1]);
				int y = Integer.parseInt(splitLine[2]);
				cities.add(new Vertex(city, x, y));

			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public void readDistance() {
		try {
			File file = new File("di.txt");
			Scanner reader = new Scanner(file);
			while (reader.hasNextLine()) {
				// read line
				String line = reader.nextLine();
				// split line
				String splitLine[] = line.split("-");
				Vertex city1 = findVertex(splitLine[0].trim());
				Vertex city2 = findVertex(splitLine[1].trim());
				double dist = Double.parseDouble(splitLine[2].trim());
				// add edge for each city
				city1.addneighboursCity(city2, dist);
				city2.addneighboursCity(city1, dist);
			}

		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public Vertex findVertex(String city) {
		for (int x = 0; x < cities.size(); x++) {
			Vertex c = cities.get(x);
			if (c.getCity().equals(city))
				return c;
		}
		return null;
	}
}
