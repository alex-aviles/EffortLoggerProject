package application;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;

//Login class
public class Login extends Application {

	private Main mainApp;
    // member variables for UI components and user input
    private TextField username = new TextField();
    private TextField password = new TextField();
    private Label messageLabel = new Label();
    
    // variables that will store the user input
    private String enteredUsername = "";
    private String enteredPassword = "";
    private JavaDatabase db = new JavaDatabase(); // handler for the database

    private boolean doesUserExist(String enteredUsername) { // check if user already exists in the database
        return db.doesUserExist(enteredUsername);
    }

    private void addUserToDatabase(String enteredUsername, String enteredPassword) { // add user to database
        db.addUser(enteredUsername, enteredPassword);
    }

    private boolean isValidUsername(String username) { // validate username according to given criteria
        if (username.length() < 5 || username.length() > 10) // if username is larger than 5 - 10 characters
            return false;
        if (!username.matches("^[a-zA-Z0-9]+$"))// if username contains forbidden characters
            return false;
        return true; // return true of conditions are met
    }

    private boolean isValidPassword(String username, String password) { // validate password according to criteria
        if (password.length() < 8 || password.length() > 12) // if created password is in excess of 8 - 12 characters
            return false;
        if (!password.matches("^(?=.*[A-Z])(?=.*\\d).+$")) // if password contains forbidden characters
            return false;
        if (password.equals(username))      // username and password shouldn't match
            return false;
        return true; // return true if provided username and password meet the conditions
    }

    private void displayInvalidInputAlert() {// display alert with input criteria for both the username and password
        Alert alert = new Alert(Alert.AlertType.ERROR);
        // setup the alert properties and display the username and password
        alert.setTitle("Invalid Input");
        alert.setHeaderText(null);
        alert.setContentText("Username criteria:\n" +
                "1. 5 to 10 characters long.\n" +
                "2. Only alphanumeric characters.\n\n" +
                "Password criteria:\n" +
                "1. 8 to 12 characters long.\n" +
                "2. At least one uppercase letter.\n" +
                "3. At least one digit.\n" +
                "4. Password should not match the username.");
        alert.showAndWait();
    }

    // overriding the start method of the application class to set up the UI
    public void start(Stage primaryStage, Main mainApp) throws Exception { // main entry point for the javafx application
    	this.mainApp = mainApp;
        // setting up the UI labels
    	// create table
    	db.createLogin();
        Label usernameLabel = new Label("Username: ");
        Label passwordLabel = new Label("Password: ");
        username.setStyle("-fx-control-inner-background: #F2F2F2;");
        password.setStyle("-fx-control-inner-background: #F2F2F2;");
        usernameLabel.setPadding(new Insets(20, 5, 5, 10));
        passwordLabel.setPadding(new Insets(5, 5, 5, 10));
        // setting up the text fields
        username.setPromptText("Enter your username");
        password.setPromptText("Enter your password");
        // setting up the buttons
        Button signUpButton = new Button("Sign Up");
        Button submitButton = new Button("Login");
        
        primaryStage.setOnShown(e -> {
            signUpButton.requestFocus();
        });

        // initialize the message label
        messageLabel.setTextFill(Color.BLUE);
        messageLabel.setVisible(false); // initially hidden
        // setting up the layout using a grid pane
        HBox buttonBox = new HBox(45);
        buttonBox.getChildren().addAll(signUpButton, submitButton);
        
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        // adding components to the grid pane
        gridPane.add(usernameLabel, 0, 0);
        gridPane.add(username, 1, 0);
        gridPane.add(passwordLabel, 0, 1);
        gridPane.add(password, 1, 1);
        gridPane.add(buttonBox, 1, 2);
        gridPane.add(messageLabel, 1, 3);
        // setting up the action when the signup button is clicked
        signUpButton.setOnAction(e -> {// signup button action
            enteredUsername = username.getText();
            enteredPassword = password.getText();
            // validation and database checks
            if (isValidUsername(enteredUsername) && isValidPassword(enteredUsername, enteredPassword)) {
                if (!doesUserExist(enteredUsername)) {
                    addUserToDatabase(enteredUsername, enteredPassword);
                    messageLabel.setText("Successfully signed up!");
                    messageLabel.setTextFill(Color.GREEN);
                    
                    // transitioning to the main menu after the short delay
                    //mainApp.changeState("LoadingPage");
                    //return to Main.java
                    //mainApp.start(primaryStage);
                } else {
                    messageLabel.setText("Username already exists!");
                    messageLabel.setTextFill(Color.RED);
                }
            } else {
                displayInvalidInputAlert();
            }
            messageLabel.setVisible(true);
        });

        // submit button clicked
        submitButton.setOnAction(e -> {
            // store text fields in variables
            enteredUsername = username.getText();
            enteredPassword = password.getText();
            // if provided username and password do not meet criteria
            if (!isValidUsername(enteredUsername) || !isValidPassword(enteredUsername, enteredPassword)) {
                displayInvalidInputAlert();
                return;
            }
            // if provided username and password meet criteria
            if (isValidUsername(enteredUsername) && isValidPassword(enteredUsername, enteredPassword)) {
                try {
                    if (db.isUserValid(enteredUsername, enteredPassword)) {
                        messageLabel.setText("Credentials Valid!");
                        messageLabel.setTextFill(Color.GREEN);
                        messageLabel.setVisible(true);
                        
                        // transitioning to the main menu after the short delay
                        mainApp.changeState("LoadingPage");
                        //return to Main.java
                        mainApp.start(primaryStage);
                    } else {
                        messageLabel.setText("Invalid username or password!");
                        messageLabel.setTextFill(Color.RED);
                    }
                } catch (Exception ex) {
                    messageLabel.setText("Error connecting to the database!");
                    messageLabel.setTextFill(Color.RED);
                }
            } else {
                messageLabel.setText("Username or password does not meet the criteria!");
                messageLabel.setTextFill(Color.RED);
            }

            messageLabel.setVisible(true);
        });
        
        // setting up the primary stage and showing it
        Scene scene = new Scene(gridPane, 1000, 500);// display the scene
        scene.getRoot().setStyle("-fx-background-color: #d9f7ff;");
        primaryStage.setTitle("Login Page");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {// main method to launch the javafx application
        launch(args);
    }

	@Override
	public void start(Stage arg0) throws Exception {
	}
}