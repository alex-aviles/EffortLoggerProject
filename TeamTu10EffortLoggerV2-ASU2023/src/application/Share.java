package application;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Share extends Application {
	private Main mainApp;
	private JavaDatabase db = new JavaDatabase();
	
	public void start(Stage primaryStage, Main mainApp) throws Exception {
		this.mainApp = mainApp;
		
		Button backButton = new Button("Main Menu");
		Button redoButton = new Button("redo");
		Button consensusButton = new Button("General Consensus Button");
		
		Label headLabel = new Label("Planning Poker");
		Font font = Font.font(24);
		headLabel.setFont(font);
		Label estimatedPointLabel = new Label(" Estimated story point: " + db.getValue());//turn into actual integer value with label
		Label actualStoryPoint = new Label("Average Story Point: " + db.averageValue());;
		font = Font.font(12);
		estimatedPointLabel.setFont(font);
		actualStoryPoint.setFont(font);
		
		backButton.setOnAction(e -> {
	           mainApp.changeState("Menu");
	           mainApp.start(primaryStage);
	    });
		redoButton.setOnAction(e -> {
	           mainApp.changeState("PlanningPoker");
	           mainApp.start(primaryStage);
	    });
		
		consensusButton.setOnAction(e -> {
            db.setStoryPoint("PLACE HOLDER FOR ACTUAL STORY POINT", mainApp.getProjectName(), mainApp.getUserStory());
            mainApp.changeState("PlanningPoker");
            mainApp.start(primaryStage);
		});
		
		VBox vbox = new VBox(20, headLabel, estimatedPointLabel, actualStoryPoint);
		vbox.setAlignment(Pos.TOP_CENTER);
		
		HBox midBox = new HBox(20, redoButton, consensusButton);
        midBox.setAlignment(Pos.BOTTOM_CENTER);
		
		HBox backBox = new HBox(backButton);
        backBox.setAlignment(Pos.BOTTOM_LEFT);
        
        BorderPane borderPane = new BorderPane();
        
        borderPane.setTop(vbox);
        borderPane.setCenter(midBox);
        borderPane.setBottom(backBox);
        
     // Create a Scene with the specified dimensions
        Scene scene = new Scene(borderPane, 1000, 500);
        scene.getRoot().setStyle("-fx-background-color: #d9f7ff;");
        primaryStage.setScene(scene);
        primaryStage.setTitle("Final Summary");
        primaryStage.show();
	}
	
	public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
    }
}