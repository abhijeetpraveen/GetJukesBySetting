package abhijeetpraveen.GetJukesBySetting;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@SpringBootApplication
public class GetJukesBySettingApplication {

	public static void main(String[] args) {
		SpringApplication.run(GetJukesBySettingApplication.class, args);
	}

	@RequestMapping("/")
	public String greeting(){
		return "Welcome to the GetJukesBySetting Application";
	}

}
