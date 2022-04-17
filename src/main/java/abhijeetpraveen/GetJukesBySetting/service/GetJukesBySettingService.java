package abhijeetpraveen.GetJukesBySetting.service;

import abhijeetpraveen.GetJukesBySetting.model.Jukebox;
import abhijeetpraveen.GetJukesBySetting.model.Setting;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.regex.Pattern;

/**
 * A class where we define the main method of our REST API and make the mock API calls
 */
@Service
public class GetJukesBySettingService {

    /**
     * This is an ObjectMapper object which allows conversion between an object that java recognizes
     * and a JSON response. The vice versa is true as well.
     */
    private static final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * This method allows us to get the body of the response corresponding to a URI.
     * @param uri  this represents the URI that we make the call to
     * @return  the body of the response
     * @throws ExecutionException  if there is an exception during the execution
     * @throws InterruptedException  if the thread encounters an interruption
     */
    private static String getResponse(String uri) throws ExecutionException, InterruptedException {

        // this is the http client we will be using to perform our mock calls to the API
        HttpClient client = HttpClient.newHttpClient();

        // this is building the actual request that we will send to the API
        HttpRequest request = HttpRequest.newBuilder(URI.create(uri)).header("accept", "application/json").build();

        // here we send our asynchronous request to the API
        CompletableFuture<HttpResponse<String>> responseFuture = client.sendAsync(request, HttpResponse.BodyHandlers.ofString());

        String response;

        // we get the body of our response that we will be returning
        response = responseFuture.get().body();

        return response;

    }

    /**
     * This is the method that we use to get all the jukes that are returned by the API
     * @return a list containing all the jukeboxes
     * @throws JsonProcessingException if there was a problem when working with JSON
     * @throws ExecutionException if there is an exception during the execution
     * @throws InterruptedException if the thread encounters an interruption
     */
    public List<Jukebox> getJukes() throws JsonProcessingException, ExecutionException, InterruptedException {

        // using the method described above to get the response body containing all the Jukes
        String response = getResponse("https://my-json-server.typicode.com/touchtunes/tech-assignment/jukes");

        // initializing our list that will hold all the jukes
        List<Jukebox> jukesList;

        // convert the JSON response body to a list of jukes that we will return
        jukesList = objectMapper.readValue(response, new TypeReference<>(){});

        return jukesList;

    }

    /**
     * This is the method that we use to get all the settings that are returned by the API
     * @return a list containing all the settings
     * @throws JsonProcessingException if there was a problem when working with JSON
     * @throws ExecutionException if there is an exception during the execution
     * @throws InterruptedException if the thread encounters an interruption
     */
    public List<Setting> getSettings() throws JsonProcessingException, ExecutionException, InterruptedException {

        // using the method described above to get the response body containing all the Settings
        String response = getResponse("https://my-json-server.typicode.com/touchtunes/tech-assignment/settings");

        // convert the JSON response body to a HashMap where the hashmap value holds all the settings
        HashMap<String,List<Object>> settingsBody = objectMapper.readValue(response,new TypeReference<>(){});

        // initializing our list that will hold all the settings
        List<Setting> settingList;

        // get the list of settings from the HashMap described above
        settingList = objectMapper.readValue(objectMapper.writeValueAsString(settingsBody.get("settings")), new TypeReference<>(){});

        return settingList;
    }

