package client;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Gui extends Application {
	Controller controller;
	ClientInstance client;
	Stage primaryStage;
	
	public Gui(Stage primaryStage) {
		controller = new Controller();
		client = new ClientInstance(controller);
		controller.setClient(client);
		this.primaryStage = primaryStage;
		start(this.primaryStage);
	}

	@Override
	public void start(Stage primaryStage) {
		try {
			FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/Gui.fxml"));
			loader.setController(controller);
			StackPane stackPane = loader.load();
			Scene scene = new Scene(stackPane, 600, 500);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
