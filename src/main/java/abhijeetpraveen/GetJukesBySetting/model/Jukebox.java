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


    /**
     * This is the constructor for the Jukebox class
     * @param jukeID - represents the ID of the jukebox
     * @param jukeModel - represents the model of the jukebox
     * @param jukeComponents - represents the components that this jukebox supports
     */
    public Jukebox(@JsonProperty("id") String jukeID, @JsonProperty("model") String jukeModel, @JsonProperty("components") List<HashMap<String,String>> jukeComponents) {
        this.jukeID = jukeID;
        this.jukeModel = jukeModel;
        this.jukeComponents = jukeComponents;
    }

    /**
     * a simple getter for the id of the Jukebox
     * @return - the string corresponding to the ID of that jukebox
     */
    public String getJukeID() {
        return this.jukeID;
    }

    /**
     * a simple getter for the model of the jukebox
     * @return - the string corresponding to the model of that jukebox
     */
    public String getJukeModel() {
        return this.jukeModel;
    }

    /**
     * a simple getter for the components supported by a jukebox
     * @return - a list of hashmaps, where the hashmaps contain the name of all the components supported by that jukebox
     */
    public List<HashMap<String, String>> getJukeComponents() {
        return this.jukeComponents;
    }

}
