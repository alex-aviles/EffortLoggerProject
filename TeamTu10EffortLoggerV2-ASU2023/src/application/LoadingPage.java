package application;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class LoadingPage extends Application {
	
    private Main mainApp;
    private Label loadingLabel;
    private int dotCount = 0;
    private String[] dots = {".", "..", "...", " "};
    private Timeline animation;

    public void start(Stage primaryStage, Main mainApp) throws Exception {
        this.mainApp = mainApp;

        // Create a Label with initial text
        loadingLabel = new Label("Logging in");
        loadingLabel.setStyle("-fx-font-size: 36; -fx-text-fill: green;");

        // Create a BorderPane to center the label
        BorderPane root = new BorderPane(loadingLabel);

        // Create a Scene with the specified dimensions
        Scene scene = new Scene(root, 1000, 500);
        scene.getRoot().setStyle("-fx-background-color: #d9f7ff;");
        primaryStage.setScene(scene);
        primaryStage.setTitle("Loading");
        primaryStage.show();

        // Create a timeline for the loading animation
        animation = new Timeline(
            new KeyFrame(Duration.seconds(0.43), event -> updateLoadingLabel())
        );
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.play();

        // Create a PauseTransition for the wait period
        PauseTransition pause = new PauseTransition(Duration.seconds(4));
        pause.setOnFinished(event -> {
            mainApp.changeState("Menu");
            mainApp.start(primaryStage);
        });
        pause.play();
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
    }

    private void updateLoadingLabel() {
        loadingLabel.setText("Logging in" + dots[dotCount]);
        dotCount = (dotCount + 1) % dots.length;
    }
}
