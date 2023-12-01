package application;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class PlanningPoker extends Application {
	
    private Main mainApp;
    private JavaDatabase db = new JavaDatabase();
    private String classState = "Generate";
    
    public void start(Stage primaryStage, Main mainApp, String projectName) throws Exception {
        this.mainApp = mainApp;
        StackPane stackPane = new StackPane();
        
        Button menuButton = new Button("Main Menu"); // Add a menu button
        Label title = new Label("Planning Poker");
        Font font = Font.font(24);
        // Set the font for the label
        title.setFont(font);
        Label project_name = new Label(projectName);
        project_name.setStyle("-fx-text-fill: blue;");
        font = Font.font(18);
        // Set the font for the label
        project_name.setFont(font);

        // Handle the menu button action
        menuButton.setOnAction(e -> {
            mainApp.changeState("Menu");
            mainApp.start(primaryStage);
        });
        
        VBox vbox = new VBox(25, title, project_name);
        vbox.setAlignment(Pos.CENTER);
        
        String userStory = db.getUserStory(projectName);
        mainApp.changeUserStory(userStory);
        String keyWords = db.getKeyWords(projectName, userStory);
        
        if (classState.equals("Generate")) {
        	Label userStoryLabel = new Label("User Story: " + userStory);
        	
        	// Create a label that has the key words
            Label keywordsLabel = new Label("Key Words: " + keyWords);
            
            //create a button that changes classState to SelectProjects
            Button selectProjectsButton = new Button("Select Projects");
            
            //create a hbox that has a button that says generate story point and next to it a label that says int*
            Button generateStoryPointButton = new Button("Generate Story Point");
            Label val = new Label();
            HBox generateStoryPoint = new HBox(10, generateStoryPointButton, val);
            generateStoryPoint.setAlignment(Pos.CENTER);
          
            // Alexander Aviles
            //point generation in program
            generateStoryPointButton.setOnAction(e -> {
            	EffortCalculation point = new EffortCalculation();
            	point.getEntry(projectName);
            	int generatedStoryPoint = point.getGeneratedPoint();  
            	val.setText("" + generatedStoryPoint);
            });
            
            //create a label that says estimate story point
            Label estimateLabel = new Label("Estimate Story Point:");
            
            // Create a ComboBox for story point selection
            ComboBox<String> storyPointComboBox = new ComboBox<>();
            // Populate the ComboBox with the Fibonacci sequence up to 89
            storyPointComboBox.getItems().add(Integer.toString((int) 0));
            storyPointComboBox.getItems().add(Integer.toString((int) 1));
            storyPointComboBox.getItems().add(Integer.toString((int) 2));
            storyPointComboBox.getItems().add(Integer.toString((int) 3));
            storyPointComboBox.getItems().add(Integer.toString((int) 5));
            storyPointComboBox.getItems().add(Integer.toString((int) 8));
            storyPointComboBox.getItems().add(Integer.toString((int) 13));
            storyPointComboBox.getItems().add(Integer.toString((int) 21));
            storyPointComboBox.getItems().add(Integer.toString((int) 34));
            storyPointComboBox.getItems().add(Integer.toString((int) 55));
            storyPointComboBox.getItems().add(Integer.toString((int) 89));
            
            
            Button submitButton = new Button("Submit");
            
            Button shareButton = new Button("Share");
            //possible correction needed to get to summary
            shareButton.setOnAction(e -> {
                mainApp.changeState("Share");
                mainApp.start(primaryStage);
            });
            
            
            HBox lastLine = new HBox(20, storyPointComboBox, submitButton, shareButton);
            lastLine.setAlignment(Pos.CENTER);
            
            // Handle the button actions to change the class state
            selectProjectsButton.setOnAction(e -> {
                mainApp.changeState("HistoricalLogsDataStructure");
                mainApp.start(primaryStage);

            });
            
            String chosenStory = userStory;
            
            submitButton.setOnAction(e -> {
            	int storyPoint = Integer.valueOf(storyPointComboBox.getValue());
                /*classState = "Waiting Lobby";
                try {
					start(primaryStage, mainApp, projectName);
				} catch (Exception exp) {
                    exp.printStackTrace();
                }*/
            	// add story point to database
            	db.sendValue(storyPoint);
            });
            
            // Add the created items to the VBox
            vbox.getChildren().addAll(userStoryLabel, keywordsLabel, selectProjectsButton, generateStoryPoint, estimateLabel, lastLine);
        }
        else if(classState.equals("Waiting Lobby")) {
        	//label that says still waiting for responses 
        }
        else if(classState.equals("Review")) {
        	//Label Project name
        	//Label Estimated story point (average of everyones story points from database)
        	//redo button that takes us back to redo the story point for the same backlog item
        	//consensus reached button
        	//hbox with redo button and consensus reached button
        }
        else if(classState.equals("SelectProjects")) {
        	//popup window that lists projects with a check box
        	//CheckBox checkBox = new CheckBox();
        	//checkBox.isSelected() returns true/false
        	VBox vbox2 = new VBox();
        	//stackPane.getChildren.addAll(vbox, vbox2);
        }
        
        // Create an HBox for the menu button and align it to the bottom-left
        HBox menuBox = new HBox(menuButton);
        menuBox.setAlignment(Pos.BOTTOM_LEFT);
        
        BorderPane borderPane = new BorderPane();
        if(!classState.equals("SelectProjects")) {
        	borderPane.setTop(vbox);
        }
        else {
        	borderPane.setTop(stackPane);
        }
        borderPane.setBottom(menuBox);

        // Create a Scene with the specified dimensions
        Scene scene = new Scene(borderPane, 1000, 500);
        scene.getRoot().setStyle("-fx-background-color: #d9f7ff;");
        primaryStage.setScene(scene);
        primaryStage.setTitle("Planning Poker");
        primaryStage.show();
        if(userStory.isEmpty()) {
            mainApp.changeState("SelectPlanningPoker");
            mainApp.start(primaryStage);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
    }
}
