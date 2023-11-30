package application;

import java.util.HashSet;
import java.util.Set;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.util.*;

public class AddBacklogItem extends Application {
    private Main mainApp;
    private JavaDatabase db = new JavaDatabase();
    int count;
    List<String> choices = new ArrayList<String>();
    public void start(Stage primaryStage, Main mainApp) throws Exception {
        this.mainApp = mainApp;
        
        Button backButton = new Button("Back");
        Button submitButton = new Button("Submit");
        Label projectNameLabel = new Label("Project Name:");
        Label userStoryLabel = new Label("User Story:");
        Label keyWordsLabel = new Label("Key Words:");
        Label stepLabel = new Label("Step");
        TextField userStoryField = new TextField();
        TextField keyWordsField = new TextField();
        ComboBox<String> choiceCombo = new ComboBox<>();
        Button stepOptions = new Button("Select Step Keywords");
        Button categoryOptions = new Button("Select Category Keywords");
        Button choiceOptions = new Button("Select Choice Options");
        categoryOptions.setVisible(false);
        choiceOptions.setVisible(false);
        choiceCombo.getItems().addAll("Step", "Category", "Choice");
        choiceCombo.getSelectionModel().selectFirst();
        CheckBox stepCheckBox = new CheckBox("Problem Understanding");
        CheckBox stepCheckBox2 = new CheckBox("Conceptual Plan Design"); 
        CheckBox stepCheckBox3 = new CheckBox("Requirements"); 
        CheckBox stepCheckBox4 = new CheckBox("Conceptual Design");
        CheckBox stepCheckBox5 = new CheckBox("Conceptual Design Review");
        CheckBox stepCheckBox6 = new CheckBox("Detailed Design Plan"); 
        CheckBox stepCheckBox7 = new CheckBox("Detailed Design/Prototype"); 
        CheckBox stepCheckBox8 = new CheckBox("Detailed Design Review"); 
        CheckBox stepCheckBox9 = new CheckBox("Implementation Plan"); 
        CheckBox stepCheckBox10 = new CheckBox("Test Case Generation"); 
        CheckBox stepCheckBox11 = new CheckBox("Solution Specification"); 
        CheckBox stepCheckBox12 = new CheckBox("Solution Review"); 
        CheckBox stepCheckBox13 = new CheckBox("Solution Implementation"); 
        CheckBox stepCheckBox14 = new CheckBox("Unit/System Test"); 
        CheckBox stepCheckBox15 = new CheckBox("Reflection"); 
        CheckBox stepCheckBox16 = new CheckBox("Repository Update"); 
        CheckBox stepCheckBox17 = new CheckBox("Planning"); 
        CheckBox stepCheckBox18 = new CheckBox("Information Understanding"); 
        CheckBox stepCheckBox19 = new CheckBox("Verifying"); 
        CheckBox stepCheckBox20 = new CheckBox("Outlining"); 
        CheckBox stepCheckBox21 = new CheckBox("Drafting"); 
        CheckBox stepCheckBox22 = new CheckBox("Finalizing"); 
        CheckBox stepCheckBox23 = new CheckBox("Team Meeting"); 
        CheckBox stepCheckBox24 = new CheckBox("Coach Meeting"); 
        CheckBox stepCheckBox25 = new CheckBox("Stakeholder Meeting"); 
        CheckBox stepCheckBox26 = new CheckBox("");
        CheckBox stepCheckBox27 = new CheckBox("");
        CheckBox stepCheckBox28 = new CheckBox("");
        CheckBox stepCheckBox29 = new CheckBox("");
        stepCheckBox26.setVisible(false);
		stepCheckBox27.setVisible(false);
		stepCheckBox28.setVisible(false);
		stepCheckBox29.setVisible(false);
        
        Label testLabel = new Label("");//this is a dummy label to test logic
		
		
        // Create a ComboBox for project names
        ComboBox<String> projectNameComboBox = new ComboBox<>();
        projectNameComboBox.setEditable(true); // Allow the user to add new project names

        Set<String> projectNamesSet = new HashSet<>(); // Create a set to store unique project names

        for (int i = 0; i < db.getBacklogSize(); i++) {
            String projectName = db.getBacklogProjectName(i);

            // Check if the project name is not already in the set
            if (!projectNamesSet.contains(projectName)) {
                projectNameComboBox.getItems().add(projectName); // Add it to the ComboBox
                projectNamesSet.add(projectName); // Add it to the set to prevent repeats
            }
        }

        // Handle the menu button action (you can replace this with your logic)
        backButton.setOnAction(e -> {
            mainApp.changeState("Backlog");
            mainApp.start(primaryStage);
        });
        
        submitButton.setOnAction(e -> {
        	//add to backlog and return to Backlog.java
        	db.addBacklogItem(projectNameComboBox.getValue(), userStoryField.getText(), keyWordsField.getText());
        	//return to backlog list
        	for(String choice:choices){
        		System.out.print(choice+"\n");
        	}
            mainApp.changeState("Backlog");
            mainApp.start(primaryStage);
        });
        
        choiceCombo.setOnAction(e->{
        	String selectedChoice = choiceCombo.getValue();
        	switch(selectedChoice){
        	case "Step":
        		stepCheckBox.setText("Problem Understanding");
        		stepCheckBox2.setText("Conceptual Plan Design");
        		stepCheckBox3.setText("Requirements");
        		stepCheckBox4.setText("Conceptual Design");
        		stepCheckBox5.setText("Conceptual Design Review");
        		stepCheckBox6.setText("Detailed Design Plan");
        		stepCheckBox7.setText("Detailted Design/Prototype");
        		stepCheckBox8.setText("Detailed Design Review");
        		stepCheckBox9.setText("Implementation Plan");
        		stepCheckBox10.setText("Test Case Generation");
        		stepCheckBox11.setText("Solution Specification");
        		stepCheckBox12.setText("Solution Review");
        		stepCheckBox13.setText("Solution Implementation");
        		stepCheckBox14.setText("Unit/System Test");
        		stepCheckBox15.setText("Reflection");
        		stepCheckBox16.setText("Repository Update");
        		stepCheckBox17.setText("Planning");
        		stepCheckBox18.setText("Information Understaning");
        		stepCheckBox19.setText("Verfying");
        		stepCheckBox20.setText("Outlining");
        		stepCheckBox22.setText("Drafting");
        		stepCheckBox22.setText("Finalizing");
        		stepCheckBox23.setText("Team Meeting");
        		stepCheckBox24.setText("Coach Meeting");
        		stepCheckBox25.setText("Stakeholder Meeting");
        		stepCheckBox5.setVisible(true);
        		stepCheckBox6.setVisible(true);
        		stepCheckBox7.setVisible(true);
        		stepCheckBox8.setVisible(true);
        		stepCheckBox9.setVisible(true);
        		stepCheckBox10.setVisible(true);
        		stepCheckBox11.setVisible(true);
        		stepCheckBox12.setVisible(true);
        		stepCheckBox13.setVisible(true);
        		stepCheckBox14.setVisible(true);
        		stepCheckBox15.setVisible(true);
        		stepCheckBox16.setVisible(true);
        		stepCheckBox17.setVisible(true);
        		stepCheckBox18.setVisible(true);
        		stepCheckBox19.setVisible(true);
        		stepCheckBox20.setVisible(true);
        		stepCheckBox21.setVisible(true);
        		stepCheckBox22.setVisible(true);
        		stepCheckBox22.setVisible(true);
        		stepCheckBox23.setVisible(true);
        		stepCheckBox24.setVisible(true);
        		stepCheckBox25.setVisible(true);
        		stepCheckBox26.setVisible(false);
        		stepCheckBox27.setVisible(false);
        		stepCheckBox28.setVisible(false);
        		stepCheckBox29.setVisible(false);
        		stepOptions.setVisible(true);
        		categoryOptions.setVisible(false);
        		choiceOptions.setVisible(false);
        		break;
        	case "Category":
        		stepCheckBox.setText("Plans");
        		stepCheckBox2.setText("Deliverables");
        		stepCheckBox3.setText("Interruptions");
        		stepCheckBox4.setText("Defects");
        		stepCheckBox5.setVisible(false);
        		stepCheckBox6.setVisible(false);
        		stepCheckBox7.setVisible(false);
        		stepCheckBox8.setVisible(false);
        		stepCheckBox9.setVisible(false);
        		stepCheckBox10.setVisible(false);
        		stepCheckBox11.setVisible(false);
        		stepCheckBox12.setVisible(false);
        		stepCheckBox13.setVisible(false);
        		stepCheckBox14.setVisible(false);
        		stepCheckBox15.setVisible(false);
        		stepCheckBox16.setVisible(false);
        		stepCheckBox17.setVisible(false);
        		stepCheckBox18.setVisible(false);
        		stepCheckBox19.setVisible(false);
        		stepCheckBox20.setVisible(false);
        		stepCheckBox21.setVisible(false);
        		stepCheckBox22.setVisible(false);
        		stepCheckBox22.setVisible(false);
        		stepCheckBox23.setVisible(false);
        		stepCheckBox24.setVisible(false);
        		stepCheckBox25.setVisible(false);
        		stepCheckBox26.setVisible(false);
        		stepCheckBox27.setVisible(false);
        		stepCheckBox28.setVisible(false);
        		stepCheckBox29.setVisible(false);
        		stepOptions.setVisible(false);
        		categoryOptions.setVisible(true);
        		choiceOptions.setVisible(false);
        		break;
        	case "Choice":
        		stepCheckBox.setText("Project Plan");
        		stepCheckBox2.setText("Risk Management Plan");
        		stepCheckBox3.setText("Conceptual Design Plan");
        		stepCheckBox4.setText("Detailed Design Plan");
        		stepCheckBox5.setText("Implementation Plan");
        		stepCheckBox6.setText("Conceptual Design");
        		stepCheckBox7.setText("Detailed Design");
        		stepCheckBox8.setText("Test Cases");
        		stepCheckBox9.setText("Solution");
        		stepCheckBox10.setText("Reflection");
        		stepCheckBox11.setText("Outline");
        		stepCheckBox12.setText("Draft");
        		stepCheckBox13.setText("Report");
        		stepCheckBox14.setText("Break");
        		stepCheckBox15.setText("Phone");
        		stepCheckBox16.setText("Teammate");
        		stepCheckBox17.setText("Visitor");
        		stepCheckBox18.setText("Not Specified");
        		stepCheckBox19.setText("Documentation");
        		stepCheckBox20.setText("Syntax");
        		stepCheckBox21.setText("Build, Package");
        		stepCheckBox22.setText("Assignment");
        		stepCheckBox23.setText("Interface");
        		stepCheckBox24.setText("Checking");
        		stepCheckBox25.setText("Data");
        		stepCheckBox26.setText("Function");
        		stepCheckBox27.setText("System");
        		stepCheckBox28.setText("Enviornment");
        		stepCheckBox29.setText("Other");
        		stepCheckBox5.setVisible(true);
        		stepCheckBox6.setVisible(true);
        		stepCheckBox7.setVisible(true);
        		stepCheckBox8.setVisible(true);
        		stepCheckBox9.setVisible(true);
        		stepCheckBox10.setVisible(true);
        		stepCheckBox11.setVisible(true);
        		stepCheckBox12.setVisible(true);
        		stepCheckBox13.setVisible(true);
        		stepCheckBox14.setVisible(true);
        		stepCheckBox15.setVisible(true);
        		stepCheckBox16.setVisible(true);
        		stepCheckBox17.setVisible(true);
        		stepCheckBox18.setVisible(true);
        		stepCheckBox19.setVisible(true);
        		stepCheckBox20.setVisible(true);
        		stepCheckBox21.setVisible(true);
        		stepCheckBox22.setVisible(true);
        		stepCheckBox22.setVisible(true);
        		stepCheckBox23.setVisible(true);
        		stepCheckBox24.setVisible(true);
        		stepCheckBox25.setVisible(true);
        		stepCheckBox26.setVisible(true);
        		stepCheckBox27.setVisible(true);
        		stepCheckBox28.setVisible(true);
        		stepCheckBox29.setVisible(true);
        		stepOptions.setVisible(false);
        		categoryOptions.setVisible(false);
        		choiceOptions.setVisible(true);
        		break;
        	}
        });
        stepOptions.setOnAction(e->{
        	if(stepCheckBox.isSelected() == true){
        		choices.add("Problem Understanding");
        		testLabel.setText("Problem Understanding");
        	}
        	if(stepCheckBox2.isSelected() == true){
        		choices.add("Conceptual Plan Design");
        	}
        	if(stepCheckBox3.isSelected() == true){
        		choices.add("Requirements");
        	}
        	if(stepCheckBox4.isSelected() == true){
        		choices.add("Conceptual Design");
        	}
        	if(stepCheckBox5.isSelected() == true){
        		choices.add("Conceptual Design Review");
        	}
        	if(stepCheckBox6.isSelected() == true){
        		choices.add("Detailed Design Plan");
        	}
        	if(stepCheckBox7.isSelected() == true){
        		choices.add("Detailed Design/Prototype");
        	}
        	if(stepCheckBox8.isSelected() == true){
        		choices.add("Detailed Design Review");
        	}
        	if(stepCheckBox9.isSelected() == true){
        		choices.add("Implementation Plan");
        	}
        	if(stepCheckBox10.isSelected() == true){
        		choices.add("Test Case Generation");
        	}
        	if(stepCheckBox11.isSelected() == true){
        		choices.add("Solution Specification");
        	}
        	if(stepCheckBox12.isSelected() == true){
        		choices.add("Solution Review");
        	}
        	if(stepCheckBox13.isSelected() == true){
        		choices.add("Solution Implementation");
        	}
        	if(stepCheckBox14.isSelected() == true){
        		choices.add("Unit/System Test");
        	}
        	if(stepCheckBox15.isSelected() == true){
        		choices.add("Reflection");
        	}
        	if(stepCheckBox16.isSelected() == true){
        		choices.add("Repository Update");
        	}
        	if(stepCheckBox17.isSelected() == true){
        		choices.add("Planning");
        	}
        	if(stepCheckBox18.isSelected() == true){
        		choices.add("Information Understanding");
        	}
        	if(stepCheckBox19.isSelected() == true){
        		choices.add("Verfying");
        	}
        	if(stepCheckBox20.isSelected() == true){
        		choices.add("Outlining");
        	}
        	if(stepCheckBox21.isSelected() == true){
        		choices.add("Drafting");
        	}
        	if(stepCheckBox22.isSelected() == true){
        		choices.add("Finalizing");
        	}
        	if(stepCheckBox23.isSelected() == true){
        		choices.add("Team Meeting");
        	}
        	if(stepCheckBox24.isSelected() == true){
        		choices.add("Coach Meeting");
        	}
        	if(stepCheckBox25.isSelected() == true){
        		choices.add("Stakeholder Meeting");
        	}
        	stepCheckBox.setSelected(false);
        	stepCheckBox2.setSelected(false);
        	stepCheckBox3.setSelected(false);
        	stepCheckBox4.setSelected(false);
        	stepCheckBox5.setSelected(false);
        	stepCheckBox6.setSelected(false);
        	stepCheckBox7.setSelected(false);
        	stepCheckBox8.setSelected(false);
        	stepCheckBox9.setSelected(false);
        	stepCheckBox10.setSelected(false);
        	stepCheckBox11.setSelected(false);
        	stepCheckBox12.setSelected(false);
        	stepCheckBox13.setSelected(false);
        	stepCheckBox14.setSelected(false);
        	stepCheckBox15.setSelected(false);
        	stepCheckBox16.setSelected(false);
        	stepCheckBox17.setSelected(false);
        	stepCheckBox18.setSelected(false);
        	stepCheckBox19.setSelected(false);
        	stepCheckBox20.setSelected(false);
        	stepCheckBox21.setSelected(false);
        	stepCheckBox22.setSelected(false);
        	stepCheckBox23.setSelected(false);
        	stepCheckBox24.setSelected(false);
        	stepCheckBox25.setSelected(false);
        });
        categoryOptions.setOnAction(e->{
        	if(stepCheckBox.isSelected() == true){
        		choices.add("Plans");
        	}
        	if(stepCheckBox2.isSelected() == true){
        		choices.add("Deliverables");
        	}
        	if(stepCheckBox3.isSelected() == true){
        		choices.add("Interruptions");
        	}
        	if(stepCheckBox4.isSelected() == true){
        		choices.add("Defects");
        	}
        	stepCheckBox.setSelected(false);
        	stepCheckBox2.setSelected(false);
        	stepCheckBox3.setSelected(false);
        	stepCheckBox4.setSelected(false);

        });
        choiceOptions.setOnAction(e->{
        	if(stepCheckBox.isSelected() == true){
        		choices.add("Project Plan");
        	}
        	if(stepCheckBox2.isSelected() == true){
        		choices.add("Risk Management Plan");
        	}
        	if(stepCheckBox3.isSelected() == true){
        		choices.add("Conceptual Design Plan");
        	}
        	if(stepCheckBox4.isSelected() == true){
        		choices.add("Detailed Design Plan");
        	}
        	if(stepCheckBox5.isSelected() == true){
        		choices.add("Implementation Plan");
        	}
        	if(stepCheckBox6.isSelected() == true){
        		choices.add("Conceptual Design");
        	}
        	if(stepCheckBox7.isSelected() == true){
        		choices.add("Detailed Design");
        	}
        	if(stepCheckBox8.isSelected() == true){
        		choices.add("Test Cases");
        	}
        	if(stepCheckBox9.isSelected() == true){
        		choices.add("Solution");
        	}
        	if(stepCheckBox10.isSelected() == true){
        		choices.add("Reflection");
        	}
        	if(stepCheckBox11.isSelected() == true){
        		choices.add("Outline");
        	}
        	if(stepCheckBox12.isSelected() == true){
        		choices.add("Draft");
        	}
        	if(stepCheckBox13.isSelected() == true){
        		choices.add("Report");
        	}
        	if(stepCheckBox14.isSelected() == true){
        		choices.add("Break");
        	}
        	if(stepCheckBox15.isSelected() == true){
        		choices.add("Phone");
        	}
        	if(stepCheckBox16.isSelected() == true){
        		choices.add("Teammate");
        	}
        	if(stepCheckBox17.isSelected() == true){
        		choices.add("Visitor");
        	}
        	if(stepCheckBox18.isSelected() == true){
        		choices.add("Not Specified");
        	}
        	if(stepCheckBox19.isSelected() == true){
        		choices.add("Documentation");
        	}
        	if(stepCheckBox20.isSelected() == true){
        		choices.add("Syntax");
        	}
        	if(stepCheckBox21.isSelected() == true){
        		choices.add("Build, Package");
        	}
        	if(stepCheckBox22.isSelected() == true){
        		choices.add("Assignment");
        	}
        	if(stepCheckBox23.isSelected() == true){
        		choices.add("Interface");
        	}
        	if(stepCheckBox24.isSelected() == true){
        		choices.add("Checking");
        	}
        	if(stepCheckBox25.isSelected() == true){
        		choices.add("Data");
        	}
        	if(stepCheckBox26.isSelected() == true){
        		choices.add("Function");
        	}
        	if(stepCheckBox27.isSelected() == true){
        		choices.add("System");
        	}
        	if(stepCheckBox28.isSelected() == true){
        		choices.add("Enviornment");
        	}
        	if(stepCheckBox29.isSelected() == true){
        		choices.add("Other");
        	}
        	stepCheckBox.setSelected(false);
        	stepCheckBox2.setSelected(false);
        	stepCheckBox3.setSelected(false);
        	stepCheckBox4.setSelected(false);
        	stepCheckBox5.setSelected(false);
        	stepCheckBox6.setSelected(false);
        	stepCheckBox7.setSelected(false);
        	stepCheckBox8.setSelected(false);
        	stepCheckBox9.setSelected(false);
        	stepCheckBox10.setSelected(false);
        	stepCheckBox11.setSelected(false);
        	stepCheckBox12.setSelected(false);
        	stepCheckBox13.setSelected(false);
        	stepCheckBox14.setSelected(false);
        	stepCheckBox15.setSelected(false);
        	stepCheckBox16.setSelected(false);
        	stepCheckBox17.setSelected(false);
        	stepCheckBox18.setSelected(false);
        	stepCheckBox19.setSelected(false);
        	stepCheckBox20.setSelected(false);
        	stepCheckBox21.setSelected(false);
        	stepCheckBox22.setSelected(false);
        	stepCheckBox23.setSelected(false);
        	stepCheckBox24.setSelected(false);
        	stepCheckBox25.setSelected(false);
        	stepCheckBox26.setSelected(false);
        	stepCheckBox27.setSelected(false);
        	stepCheckBox28.setSelected(false);
        	stepCheckBox29.setSelected(false);
        });
        VBox vbox = new VBox(10); // Create a VBox with spacing
        vbox.setPadding(new Insets(10)); // Add some padding
        HBox hbox = new HBox(10);
        VBox vbox2 = new VBox(10);
        VBox vbox3 = new VBox(10);
        VBox vbox4 = new VBox(10);
        VBox vbox5 = new VBox(10);
        VBox vbox6 = new VBox(10);
        //ComboBox option = new ComboBox<>();
        //option.getItems().addAll("Step", "Category", "Choice");
        vbox2.getChildren().addAll(
        		stepCheckBox,stepCheckBox2,stepCheckBox3,stepCheckBox4,
                stepCheckBox5,stepCheckBox6,stepCheckBox7,stepCheckBox8);
        // Add the labels, text fields, and button to the VBox
        vbox3.getChildren().addAll(stepCheckBox9,stepCheckBox10,stepCheckBox11, stepCheckBox12,stepCheckBox13,stepCheckBox14,stepCheckBox15,stepCheckBox16);
        vbox4.getChildren().addAll(stepCheckBox17,stepCheckBox18,
                stepCheckBox19,stepCheckBox20,stepCheckBox21,stepCheckBox22,stepCheckBox23,stepCheckBox24,stepCheckBox25);
        vbox5.getChildren().addAll(stepCheckBox26, stepCheckBox27, stepCheckBox28, stepCheckBox29);
        vbox6.getChildren().addAll(choiceCombo, stepOptions, categoryOptions, choiceOptions);
        hbox.getChildren().addAll(vbox2, vbox3,vbox4, vbox5, vbox6);
        vbox.getChildren().addAll(
            projectNameLabel, projectNameComboBox,
            userStoryLabel, userStoryField,
            keyWordsLabel, keyWordsField, stepLabel, hbox,
            submitButton
        );
        
       
        // Create an HBox for the menu button and align it to the bottom-left
        HBox menuBox = new HBox(backButton);
        menuBox.setAlignment(Pos.BOTTOM_LEFT);
        
        BorderPane borderPane = new BorderPane();
        borderPane.setBottom(menuBox);
        borderPane.setCenter(vbox);

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