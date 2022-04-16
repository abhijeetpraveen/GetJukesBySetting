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

@Service
public class GetJukesBySettingService {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    private static String getResponse(String uri) throws ExecutionException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder(URI.create(uri)).header("accept", "application/json").build();

        CompletableFuture<HttpResponse<String>> responseFuture = client.sendAsync(request, HttpResponse.BodyHandlers.ofString());

        String response;
        response = responseFuture.get().body();

        return response;

    }

    public List<Jukebox> getJukes() throws JsonProcessingException, ExecutionException, InterruptedException {

        String response = getResponse("https://my-json-server.typicode.com/touchtunes/tech-assignment/jukes");
        List<Jukebox> jukesList;
        jukesList = objectMapper.readValue(response, new TypeReference<>(){});

        return jukesList;

    }

    public List<Setting> getSettings() throws JsonProcessingException, ExecutionException, InterruptedException {
        String response = getResponse("https://my-json-server.typicode.com/touchtunes/tech-assignment/settings");

        HashMap<String,List<Object>> settingsBody = objectMapper.readValue(response,new TypeReference<>(){});
        List<Setting> settingList;
        settingList = objectMapper.readValue(objectMapper.writeValueAsString(settingsBody.get("settings")), new TypeReference<>(){});

        return settingList;
    }

    public String getJukesBySetting(String settingID, String model, String offset, String limit) throws ExecutionException, IllegalArgumentException, JsonProcessingException, InterruptedException {
        List<Jukebox> jukeboxList = getJukes();
        List<Setting> settingList = getSettings();

        if (settingID == null || settingID.trim().length() == 0) throw new IllegalArgumentException("Error " + HttpStatus.NOT_ACCEPTABLE + " : No Setting ID was given. Please enter a valid id");

        Pattern idPattern = Pattern.compile("[a-z0-9]{8}-[a-z0-9]{4}-[a-z0-9]{4}-[a-z0-9]{4}-[a-z0-9]{12}");

        if (!idPattern.matcher(settingID).matches()) throw new IllegalArgumentException("Error " + HttpStatus.NOT_ACCEPTABLE + " : An invalid Setting ID was provided.");

        ArrayList<String> allSettingsIDs = new ArrayList<>();

        for (Setting setting : settingList) allSettingsIDs.add(setting.getSettingID());

        if (!allSettingsIDs.contains(settingID)) throw new IllegalArgumentException("Error " + HttpStatus.NOT_FOUND + " : Given ID has no matching setting");

        ArrayList<String> allJukeModels = new ArrayList<>();

        for (Jukebox jukebox : jukeboxList) allJukeModels.add(jukebox.getJukeModel());

        boolean modelInParams = !model.equals("");

        if (!allJukeModels.contains(model) && modelInParams) throw new IllegalArgumentException("Error " + HttpStatus.NOT_FOUND + " : Given model type is invalid");

        ArrayList<String> supportingJukes = new ArrayList<>();

        Setting specifiedSetting = null;

        for (Setting setting : settingList) {
            if (setting.getSettingID().equals(settingID)) {
                specifiedSetting = setting;
            }
        }
        assert specifiedSetting != null;

        for (Jukebox juke : jukeboxList) {
            Set<String> componentNames = new HashSet<>();
            for (HashMap<String,String> component : juke.getJukeComponents()) {
                componentNames.add(component.get("name"));
            }
            if (modelInParams) {
                if (juke.getJukeModel().equals(model) && componentNames.containsAll(specifiedSetting.getSettingRequirements())) {
                    supportingJukes.add(juke.getJukeID());
                }
            }
            else if (componentNames.containsAll(specifiedSetting.getSettingRequirements())) {
                supportingJukes.add(juke.getJukeID());
            }
        }

        boolean offsetInParams = !offset.equals("");
        boolean limitInParams = !limit.equals("");

        int specifiedOffset = 0;
        int specifiedLimit = 0;


        if (offsetInParams) {
            try {
                specifiedOffset = Integer.parseInt(offset);
            } catch (Exception error) {
                throw new IllegalArgumentException("Error " + HttpStatus.NOT_ACCEPTABLE + " : Offset must be an integer type");
            }
            if (specifiedOffset < 0) throw new IllegalArgumentException("Error " + HttpStatus.NOT_ACCEPTABLE + " : Please provide a positive Offset");
            if (specifiedOffset > supportingJukes.size()) throw new IllegalArgumentException("Error " + HttpStatus.NOT_ACCEPTABLE + " : There are only " + supportingJukes.size() + " jukes that support this setting. Please specify an offset lower than this number.");
        }

        if (limitInParams) {
            try {
                specifiedLimit = Integer.parseInt(limit);
            } catch (Exception error) {
                throw new IllegalArgumentException("Error " + HttpStatus.NOT_ACCEPTABLE + " : Limit must be an integer type");
            }
            if (specifiedLimit < 0) throw new IllegalArgumentException("Error " + HttpStatus.NOT_ACCEPTABLE + " : Please provide a positive Limit");
        }

        if (offsetInParams && limitInParams) {

            int offsetIndex = specifiedOffset - 1;

            return gettingJukesAsString(supportingJukes.subList(offsetIndex,offsetIndex+specifiedLimit));



        }

        if (offsetInParams) return gettingJukesAsString(supportingJukes.subList(specifiedOffset - 1, supportingJukes.size()));

        if (limitInParams) return gettingJukesAsString(supportingJukes.subList(0,specifiedLimit));

        return gettingJukesAsString(supportingJukes);

    }

    private String gettingJukesAsString(List<String> supportedJukes) {
        StringBuilder output = new StringBuilder();
        int count = 1;
        for (String jukeID : supportedJukes) {
            output.append(count).append(".").append(" ").append(jukeID).append("<br>").append("<br>");
            count++;
        }

        return output.toString();
    }
}
