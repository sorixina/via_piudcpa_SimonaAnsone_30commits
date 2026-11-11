package ocstoragesystem;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;

public class OCMain extends Application {
	
	static Stage globalStage;
	
	@Override
		public void start(Stage primaryStage) {
			try {
				globalStage = primaryStage;
				Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
				Scene scene = new Scene(root,550,646);
				
				globalStage.setScene(scene);
		        globalStage.setTitle("Base -- a character database");
		          Image icon = new Image(getClass().getResourceAsStream("/ocstoragesystem/images/favicon.png"));
		            globalStage.getIcons().add(icon);
				globalStage.show();
				
				} catch(Exception e) {
					e.printStackTrace();
				}
		
	}
	
	
	public static void main(String[] args) {
		launch(args);
		
	}

}
