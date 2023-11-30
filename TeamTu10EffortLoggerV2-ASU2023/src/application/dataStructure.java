// Author: Alexander Aviles
// Class file for the local data structure source
package application; // declare package location

public class dataStructure {
    // private variable declarations
    private String projectName;
    private String lifecycleStep;
    private String chosenCat;
    private String choiceOption;

    // dataStructure object constructor
    public dataStructure(String projectName, String lifecycleStep, String chosenCat, String choiceOption) {
        this.projectName = projectName;
        this.lifecycleStep = lifecycleStep;
        this.chosenCat = chosenCat;
        this.choiceOption = choiceOption;
    }

    // project name getter
    public String getProject() {
        return projectName;
    }

    // project name setter
    public void setName(String name) {
        this.projectName = name;
    }

    // life cycle step getter
    public String getStep() {
        return lifecycleStep;
    }

    // life cycle step setter
    public void setStep(String step) {
        this.lifecycleStep = step;
    }

    // category getter
    public String getCategory() {
        return chosenCat;
    }

    // category setter
    public void setCategory(String category) {
        this.chosenCat = category;
    }

    // choice getter
    public String getChoice() {
        return choiceOption;
    }

    // choice setter
    public void setChoice(String choice) {
        this.choiceOption = choice;
    }
}