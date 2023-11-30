package application;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.Arrays;
import java.util.Collections;
import javafx.collections.ObservableList;

public class EffortLoggerV2Editor extends Application {
    private Main mainApp;
    private JavaDatabase db = new JavaDatabase();
    
    private ComboBox<LogEntry> logEntryComboBox = new ComboBox<>(); //11.28.23 Change Armaani
    private ComboBox<String> projectComboBox = new ComboBox<>();
    private ComboBox<String> stepComboBox = new ComboBox<>();
    private ComboBox<String> categoryComboBox = new ComboBox<>();
    private ComboBox<String> choiceComboBox = new ComboBox<>();
    
    public void start(Stage primaryStage, Main mainApp) throws Exception {
        this.mainApp = mainApp;
        
        Button menuButton = new Button("Main Menu"); // Add a menu button
        Button updateButton = new Button("Update");
        Label title = new Label("EffortLoggerV2 Editor");
        Label statusLabel = new Label("Status: Open");
        VBox updateStatusBox = new VBox(updateButton, statusLabel); 
        updateStatusBox.setAlignment(Pos.CENTER);
        updateStatusBox.setSpacing(5);
        Font font = Font.font(24);
        // Set the font for the label
        title.setFont(font);
        
        //set combobox widths
        projectComboBox.setPrefWidth(300);
        stepComboBox.setPrefWidth(300);
        categoryComboBox.setPrefWidth(300);
        choiceComboBox.setPrefWidth(300);
        
        //set comboboxes as editable
        stepComboBox.setEditable(true);
        categoryComboBox.setEditable(true);
        choiceComboBox.setEditable(true);
        
        //populate comboboxes with data from database
        projectComboBox.getItems().addAll(db.getBacklogProjects());
        
        projectComboBox.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            // Populate stepComboBox with all steps available
        	stepComboBox.setItems(FXCollections.observableArrayList(
        	        "Problem Understanding", "Conceptual Design Plan", "Requirements",
        	        "Conceptual Design", "Conceptual Design Review", "Detailed Design Plan", "Detailed Design/Prototype",
        	        "Detailed Design Review", "Implementation Plan", "Test Case Generation", "Solution Specification", 
        	        "Solution Review", "Solution Implementation", "Unit/System Test", "Reflection", "Repository Update",
        	        "Planning", "Information Gathering", "Information Understanding", "Verifying", "Outlining", "Drafting",
        	        "Finalizing", "Team Meeting", "Coach Meeting", "Stakeholder Meeting"
        	    ));
            // Similarly for categoryComboBox and choiceComboBox
            categoryComboBox.setItems(FXCollections.observableArrayList(
                db.getAllCategories() // Implement getAllCategories
            ));
            choiceComboBox.setItems(FXCollections.observableArrayList(
                db.getAllChoices() // Implement getAllChoices
            ));
            
            List<String> categoriesFromDb = db.getAllCategories();
            if (!categoriesFromDb.contains("Interruptions")) {
                categoriesFromDb.add("Interruptions");
            }
            if (!categoriesFromDb.contains("Defects")) {
                categoriesFromDb.add("Defects");
            }
            categoryComboBox.setItems(FXCollections.observableArrayList(categoriesFromDb));

            // Set current settings as selected value
            stepComboBox.setValue(db.getEntryStep(newValue));
            categoryComboBox.setValue(db.getEntryCat(newValue));
            choiceComboBox.setValue(db.getEntryChoice(newValue));
        });
        
        categoryComboBox.getItems().addAll("Plans", "Deliverables", "Interruptions", "Defects");
        
        categoryComboBox.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            choiceComboBox.getItems().clear(); // Clear existing choices

            if ("Interruptions".equals(newValue)) {
                choiceComboBox.getItems().addAll("Break", "Phone", "Teammate", "Visitor", "Other");
            } else if ("Defects".equals(newValue)) {
                choiceComboBox.getItems().addAll("Not Specified", "Documentation", "Syntax", "Build, Package", "Assignment", "Interface", "Checking", "Data", "Function", "System", "Environment");
            } else if ("Deliverables".equals(newValue)) {
                choiceComboBox.getItems().addAll("Conceptual Design", "Detailed Design", "Test Cases", "Solution", "Reflection", "Outline", "Draft", "Report", "Other");
            } else if ("Plans".equals(newValue)) {
                choiceComboBox.getItems().addAll("Project Plan", "Risk Management Plan", "Conceptual Design Plan", "Detailed Design Plan", "Implementation Plan");
            }
        });
        
        logEntryComboBox.setPrefWidth(300);
        logEntryComboBox.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            if (newValue != null) {
                stepComboBox.setValue(newValue.getStep());
                categoryComboBox.setValue(newValue.getCategory());
                choiceComboBox.setValue(newValue.getChoice());
            }
        });
        
        populateLogEntryComboBox(); //11.28.23 Change Armaani

        //update button action
        updateButton.setOnAction(e -> {
            LogEntry selectedEntry = logEntryComboBox.getValue();
            if (selectedEntry != null) {
                int entryId = selectedEntry.getEntryId(); // Get the entry ID from the selected log entry //11.28.23 Change Armaani
                String selectedStep = stepComboBox.getValue();
                String selectedCategory = categoryComboBox.getValue();
                String selectedChoice = choiceComboBox.getValue();
                
                db.updateLogEntry(entryId, selectedStep, selectedCategory, selectedChoice); // Update using entryId //11.28.23 Change Armaani
                
                statusLabel.setText("Status: Updated");
            } else {
                statusLabel.setText("Status: No Entry Selected");
            }
        });

        
        

        // Handle the menu button action
        menuButton.setOnAction(e -> {
            mainApp.changeState("Menu");
            mainApp.start(primaryStage);
        });
        
        // Create an HBox for the menu button and align it to the bottom-left
        HBox topBox = new HBox(title, updateButton);
        topBox.setAlignment(Pos.CENTER);
        topBox.setSpacing(10);
        
        Label projectLabel = new Label("Project:");
        Label stepLabel = new Label("Step:");
        Label categoryLabel = new Label("Category:");
        Label choiceLabel = new Label("Choice:");
        Label logEntryLabel = new Label("Log Entry: ");
        
        VBox labelsBox = new VBox(5, projectLabel, logEntryLabel, stepLabel, categoryLabel, choiceLabel); //11.28.23 Change Armaani added logEntryLabel
        VBox comboBoxesBox = new VBox(projectComboBox, logEntryComboBox, stepComboBox, categoryComboBox, choiceComboBox, statusLabel); //11.28.23 Change Armaani added logEntryComboBox
        comboBoxesBox.setSpacing(10);
        
        HBox formBox = new HBox(20, labelsBox, comboBoxesBox);
        formBox.setAlignment(Pos.CENTER);
        formBox.setPadding(new Insets(20, 0, 0, 200));
        
        BorderPane borderPane = new BorderPane();
        borderPane.setTop(topBox);
        borderPane.setCenter(formBox);
        //borderPane.setCenter(comboBoxesBox);
        borderPane.setBottom(new HBox(menuButton));       

        // Create a Scene with the specified dimensions
        Scene scene = new Scene(borderPane, 1000, 500);
        scene.getRoot().setStyle("-fx-background-color: #d9f7ff;");
        primaryStage.setScene(scene);
        primaryStage.setTitle("EffortLoggerV2Editor");
        primaryStage.show();
    }
    
    private void populateLogEntryComboBox() { //11.28.23 Change Armaani
    	ObservableList<LogEntry> logEntries = FXCollections.observableArrayList(db.fetchHistoricalLogs());
    	logEntryComboBox.setItems(logEntries);
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
    }
}