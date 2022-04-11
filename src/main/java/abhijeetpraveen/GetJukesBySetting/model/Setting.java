package abhijeetpraveen.GetJukesBySetting.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Set;

public class Setting {

    private final String settingID;
    private Set<String> settingRequirements;

    public Setting(@JsonProperty("id") String settingID, @JsonProperty("requirements") Set<String> settingRequirements) {
        this.settingID = settingID;
        this.settingRequirements = settingRequirements;
    }

    public String getSettingID() {
        return this.settingID;
    }

    public Set<String> getSettingRequirements() {
        return this.settingRequirements;
    }

    public void setSettingRequirements(Set<String> settingRequirements) {
        this.settingRequirements = settingRequirements;
    }
}
