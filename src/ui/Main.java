package ui;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;


public class Main extends Application{


	public static void main(String[] args) {
		launch(args);
	}

	
	@Override

	public void start(Stage stage) throws Exception {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("Ball.fxml"));
		Parent root = loader.load();
		
		BallController ballController = loader.getController();
		ballController.setStage(stage);
		ballController.setHostServices(getHostServices());
		stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
                try {
					ballController.onCloseRequest();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
            }
        });
		
		Scene scene = new Scene(root);
    	stage.setTitle("Atrapalas todas!");
    	stage.setScene(scene);
    	stage.setFullScreen(true);
    	stage.setResizable(true);
    	stage.show();
	}

}
