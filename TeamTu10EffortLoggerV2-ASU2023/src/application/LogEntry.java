package application;

public class LogEntry {
	
	private int entryId;
	private String projectName;
	private String storyChoice;
	private String step;
	private String category;
	private String choice;
	private long timeRecord;
	
	//constructor
	public LogEntry(int entryId, String projectName, String storyChoice, String step, String category, String choice, long timeTaken) {
		this.entryId = entryId;
		this.storyChoice = storyChoice;
		this.projectName = projectName;
		this.step = step;
		this.category = category;
		this.choice = choice;
		this.timeRecord = timeTaken;
	}
	
	public int getEntryId() { //11.28.23 Change Armaani
		return entryId;
	}
	
	public String getProjectName() {
		return projectName;
	}
	
	public String getStory() {
		return storyChoice;
	}
	
	public String getStep() {
		return step;
	}
	
	public String getCategory() {
		return category;
	}
	
	public String getChoice() {
		return choice;
	}
	
	public long getTime() {
		return timeRecord;
	}
	
	public String toString() {
		return "Project Name: " + projectName + "\n" +
				"User Story: " + storyChoice + "\n" +
				"Step: " + step + "\n" +
				"Category: " + category + "\n" +
				"Choice: " + choice + "\n" + "Time: " + timeRecord + "\n" + "EntryId: " + entryId;
	}
}