    /**
     * This is the main method of our REST API that allows a user to get jukes that support their input params
     * @param settingID the setting ID for which the user wishes to find all compatible jukes
     * @param model an optional parameter where the user can filter jukes by their model
     * @param offset an optional parameter where the user can indicate at what index the page should start
     * @param limit an optional parameter where the user can indicate the page size
     * @return a string corresponding to a paginated list of all the supporting jukes
     * @throws ExecutionException if there is an exception during the execution
     * @throws IllegalArgumentException an illegal input exception that we throw ourselves
     * @throws JsonProcessingException if there was a problem when working with JSON
     * @throws InterruptedException if the thread encounters an interruption
     */
    public String getJukesBySetting(String settingID, String model, String offset, String limit) throws ExecutionException, IllegalArgumentException, JsonProcessingException, InterruptedException {

        // using the methods we wrote above to get all the jukes and settings returned by the API
        List<Jukebox> jukeboxList = getJukes();
        List<Setting> settingList = getSettings();

        // since the settingID is a required parameter, if it is not in the requested params, we throw an appropriate error message
        if (settingID == null || settingID.trim().length() == 0) throw new IllegalArgumentException("Error " + HttpStatus.NOT_ACCEPTABLE + " : No Setting ID was given. Please enter a valid id");

        // this defines the pattern an input settingID should follow
        Pattern idPattern = Pattern.compile("[a-z0-9]{8}-[a-z0-9]{4}-[a-z0-9]{4}-[a-z0-9]{4}-[a-z0-9]{12}");

        // we check to see if the input settingID matches the pattern, if not, we throw an appropriate error message
        if (!idPattern.matcher(settingID).matches()) throw new IllegalArgumentException("Error " + HttpStatus.NOT_ACCEPTABLE + " : " + settingID + " is an invalid ID");

        // initializing an arraylist that will hold all the settingIDs of the settings that were returned by the API
        ArrayList<String> allSettingsIDs = new ArrayList<>();

        // adding all the settingIDs to our arraylist
        for (Setting setting : settingList) allSettingsIDs.add(setting.getSettingID());

        // if the arraylist holding all the settingIDs does not contain the input settingID, we throw an appropriate error message
        if (!allSettingsIDs.contains(settingID)) throw new IllegalArgumentException("Error " + HttpStatus.NOT_FOUND + " : The ID (" + settingID + ")" + " has no matching setting");

        // initializing an arraylist that will hold all the models of the jukes that were returned by the API
        ArrayList<String> allJukeModels = new ArrayList<>();

        // adding all the models to our arraylist
        for (Jukebox jukebox : jukeboxList) allJukeModels.add(jukebox.getJukeModel());

        // we will check if the user wishes to filter the output by model type
        boolean modelInParams = !(model.trim().length() == 0);

        // if the user has specified a model but if the arraylist holding all the models does not contain the input model, we throw an appropriate error message
        if (!allJukeModels.contains(model) && modelInParams) throw new IllegalArgumentException("Error " + HttpStatus.NOT_FOUND + " : " + model + " is not a valid model");

        // initializing the list that will hold all the jukes that actually support the input params
        List<Jukebox> supportingJukes = new ArrayList<>();

        // initializing the Setting instance that will hold the setting corresponding to the input settingID
        Setting specifiedSetting = null;

        // assigning the setting instance to be the setting that has been specified in the input
        for (Setting setting : settingList) {
            if (setting.getSettingID().equals(settingID)) {
                specifiedSetting = setting;
            }
        }

        // confirming that the specified setting has been found
        assert specifiedSetting != null;

        for (Jukebox juke : jukeboxList) {

            // getting all the component names of the juke (method is described later)
            Set<String> componentNames = getJukeComponentNames(juke);

            // if the user wishes to filter by the model, we only add the jukes of that model
            // that support all the requirements of that setting
            if (modelInParams) {
                if (juke.getJukeModel().equals(model) && componentNames.containsAll(specifiedSetting.getSettingRequirements())) {
                    supportingJukes.add(juke);
                }

            //  if the user has not specified a model in the params, we just add the jukes that support
            // all the requirements of that setting
            } else if (componentNames.containsAll(specifiedSetting.getSettingRequirements())) {
                supportingJukes.add(juke);
            }
        }

        // we check to see if the user has specified an offset and/or a limit in the input params
        boolean offsetInParams = !(offset.trim().length() == 0);
        boolean limitInParams = !(limit.trim().length() == 0);

        // initializing the integers that will hold the offset and the limit
        int specifiedOffset = 0;
        int specifiedLimit = 0;

        // handling all the errors regarding the offset param
        if (offsetInParams) {
            try {

                // attempting to convert the offset string to an integer
                specifiedOffset = Integer.parseInt(offset);

            } catch (Exception error) {

                // if the conversion throws an error, we throw the appropriate error message ourselves.
                throw new IllegalArgumentException("Error " + HttpStatus.NOT_ACCEPTABLE + " : Offset must be an integer");
            }

            // if the converted integer is negative or 0, we throw the appropriate the error message
            if (specifiedOffset <= 0) throw new IllegalArgumentException("Error " + HttpStatus.NOT_ACCEPTABLE + " : Please provide a positive Offset");

            // if an offset has been specified but the list containing the supporting jukes is empty, we return an appropriate message
            if (supportingJukes.isEmpty()) return "No Jukes support the given parameter(s)";

            // if the specified offset is larger than the actual number of supporting jukes, we throw an appropriate error message
            if (specifiedOffset > supportingJukes.size()) throw new IllegalArgumentException("Error " + HttpStatus.NOT_ACCEPTABLE + " : There are only " + supportingJukes.size() + " jukes that support the given parameter(s). Please specify an offset lower than this number.");
        }

        // handling all the errors regarding the limit param
        if (limitInParams) {
            try {

                // attempting to convert the limit string to an integer
                specifiedLimit = Integer.parseInt(limit);

            } catch (Exception error) {

                // if the conversion throws an error, we throw the appropriate error message ourselves.
                throw new IllegalArgumentException("Error " + HttpStatus.NOT_ACCEPTABLE + " : Limit must be an integer");

            }

            // if the converted integer is negative or 0, we throw the appropriate the error message
            if (specifiedLimit <= 0) throw new IllegalArgumentException("Error " + HttpStatus.NOT_ACCEPTABLE + " : Please provide a positive Limit");
        }

        // creating the sublist of supporting jukes when both offset and limit have been specified
        if (offsetInParams && limitInParams) {

            // arrays have indexing that starts at 0, therefore, we subtract 1 from the specified offset
            int offsetIndex = specifiedOffset - 1;

            // if the limit is bigger than the actual size of the list, we just set the limit to be the size
            // a bigger limit should not throw an error as the number of jukes shown will be inside the limit anyway
            int limitIndex = Math.min(offsetIndex+specifiedLimit, supportingJukes.size());

            // returning the sublist of jukes as a string (method described later)
            return gettingJukesAsString(supportingJukes.subList(offsetIndex,limitIndex));
        }

        // if only the offset is specified, we return the corresponding sublist with the limit set to the end of the list
        if (offsetInParams) return gettingJukesAsString(supportingJukes.subList(specifiedOffset - 1, supportingJukes.size()));

        // if only the limit is specified, we set the limit of the sublist to be the minimum between the specified limit
        // and the actual list size, then the actual sublist from the first index to that limit is returned
        if (limitInParams) {

            int limitIndex = Math.min(specifiedLimit, supportingJukes.size());
            return gettingJukesAsString(supportingJukes.subList(0,limitIndex));
        }

        // returning the string corresponding to the list of jukes that support the input params
        return gettingJukesAsString(supportingJukes);

    }

