package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

public class EffortLoggerV2Console extends Application {
	
	private Main mainApp;
	private JavaDatabase db = new JavaDatabase();
	LocalTime startTime;
	LocalTime endTime;
	long durationSeconds;
	private ObservableList<String> backlogProjects = FXCollections.observableArrayList();
	private ObservableList<String> projectUserStories = FXCollections.observableArrayList();
	
	public void start(Stage primaryStage, Main mainApp) throws Exception {
		this.mainApp = mainApp;
		Label titleLabel = new Label("Effort Logger V2 Console");

		backlogProjects.addAll(db.getBacklogProjects());
		Label projectLabel = new Label("Project:");
		ComboBox<String> projectComboBox = new ComboBox<>(backlogProjects);
		
		Label userStoryLabel = new Label("User Story:");
		ComboBox<String> userStoryComboBox = new ComboBox<>();
				
		projectComboBox.setOnAction(e -> {
			String selectedProject = projectComboBox.getValue();

			userStoryComboBox.getItems().clear(); // clear existing items
			projectUserStories.clear();
	        for (int i = 0; i < db.getBacklogSize(); i++) {
	            String projectName = db.getBacklogProjectName(i);

	            if (projectName.equals(selectedProject)) {
	            	
	                // add user stories with the same project name
	                for (int j = 0; j < db.getBacklogSize(); j++) {
	                    if (db.getBacklogProjectName(i).equals(db.getBacklogProjectName(j))) {
	                        String userStory = db.getBacklogUserStory(j);
	                        projectUserStories.add(userStory);
	                    } // end of if comparison
	                } // end of user stories for loop
	            } // end of unique check
	        } // end of project names for loop
	        
	        // add list of projectUserStories to comboBox
	        userStoryComboBox.getItems().setAll(projectUserStories);
		});

		Label stepLabel = new Label("Step:");
		ComboBox<String> stepComboBox = new ComboBox<>();
		stepComboBox.getItems().addAll("Problem Understanding", "Conceptual Design Plan", "Requirements",
				"Conceptual Design", "Conceptual Design Review", "Detailed Design Plan", "Detailed Design/Prototype",
				"Detailed Design Review", "Implementation Plan", "Test Case Generation", "Solution Specification", 
				"Solution Review", "Solution Implementation", "Unit/System Test", "Reflection", "Repository Update",
				"Planning", "Information Gathering", "Information Understanding", "Verifying", "Outlining", "Drafting",
				"Finalizing", "Team Meeting", "Coach Meeting", "Stakeholder Meeting"); // Add your step names here

		Label categoryLabel = new Label("Category:");
		ComboBox<String> categoryComboBox = new ComboBox<>();
		categoryComboBox.getItems().addAll("Plans", "Deliverables", "Interruptions", "Defects"); // Add your category names here
		
		Button menuButton = new Button("Back"); // Add a menu button

        // Handle the menu button action (you can replace this with your logic)
        menuButton.setOnAction(e -> {
            mainApp.changeState("Menu");
            mainApp.start(primaryStage);
        });

		Label choiceLabel = new Label("Choice:");
		ComboBox<String> choiceComboBox = new ComboBox<>();
		// your choice names here
		categoryComboBox.setOnAction(e -> {
			String selectedCategory = categoryComboBox.getValue();

			choiceComboBox.getItems().clear(); // clear existing items

			switch (selectedCategory) {
				case "Plans":
					choiceComboBox.getItems().addAll(
							"Project Plan", "Risk Management Plan", "Conceptual Design Plan", "Detailed Design Plan", "Implementation Plan");
					break;
				case "Deliverables":
					choiceComboBox.getItems().addAll(
							"Conceptual Design", "Detailed Design", "Test Cases", "Solution", "Reflection", "Outline", "Draft", "Report", "Other");
					break;
				case "Interruptions":
					choiceComboBox.getItems().addAll(
							"Break", "Phone", "Teammate", "Visitor", "Other");
					break;
				case "Defects":
					choiceComboBox.getItems().addAll(
							"Not Specified", "Documentation", "Syntax", "Build, Package",
							"Assignment", "Interface", "Checking", "Data", "Function", "System", "Environment");
					break;
			}
		});

		Button logsButton = new Button("Logs"); // button to log Effort entry and pull up historical logs
		logsButton.setOnAction(e -> { // button event sequence
            mainApp.changeState("HistoricalLogs");
            mainApp.start(primaryStage);
        }); 
		
		Button startButton = new Button("Start");
		Button endButton = new Button("End");
		Label timeLabel = new Label("Clock is Stopped");
		Label time = new Label();
		timeLabel.setStyle("-fx-background-color: LIGHTCORAL;");
		startButton.setOnAction(e->{
			timeLabel.setText("Clock is Running");
			timeLabel.setStyle("-fx-background-color: LIMEGREEN");
			startTime = LocalTime.now();
			time.setText("");
		});
		
		endButton.setOnAction(e->{
			endTime = LocalTime.now();
			if (startTime != null) {
				durationSeconds = startTime.until(endTime, java.time.temporal.ChronoUnit.SECONDS);
				//the line above records minutes, so it's easy to see the change we will change this to hours later
			}
			timeLabel.setText("Clock is Stopped");
			timeLabel.setStyle("-fx-background-color: LIGHTCORAL;");
			time.setText(Long.toString(durationSeconds));
			// updating the database
			String projectOption = projectComboBox.getValue(); //store project choice
			String selectUserStory = userStoryComboBox.getValue(); // user story choice
			String stepOption  = stepComboBox.getValue(); // store life cycle step choice
			String categoryOption = categoryComboBox.getValue(); // store category choice
			String choiceOption = choiceComboBox.getValue(); // store selection choice
			
			db.insertLogs(projectOption, selectUserStory, stepOption, categoryOption, choiceOption, durationSeconds);

		});
		
		Button openEditor = new Button("Open Editor");
		openEditor.setOnAction(e -> {
			mainApp.changeState("EffortLoggerV2Editor");
			mainApp.start(primaryStage);
		});
		
		Button defectLoggerConsole = new Button("Defect Logger Console");
		Button customization = new Button("Customization");
		
		GridPane gridPane = new GridPane();
		gridPane.setHgap(10);
		gridPane.setVgap(10);
		gridPane.add(titleLabel, 1, 0);
		gridPane.add(startButton,1,1);
		gridPane.add(projectLabel, 0, 2);
		gridPane.add(projectComboBox, 1, 2);
		gridPane.add(userStoryLabel, 0, 3);
		gridPane.add(userStoryComboBox, 1, 3);
		gridPane.add(stepLabel, 0, 4);
		gridPane.add(stepComboBox, 1, 4);
		gridPane.add(categoryLabel, 0, 5);
		gridPane.add(categoryComboBox, 1, 5);
		gridPane.add(choiceLabel, 0, 6);
		gridPane.add(choiceComboBox, 1, 6);
		gridPane.add(endButton, 1, 7);
		gridPane.add(openEditor, 2, 7);
		gridPane.add(defectLoggerConsole, 1, 8);
		gridPane.add(logsButton, 2, 8);
		gridPane.add(customization, 3, 8);
		gridPane.add(timeLabel, 4, 1);
		gridPane.add(time, 5, 1);
		
		// Create an HBox for the menu button and align it to the bottom-left
        HBox menuBox = new HBox(menuButton);
        menuBox.setAlignment(Pos.BOTTOM_LEFT);

        // Create a BorderPane and set the gridPane at the center and the menuBox at the bottom
        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(gridPane);
        borderPane.setBottom(menuBox);

		Scene scene = new Scene(borderPane, 1000, 500);
		scene.getRoot().setStyle("-fx-background-color: #d9f7ff;");
		primaryStage.setTitle("Effort Logger V2 Console");
		primaryStage.setScene(scene);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
	}
}