package abhijeetpraveen.GetJukesBySetting.service;

import abhijeetpraveen.GetJukesBySetting.model.Jukebox;
import abhijeetpraveen.GetJukesBySetting.model.Setting;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class MockAPIService {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    private static String getResponse(String uri) throws ExecutionException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder(URI.create(uri)).header("accept", "application/json").build();

        CompletableFuture<HttpResponse<String>> responseFuture = client.sendAsync(request, HttpResponse.BodyHandlers.ofString());

        String response;
        response = responseFuture.get().body();

        return response;

    }

    public static List<Jukebox> getJukes() throws JsonProcessingException, ExecutionException, InterruptedException {

        String response = getResponse("https://my-json-server.typicode.com/touchtunes/tech-assignment/jukes");
        List<Jukebox> jukesList = objectMapper.readValue(response, new TypeReference<>(){});

        for (Jukebox juke : jukesList) {
            Set<String> componentNames = new HashSet<>();
            for (HashMap<String,String> component : juke.getJukeComponents()) {
                componentNames.add(component.get("name"));
            }
            juke.setComponentNames(componentNames);
            Jukebox.allModels.add(juke.getJukeModel());
        }

        return jukesList;

    }

    public static List<Setting> getSettings() throws JsonProcessingException, ExecutionException, InterruptedException {
        String response = getResponse("https://my-json-server.typicode.com/touchtunes/tech-assignment/settings");

        HashMap<String,List<Object>> settingsBody = objectMapper.readValue(response,new TypeReference<>(){});
        List<Setting> settingList;
        settingList = objectMapper.readValue(objectMapper.writeValueAsString(settingsBody.get("settings")), new TypeReference<>(){});

        return settingList;
    }
}
