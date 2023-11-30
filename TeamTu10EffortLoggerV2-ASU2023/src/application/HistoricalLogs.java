package application;

import java.util.List;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class HistoricalLogs extends Application {
    private Main mainApp;
    private JavaDatabase db = new JavaDatabase();
    
    public void start(Stage primaryStage, Main mainApp) throws Exception {
        this.mainApp = mainApp;
        
        Button menuButton = new Button("Main Menu"); // Add a menu button
        Label title = new Label("Historical Logs");
        Font font = Font.font(24);
        // Set the font for the label
        title.setFont(font);

        // Handle the menu button action
        menuButton.setOnAction(e -> {
            mainApp.changeState("Menu");
            mainApp.start(primaryStage);
        });
        
        //create scroll pane to hold the historical logs
        TextArea logsTextArea = new TextArea();
        logsTextArea.setEditable(false);
        logsTextArea.setText(fetchHistoricalLogsFromDatabase());
        logsTextArea.setWrapText(true);
        logsTextArea.setPrefRowCount(10);
        logsTextArea.setPrefColumnCount(40);
        
        logsTextArea.setText(fetchHistoricalLogsFromDatabase());
        
        // Create an HBox for the menu button and align it to the bottom-left
        HBox menuBox = new HBox(menuButton);
        menuBox.setAlignment(Pos.BOTTOM_LEFT);
        
        BorderPane borderPane = new BorderPane();
        borderPane.setTop(title);
        borderPane.setCenter(logsTextArea);
        borderPane.setBottom(menuBox);

        // Create a Scene with the specified dimensions
        Scene scene = new Scene(borderPane, 1000, 500);
        scene.getRoot().setStyle("-fx-background-color: #d9f7ff;");
        primaryStage.setScene(scene);
        primaryStage.setTitle("Historical Logs");
        primaryStage.show();
    }
    
    private String fetchHistoricalLogsFromDatabase() {
    	StringBuilder sb = new StringBuilder();
    	List<LogEntry> logs = db.fetchHistoricalLogs();
    	for (LogEntry log : logs) {
    		sb.append(log.toString());
    		sb.append("\n\n");
    	}
    	return sb.toString();
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
    }
}