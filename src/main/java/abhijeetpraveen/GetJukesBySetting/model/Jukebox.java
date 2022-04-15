package abhijeetpraveen.GetJukesBySetting.model;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class Jukebox {

    private final String jukeID;
    private final String jukeModel;
    private final List<HashMap<String,String>> jukeComponents;
    private Set<String> componentNames;
    public static final Set<String> allModels = new HashSet<>();

    public Jukebox(@JsonProperty("id") String jukeID, @JsonProperty("model") String jukeModel, @JsonProperty("components") List<HashMap<String,String>> jukeComponents) {
        this.jukeID = jukeID;
        this.jukeModel = jukeModel;
        this.jukeComponents = jukeComponents;
    }

    public String getJukeID() {
        return this.jukeID;
    }

    public String getJukeModel() {
        return this.jukeModel;
    }

    public List<HashMap<String, String>> getJukeComponents() {
        return this.jukeComponents;
    }

    public Set<String> getComponentNames() {
        return this.componentNames;
    }

    public void setComponentNames(Set<String> componentNames) {
        this.componentNames = componentNames;
    }
}
