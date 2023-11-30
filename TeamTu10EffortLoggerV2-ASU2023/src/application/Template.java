package application;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Template extends Application {
    private Main mainApp;
    private JavaDatabase db = new JavaDatabase();
    
    public void start(Stage primaryStage, Main mainApp) throws Exception {
        this.mainApp = mainApp;
        
        Button menuButton = new Button("Main Menu"); // Add a menu button
        Label title = new Label("SET YOUR TITLE");
        Font font = Font.font(24);
        // Set the font for the label
        title.setFont(font);

        // Handle the menu button action
        menuButton.setOnAction(e -> {
            mainApp.changeState("Menu");
            mainApp.start(primaryStage);
        });
        
        // IMPLEMENT YOUR CLASS HERE
        //...
        //db.addBacklogItem(param1, param2, param3);
        
        // Create an HBox for the menu button and align it to the bottom-left
        HBox menuBox = new HBox(menuButton);
        menuBox.setAlignment(Pos.BOTTOM_LEFT);
        
        BorderPane borderPane = new BorderPane();
        // borderPane.setTop(*insert the rest of your class here*)
        borderPane.setBottom(menuBox);

        // Create a Scene with the specified dimensions
        Scene scene = new Scene(borderPane, 1000, 500);
        scene.getRoot().setStyle("-fx-background-color: #d9f7ff;");
        primaryStage.setScene(scene);
        primaryStage.setTitle("TITLE BAR CHANGE **");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
    }
}