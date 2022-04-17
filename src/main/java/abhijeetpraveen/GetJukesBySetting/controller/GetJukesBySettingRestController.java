package abhijeetpraveen.GetJukesBySetting.controller;


import abhijeetpraveen.GetJukesBySetting.service.GetJukesBySettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class GetJukesBySettingRestController {


    @Autowired
    GetJukesBySettingService service;


    /**
     * The RESTful Service Get Endpoint that calls the method from the service class to get all supported jukes
     * @param id the setting ID for which the user wishes to find all compatible jukes
     * @param model an optional parameter where the user can filter jukes by their model
     * @param offset an optional parameter where the user can indicate at what index the page should start
     * @param limit an optional parameter where the user can indicate the page size
     * @return a String response entity corresponding to the paginated list or any error thrown by the service
     */
    @GetMapping(value = "/supportedJukes")
    public ResponseEntity<String> getJukesBySetting(@RequestParam(value = "id", defaultValue = "") String id,
                                               @RequestParam(value = "model", defaultValue = "") String model,
                                               @RequestParam(value = "offset", defaultValue = "") String offset,
                                               @RequestParam(value = "limit", defaultValue = "") String limit) {
        try {
            return ResponseEntity.ok(service.getJukesBySetting(id,model,offset,limit));
        } catch (Exception error) {
            return ResponseEntity.badRequest().body(error.getMessage());
        }
    }

}
