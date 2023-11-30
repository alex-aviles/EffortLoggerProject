package application;

import java.util.HashSet;
import java.util.Set;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class SelectPlanningPokerSession extends Application {
    private Main mainApp;
    private JavaDatabase db = new JavaDatabase();
    
    public void start(Stage primaryStage, Main mainApp) throws Exception {
        this.mainApp = mainApp;
        
        Button menuButton = new Button("Back"); // Add a menu button
        Label title = new Label("Select Planning Poker Session:");
        Font font = Font.font(24);
        // Set the font for the label
        title.setFont(font);

        // Handle the menu button action
        menuButton.setOnAction(e -> {
            mainApp.changeState("Menu");
            mainApp.start(primaryStage);
        });
        
        // Create a VBox for the "+" button and center it
        VBox backlogItems = new VBox(10);
        backlogItems.getChildren().addAll(title);
        // Create a Set to store unique project names
        Set<String> uniqueProjectNames = new HashSet<>();
        for (int i = 0; i < db.getBacklogSize(); i++) {
            String projectName = db.getBacklogProjectName(i);

            // Check if the project name is unique; if not, skip it
            if (!uniqueProjectNames.contains(projectName)) {
                // Add the project name to the set
                uniqueProjectNames.add(projectName);

                // Check if project is generated (use your method here)
                boolean isProjectGenerated = db.projectGenerated(projectName);

                // Create a label for the project name if project is generated, otherwise create a button
                if (isProjectGenerated) {
                    Label projectNameLabel = new Label(projectName);
                    projectNameLabel.setTextFill(Color.BLUE);

                    backlogItems.getChildren().add(projectNameLabel);
                } else {
                    Button projectNameButton = new Button(projectName);
                    projectNameButton.setTextFill(Color.BLUE);

                    // Add an event handler to the button
                    projectNameButton.setOnAction(e -> {
                        // Change the state to "PlanningPoker" when the button is clicked
                        PlanningPoker planningPoker = new PlanningPoker();
                        mainApp.changeBacklogProjectName(projectNameButton.getText());
                        mainApp.changeState("PlanningPoker");
                        mainApp.start(primaryStage);
                    });

                    backlogItems.getChildren().add(projectNameButton);
                }
            }
        }
        backlogItems.setAlignment(Pos.CENTER);
        
        // Create an HBox for the menu button and align it to the bottom-left
        HBox menuBox = new HBox(menuButton);
        menuBox.setAlignment(Pos.BOTTOM_LEFT);
        
        BorderPane borderPane = new BorderPane();
        borderPane.setTop(backlogItems); // Place the VBox at the top
        borderPane.setBottom(menuBox);

        // Create a Scene with the specified dimensions
        Scene scene = new Scene(borderPane, 1000, 500);
        scene.getRoot().setStyle("-fx-background-color: #d9f7ff;");
        primaryStage.setScene(scene);
        primaryStage.setTitle("Planning Poker");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
    }
}