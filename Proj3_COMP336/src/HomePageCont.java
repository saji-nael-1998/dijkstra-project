import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

public class HomePageCont implements Initializable {
	// define views
	@FXML
	private AnchorPane mapContainer;
	private AnchorPane palestine_map;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

		setPalestineMap();

	}

//-------------------------------------------------------------------------------------------
	// set map
	LinkedList<Circle> arrCircle;
	int[] selectedCities;

	public void setPalestineMap() {
		try {
			// load map
			palestine_map = FXMLLoader.load(getClass().getResource("palestine_map.fxml"));
			mapContainer.getChildren().setAll(palestine_map);
			// display cities on the screen
			selectedCities = new int[2];
			selectedCities[0] = -1;
			selectedCities[1] = -1;
			arrCircle = new LinkedList<>();
			// read data from files
			Data readData = new Data();
			// get the list of cities
			LinkedList<Vertex> cities = readData.cities;
			// set node on screen
			for (Vertex v : cities) {
				// create circle
				Circle circle = new Circle(v.getX(), v.getY(), 5);
				// set color of circle
				circle.setFill(Color.BLACK);
				// set action for circle
				circle.setOnMouseClicked(this::setCircleAction);
				// add to map
				palestine_map.getChildren().add(circle);
				// add circle to map
				arrCircle.add(circle);
			}
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	public void setCircleAction(MouseEvent e) {

		for (int x = 0; x < arrCircle.size(); x++) {
			Circle c = arrCircle.get(x);
			if (e.getSource() == c) {
				// set source
				if (e.getButton() == MouseButton.SECONDARY) {
					if (selectedCities[0] != -1) {
						// set the previous circle white
						arrCircle.get(selectedCities[0]).setFill(Color.BLACK);

						// get the new city
						selectedCities[0] = x;
						// set the new circle green
						arrCircle.get(selectedCities[0]).setFill(Color.GREEN);

					} else {
						selectedCities[0] = x;
						arrCircle.get(selectedCities[0]).setFill(Color.GREEN);

					}

				} // set destination
				else if (e.getButton() == MouseButton.PRIMARY) {
					if (selectedCities[1] != -1) {
						// set the previous circle white
						arrCircle.get(selectedCities[1]).setFill(Color.BLACK);

						// get the new city
						selectedCities[1] = x;
						// set the new circle green
						arrCircle.get(selectedCities[1]).setFill(Color.RED);

					} else {
						selectedCities[1] = x;
						arrCircle.get(selectedCities[1]).setFill(Color.RED);

					}
				}
			}
		}
	}

	// -------------------------------------------------------------------------------------------
	// find shortest path
	@FXML
	private TextField distanceTF;

	public void runButton(ActionEvent e) {
		if (selectedCities[0] != -1 && selectedCities[1] != -1) {
			// find shortest path
			Graph graph = new Graph(arrCircle.size());
			Vertex src = Data.cities.get(selectedCities[0]);
			Vertex dest = Data.cities.get(selectedCities[1]);
			double distance = graph.findShortestPath(src, dest);
			distanceTF.setText("Distance : " + String.valueOf(roundNum(distance)) + " km");

			// display lines
			showRoute(graph.getPreviousVertex(), graph.findIndex(dest.getCity()));

		} else {

			Alert alert = new Alert(AlertType.WARNING);
			alert.setContentText("Please, you must SELECT 2 cities!!");
			alert.show();
		}
	}

	public double roundNum(double num) {
		num = num * 100;
		num = (int) num;
		num /= 100;
		return num;
	}

	public void showRoute(int prevVertex[], int indexOfDestination) {
		// get previous vertex
		LinkedList<Vertex> cities = Data.cities;
		Vertex vertex = null;
		LinkedList<Vertex> path = new LinkedList<>();
		int index = indexOfDestination;
		while (index != -1) {
			vertex = cities.get(index);
			path.add(vertex);
			index = prevVertex[index];
		}
		// display line
		displayLines(path);
		try {// Load second scene
			FXMLLoader loader = new FXMLLoader(getClass().getResource("path_pane.fxml"));
			Parent root;
			root = loader.load();

			// Get controller of scene2
			PathScreenCont scene2Controller = loader.getController();
			// Pass whatever data you want. You can have multiple method calls here

			scene2Controller.print(path);

			// Show scene 2 in new window
			Stage stage = new Stage();
			stage.setScene(new Scene(root));
			stage.setTitle("Second Window");
			stage.show();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	private void displayLines(LinkedList<Vertex> path) {
		// display lines
		for (int x = 1; x < path.size(); x++) {
			Vertex start = path.get(x - 1);
			Vertex end = path.get(x);

			Line line = new Line(start.getX(), start.getY(), end.getX(), end.getY());
			palestine_map.getChildren().add(line);
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

	public void clearButton(ActionEvent e) {
		// re-set the map
		setPalestineMap();
		// re-set textfield
		distanceTF.setText("Distance : 0 km");

	}

}
