import java.util.LinkedList;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class PathScreenCont {
	@FXML
	private VBox table;

	public void print(LinkedList<Vertex> path) {
		// display lines
		for (int x = 1; x < path.size(); x++) {
			HBox hb = new HBox();
			if (x % 2 == 0) {
				hb.setStyle("-fx-background-color: lightgray ");
			} else {
				hb.setStyle("-fx-background-color: gray ");
			}
			Vertex src = path.get(x - 1);
			hb.getChildren().add(createLabel(src.getCity()));
			Vertex dest = path.get(x);
			hb.getChildren().add(createLabel(dest.getCity()));

			hb.getChildren().add(createLabel(String.valueOf(findDistance(src, dest))));
			table.getChildren().add(hb);
		}
	}

	public Label createLabel(String text) {
		Label label = new Label(text);
		label.setPrefWidth(150);
		label.setFont(Font.font(18));
		label.setTextFill(Color.BLACK);
		label.setPadding(new Insets(0, 0, 0, 10));
		label.setBorder(new Border(
				new BorderStroke(Color.WHITE, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
		return label;
	}

	public double findDistance(Vertex src, Vertex dest) {
		double distance = 0;
		int size = src.getNeighboursCity().size();
		for (int x = 0; x < size; x++) {
			EdgeVertices edge = (EdgeVertices) src.getNeighboursCity().get(x);
			if (edge.getTargetNode().getCity().equals(dest.getCity())) {
				distance = edge.getDistanceBetweenVertices();
				break;
			}
		}
		return roundNum(distance);

	}

	public double roundNum(double num) {
		num = num * 100;
		num = (int) num;
		num /= 100;
		return num;
	}

}
