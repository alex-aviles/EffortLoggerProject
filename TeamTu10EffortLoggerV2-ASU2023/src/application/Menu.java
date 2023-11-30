package application;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Menu {
	
    private Main mainApp;
    private JavaDatabase db = new JavaDatabase();

    public void start(Stage primaryStage, Main mainApp) throws Exception {
        this.mainApp = mainApp;
        Button btnEffortLoggerV2 = new Button("Effort Logger V2");
        Button btnBacklog = new Button("Backlog");
        Button btnPlanningPoker = new Button("Planning Poker");
        Button historicalLogsBtn = new Button("Historical Logs");
        Button signOutBtn = new Button("Sign Out");

        // Increase font size for buttons
        btnEffortLoggerV2.setStyle("-fx-font-size: 20;");
        btnBacklog.setStyle("-fx-font-size: 20;");
        btnPlanningPoker.setStyle("-fx-font-size: 20;");
        historicalLogsBtn.setStyle("-fx-font-size: 20;");
        signOutBtn.setStyle("-fx-font-size: 20;");

        btnEffortLoggerV2.setOnAction(e -> {
            mainApp.changeState("EffortLoggerV2");
            db.createLogs();
            mainApp.start(primaryStage);
        });
        btnBacklog.setOnAction(e -> {
            mainApp.changeState("Backlog");
            db.createBacklog();
            mainApp.start(primaryStage);
        });
        btnPlanningPoker.setOnAction(e -> {
            mainApp.changeState("SelectPlanningPoker");
            mainApp.start(primaryStage);
        });
        historicalLogsBtn.setOnAction(e -> {
            mainApp.changeState("HistoricalLogs");
            mainApp.start(primaryStage);
        });
        signOutBtn.setOnAction(e -> {
            mainApp.changeState("Login");
            mainApp.start(primaryStage);
        });

        // Increase font size for the main menu label
        Label mainMenuLabel = new Label("EffortLogger v2 Main Menu");
        mainMenuLabel.setStyle("-fx-font-size: 36;");
        mainMenuLabel.setPadding(new Insets(20, 5, 5, 10));

        // Create a VBox for the label and buttons
        VBox menuBox = new VBox(10, btnEffortLoggerV2, btnBacklog, btnPlanningPoker);
        menuBox.setAlignment(Pos.CENTER);

        // Create an HBox for the "Sign Out" button
        HBox signOutBox = new HBox(signOutBtn);
        signOutBox.setPadding(new Insets(5, 10, 5, 10));

        // Create a BorderPane for the layout
        BorderPane layout = new BorderPane();
        layout.setCenter(menuBox);
        layout.setTop(mainMenuLabel);
        layout.setBottom(signOutBox);
        BorderPane.setAlignment(mainMenuLabel, Pos.CENTER);

        Scene scene = new Scene(layout, 1000, 500);
        scene.getRoot().setStyle("-fx-background-color: #d9f7ff;");
        primaryStage.setTitle("Main Menu");
        primaryStage.setScene(scene);
    }
} // end of class

