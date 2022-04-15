package abhijeetpraveen.GetJukesBySetting.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Set;

public class Setting {

    private final String settingID;
    private Set<String> settingRequirements;

    /**
     * This is the constructor for the Setting class
     * @param settingID - represents the ID of the setting
     * @param settingRequirements - represents a set of string that corresponds to the requirements of the setting
     */
    public Setting(@JsonProperty("id") String settingID, @JsonProperty("requirements") Set<String> settingRequirements) {
        this.settingID = settingID;
        this.settingRequirements = settingRequirements;
    }

    /**
     * a simple getter for the id of the Setting
     * @return - the string corresponding to the ID of that setting
     */
    public String getSettingID() {
        return this.settingID;
    }

    /**
     * a simple getter for the requirements of the Setting
     * @return - a set of strings corresponding to the requirements of that setting
     */
    public Set<String> getSettingRequirements() {
        return this.settingRequirements;
    }

    /**
     * this method sets the requirements of the setting to the settingRequirements set that has been passed as a parameter
     * @param settingRequirements - a set consisting of the setting requirements
     */
    public void setSettingRequirements(Set<String> settingRequirements) {
        this.settingRequirements = settingRequirements;
    }
}
