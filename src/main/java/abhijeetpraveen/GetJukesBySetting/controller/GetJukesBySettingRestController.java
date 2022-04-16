package abhijeetpraveen.GetJukesBySetting.controller;


import abhijeetpraveen.GetJukesBySetting.service.MockAPIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class GetJukesBySettingRestController {

    @Autowired
    MockAPIService service;


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
