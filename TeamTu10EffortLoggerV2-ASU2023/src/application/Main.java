package application;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    private String state = "Login";
    private String backlogProjectName = "";
    private String backlogUserStory = "";
    
    public void changeUserStory(String userStory) {
        this.backlogUserStory = userStory;
    }

    public String getUserStory() {
        return this.backlogUserStory;
    }

    public void changeState(String state) {
        this.state = state;
    }
    public void changeBacklogProjectName(String projectName) {
    	this.backlogProjectName = projectName;
    }
    
    public String getProjectName() {
        return this.backlogProjectName;
    }

    @Override
    public void start(Stage primaryStage) {
        switch (state) {
            case "Login":
            	try {
                    Login login = new Login();
                    login.start(primaryStage, this);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "LoadingPage":
            	try {
                    LoadingPage loadingPage = new LoadingPage();
                    loadingPage.start(primaryStage, this);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "Menu":
            	try {
                    Menu menu = new Menu();
                    menu.start(primaryStage, this);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "EffortLoggerV2":
            	try {
                    EffortLoggerV2Console effortLoggerV2Console = new EffortLoggerV2Console();
                    effortLoggerV2Console.start(primaryStage, this);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "EffortLoggerV2Editor":
            	try {
            		EffortLoggerV2Editor editor = new EffortLoggerV2Editor();
            		editor.start(primaryStage, this);
            	} catch (Exception e) {
            		e.printStackTrace();
            	}
            	break;
            case "Backlog":
            	try {
                    Backlog backlog = new Backlog();
                    backlog.start(primaryStage, this);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "SelectPlanningPoker":
            	try {
            		SelectPlanningPokerSession selectPlanningPokerSession = new SelectPlanningPokerSession();
            		selectPlanningPokerSession.start(primaryStage, this);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "HistoricalLogs":
            	try {
                    HistoricalLogs historicalLogs = new HistoricalLogs();
                    historicalLogs.start(primaryStage, this);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "AddBacklogItem":
            	try {
                    AddBacklogItem addBacklogItem = new AddBacklogItem();
                    addBacklogItem.start(primaryStage, this);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "PlanningPoker":
            	try {
            		PlanningPoker PlanningPoker = new PlanningPoker();
            		PlanningPoker.start(primaryStage, this, backlogProjectName);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "Share":
                try {
                    Share share = new Share();
                    share.start(primaryStage, this);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "HistoricalLogsDataStructure":
                try {
                    HistoricalLogsDataStructure buttonDisplay = new HistoricalLogsDataStructure();
                    buttonDisplay.start(primaryStage, this);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            default:
            	System.err.println("Unknown state: " + state);
                break;
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}