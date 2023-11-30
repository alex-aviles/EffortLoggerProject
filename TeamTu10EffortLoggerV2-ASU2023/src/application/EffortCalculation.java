package application;

public class EffortCalculation {
    //private Main mainApp;
    private JavaDatabase db = new JavaDatabase();
    
    private String entryStep;
    private String entryCat;
    private String entryChoice;
    private long timeTaken;
    
    int generatedPoint = 0;
    
    public int getGeneratedPoint() {
    	generatedPoint = Calculate(entryStep, entryCat, entryChoice, timeTaken);
    	return generatedPoint;
    }
    
    public String getStep() {
    	return entryStep;
    }
    public String getCat() {
    	return entryCat;
    }
    public String getChoice() {
    	return entryChoice;
    }
    public long getTime() {
    	return timeTaken;
    }
    
    public void getEntry(String projectName) {
    	
    	this.entryStep = db.getEntryStep(projectName);
    	this.entryCat = db.getEntryCat(projectName);
    	this.entryChoice = db.getEntryChoice(projectName);
    	this.timeTaken = db.getEntryTime(projectName);
        
    } // end of getEntry

    public int Calculate(String step, String category, String choice, long time) {
    	
    	// Determine values
    	switch(step) {
    		case "Problem Understanding":
    			generatedPoint += 1;
    			break;
    		case "Conceptual Design Plan":
    			generatedPoint += 2;
    			break;
    		case "Requirements":
    			generatedPoint += 1;
    			break;
    		case "Conceptual Design":
    			generatedPoint += 2;
    			break;
    		case "Conceptual Design Review":
    			generatedPoint += 1;
    			break;
    		case "Detailed Design Plan":
    			generatedPoint += 2;
    			break;
    		case "Detailed Design/Prototype":
    			generatedPoint += 3;
    			break;
    		case "Detailed Design Review":
    			generatedPoint += 1;
    			break;
    		case "Implementation Plan":
    			generatedPoint += 2;
    			break;
    		case "Test Case Generation":
    			generatedPoint += 3;
    			break;
    		case "Solution Specification":
    			generatedPoint += 2;
    			break;
    		case "Solution Review":
    			generatedPoint += 1;
    			break;
    		case "Solution Implementation":
    			generatedPoint += 3;
    			break;
    		case "Unit/System Test":
    			generatedPoint += 2;
    			break;
    		case "Reflection":
    			generatedPoint += 1;
    			break;
    		case "Repository Update":
    			generatedPoint += 1;
    			break;
    		case "Planning":
    			generatedPoint += 2;
    			break;
    		case "Information Gathering":
    			generatedPoint += 2;
    			break;
    		case "Information Understanding":
    			generatedPoint += 1;
    			break;
    		case "Verifying":
    			generatedPoint += 2;
    			break;
    		case "Outlining":
    			generatedPoint += 2;
    			break;
    		case "Drafting":
    			generatedPoint += 3;
    			break;
    		case "Finalizing":
    			generatedPoint += 1;
    			break;
    		case "Team Meeting":
    			generatedPoint += 0;
    			break;
    		case "Coach Meeting":
    			generatedPoint += 0;
    			break;
    		case "Stakeholder Meeting":
    			generatedPoint += 0;
    			break;
    		default:
    			break;
    	} // end of step switch
    	
    	switch(category) {
			case "Plans":
				generatedPoint += 2;
			
				switch(choice) {
				case "Project Plan":
					generatedPoint += 5;
					break;
				case "Risk Management Plan":
					generatedPoint += 4;
					break;
				case "Conceptual Design Plan":
					generatedPoint += 2;
					break;
				case "Detailed Design Plan":
					generatedPoint += 3;
					break;
				case "Implementation Plan":
					generatedPoint += 5;
					break;
				default:
					break;
				} // end of choice switch
					break;
		
			case "Deliverables":
				generatedPoint += 3;
			
				switch(choice) {
				case "Conceptual Design":
					generatedPoint += 3;
					break;
				case "Detailed Design":
					generatedPoint += 3;
					break;
				case "Test Cases":
					generatedPoint += 4;
					break;
				case "Solution":
					generatedPoint += 5;
					break;
				case "Reflection":
					generatedPoint += 1;
					break;
				case "Outline":
					generatedPoint += 2;
					break;
				case "Draft":
					generatedPoint += 3;
					break;
				case "Report":
					generatedPoint += 4;
					break;
				case "Other":
					generatedPoint += 1;
					break;
				default:
					break;
				} // end of choice switch
			break;
		
			case "Interruptions":
				generatedPoint += 0;
			
				switch(choice) {
					case "Break":
						generatedPoint += 2;
						break;
					case "Phone":
						generatedPoint += 0;
						break;
					case "Teammate":
						generatedPoint += 1;
						break;
					case "Visitor":
						generatedPoint += 0;
						break;
					case "Other":
						generatedPoint += 0;
						break;
					default:
						break;
				} // end of choice switch
				break;
		
			case "Defects":
				generatedPoint += 1;
			
				switch(choice) {
					case "Not Specified":
						generatedPoint += 1;
						break;
					case "Documentation":
						generatedPoint += 1;
						break;
					case "Syntax":
						generatedPoint += 3;
						break;
					case "Build, Package":
						generatedPoint += 2;
						break;
					case "Assignment":
						generatedPoint += 2;
						break;
					case "Interface":
						generatedPoint += 3;
						break;
					case "Checking":
						generatedPoint += 3;
						break;
					case "Data":
						generatedPoint += 4;
						break;
					case "Function":
						generatedPoint += 5;
						break;
					case "System":
						generatedPoint += 5;
						break;
					case "Environment":
						generatedPoint += 1;
						break;
					default:
						break;
				} // end of choice switch
				break;
		
		default:
			break;
    	} // end of category switch
    	
    	// time points
    	if (0 <= time && time <= 300) {
    		generatedPoint += 0;
    	}
    	if (301 <= time && time <= 600) {
    		generatedPoint += 1;
    	}
    	if (601 <= time && time <= 900) {
    		generatedPoint += 2;
    	}
    	if (901 <= time && time <= 1800) {
    		generatedPoint += 3;
    	}
    	if (1801 <= time && time <= 2700) {
    		generatedPoint += 5;
    	}
    	if (2701 <= time && time <= 3600) {
    		generatedPoint += 8;
    	}
    	if (3601 <= time && time <= 7200) {
    		generatedPoint += 13;
    	}
    	if (7201 <= time && time <= 10800) {
    		generatedPoint += 21;
    	}
    	if (10801 <= time && time <= 14400) {
    		generatedPoint += 34;
    	}
    	if (14401 <= time && time <= 21600) {
    		generatedPoint += 55;
    	}
    	if ((21601 <= time && time <= 28800) || time > 28800) {
    		generatedPoint += 89;
    	}
    	
    	// Fibonacci
    	if (generatedPoint == 0) {
    		return generatedPoint;
    	} else if (1 <= generatedPoint && generatedPoint <= 2) {
    		generatedPoint = 1;
    		return generatedPoint;
    	} else if (3 <= generatedPoint && generatedPoint <= 4) {
    		generatedPoint = 2;
    		return generatedPoint;
    	} else if (5 <= generatedPoint && generatedPoint <= 6) {
    		generatedPoint = 3;
    		return generatedPoint;
    	} else if (7 <= generatedPoint && generatedPoint <= 10) {
    		generatedPoint = 5;
    		return generatedPoint;
    	} else if (11 <= generatedPoint && generatedPoint <= 16) {
    		generatedPoint = 8;
    		return generatedPoint;
    	} else if (17 <= generatedPoint && generatedPoint <= 26) {
    		generatedPoint = 13;
    		return generatedPoint;
    	} else if (27 <= generatedPoint && generatedPoint <= 42) {
    		generatedPoint = 21;
    		return generatedPoint;
    	} else if (43 <= generatedPoint && generatedPoint <= 68) {
    		generatedPoint = 23;
    		return generatedPoint;
    	} else if (69 <= generatedPoint && generatedPoint <= 84) {
    		generatedPoint = 55;
    		return generatedPoint;
    	} else if (85 <= generatedPoint && generatedPoint <= 100) {
    		generatedPoint = 89;
    		return generatedPoint;
    	} else {
    		return generatedPoint;
    	} // end of if sequence
    } // end of method       
} // end of class

