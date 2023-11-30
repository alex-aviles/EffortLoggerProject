package application;

import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.layout.VBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;

public class HistoricalLogsDataStructure extends Application {
    private Main mainApp;
    private JavaDatabase db = new JavaDatabase();
    private List<Button> allLogButtons = new ArrayList<>();
    
    public void start(Stage primaryStage, Main mainApp) throws Exception {
        this.mainApp = mainApp;
        
        TextField searchField = new TextField();
        Button searchButton = new Button("Search");
        Label instructionLabel = new Label("Search for a key word or click search to see all logs");
        HBox searchBox = new HBox(searchField, searchButton, instructionLabel);
        searchBox.setSpacing(10);
        
        Button backButton = new Button("Back");
        Label title = new Label("Effort Selection");
        Font font = Font.font(24);
        title.setFont(font);

        backButton.setOnAction(e -> {
            mainApp.changeState("PlanningPoker");
            mainApp.start(primaryStage);
        });

        VBox logsVBox = new VBox();  // Use VBox to stack buttons vertically
        logsVBox.setSpacing(5);  // Set spacing between buttons
        logsVBox.getChildren().add(searchBox);
        
        searchButton.setOnAction(e -> {
        	logsVBox.getChildren().clear(); // Clear the current log buttons
        	logsVBox.getChildren().add(searchBox);

            // Filter logs based on the entered keyword
            for (Button logButton : allLogButtons) {
                String logText = logButton.getText().toLowerCase();
                if (logText.contains(searchField.getText().toLowerCase())) {
                    logsVBox.getChildren().add(logButton); // Add matching logs to the VBox
                }
            }
        });

        List<LogEntry> logs = fetchHistoricalLogsFromDatabase();
        for (LogEntry log : logs) {
            Button logButton = new Button(log.toString());
            logButton.setStyle("-fx-background-color: #d3d3d3;"); // Set the initial background color
            logButton.setOnAction(e -> handleLogButtonClick(log, logButton));
            allLogButtons.add(logButton); // Add the button to the list
        }

        ScrollPane logsScrollPane = new ScrollPane(logsVBox);  // Use ScrollPane to handle overflow

        HBox menuBox = new HBox(backButton);
        menuBox.setAlignment(Pos.BOTTOM_LEFT);

        BorderPane borderPane = new BorderPane();
        borderPane.setTop(title);
        borderPane.setCenter(logsScrollPane);
        borderPane.setBottom(menuBox);

        Scene scene = new Scene(borderPane, 1000, 500);
        scene.getRoot().setStyle("-fx-background-color: #d9f7ff;");

        primaryStage.setScene(scene);
        primaryStage.setTitle("Effort Selection");
        primaryStage.show();
    }
    
    private List<LogEntry> fetchHistoricalLogsFromDatabase() {
    	
    	return db.fetchHistoricalLogs();
    }
    
    private void handleLogButtonClick(LogEntry log, Button logButton) {
    	if (logButton.getStyle().equals("-fx-background-color: #d3d3d3;")) {
            logButton.setStyle("-fx-background-color: #808080;");
        } else {
            logButton.setStyle("-fx-background-color: #d3d3d3;");
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
    }
}