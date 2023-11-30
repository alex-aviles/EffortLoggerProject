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

public class Backlog extends Application {
    private Main mainApp;
    private JavaDatabase db = new JavaDatabase();

    public void start(Stage primaryStage, Main mainApp) throws Exception {
        this.mainApp = mainApp;

        Label title = new Label("Backlog List");
        Font font = Font.font(24);
        // Set the font for the label
        title.setFont(font);
        Button menuButton = new Button("Back"); // Add a menu button
        Button addItem = new Button("+");
        addItem.setPrefWidth(60);

        // Handle the menu button action
        menuButton.setOnAction(e -> {
            mainApp.changeState("Menu");
            mainApp.start(primaryStage);
        });
        // Handle the add button action
        addItem.setOnAction(e -> {
            mainApp.changeState("AddBacklogItem");
            mainApp.start(primaryStage);
        });

        // Create a VBox for the "+" button and center it
        VBox backlogItems = new VBox();
        backlogItems.getChildren().addAll(title);
        // Create a Set to store unique project names
        Set<String> uniqueProjectNames = new HashSet<>();
        for (int i = 0; i < db.getBacklogSize(); i++) {
            String projectName = db.getBacklogProjectName(i);

            // Check if the project name is unique; if not, skip it
            if (!uniqueProjectNames.contains(projectName)) {
                // Add the project name to the set
                uniqueProjectNames.add(projectName);

                // Create a label for the project name
                Label projectNameLabel = new Label(projectName);
                projectNameLabel.setTextFill(Color.BLUE);
                backlogItems.getChildren().add(projectNameLabel);

                // Print user stories with the same project name
                for (int j = 0; j < db.getBacklogSize(); j++) {
                    if (db.getBacklogProjectName(i).equals(db.getBacklogProjectName(j))) {
                        String userStory = db.getBacklogUserStory(j);
                        Label userStoryLabel = new Label(userStory);
                        backlogItems.getChildren().add(userStoryLabel);
                    } // end of if comparison
                } // end of user stories for loop
            } // end of unique check
        } // end of project names for loop

        backlogItems.getChildren().addAll(addItem);
        backlogItems.setAlignment(Pos.CENTER);
        
        //BorderPane.setMargin(backlogItems, new Insets(20, 0, 0, 0));

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
        primaryStage.setTitle("Backlog");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
    }
}