    /**
     * This method converts the list of supported jukes into a string that is easy to read for a user
     * @param supportedJukes list corresponding to the jukes that we wish to return as a pretty string
     * @return a string corresponding to a paginated list of supported jukes
     */
    private String gettingJukesAsString(List<Jukebox> supportedJukes) {

        // if the input list is empty, we return an appropriate message
        if (supportedJukes.isEmpty()) return "No Jukes support the given parameter(s)";

        // initializing the string builder that will be returned as a string
        StringBuilder output = new StringBuilder();

        // since there were no errors thrown and the list was created with a success, we show the appropriate message
        // at the top of the screen
        output.append("Status: ").append(HttpStatus.OK).append(" (Success)").append("<br>").append("<br>");

        // set the initial count to 1 to be shown in the paginated list
        int count = 1;

        // iterate through input list and build our string
        // we include the count, the ID, model, and the component names of the juke
        for (Jukebox juke : supportedJukes) {
            output.append(count).append(". ").append("ID: ").append(juke.getJukeID());
            output.append("<br>").append("Model: ").append(juke.getJukeModel());
            output.append("<br>").append("Components: ").append(getJukeComponentNames(juke));

            // adding "<br>" tags to create a new line
            output.append("<br>").append("<br>");

            // increment the count for the next juke
            count++;
        }

        // returning the string that we will show
        return output.toString();
    }

    /**
     * This is a simple getter for the component names of a juke
     * @param juke whose components' names we wish to get
     * @return a set of strings corresponding to the names of the juke's components
     */
    private Set<String> getJukeComponentNames(Jukebox juke) {
        Set<String> componentNames = new HashSet<>();
        for (HashMap<String,String> component : juke.getJukeComponents()) {
            componentNames.add(component.get("name"));
        }
        return componentNames;
    }
}
