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
		String greeting = "Welcome to the GetJukesBySetting Application by Abhijeet Praveen.";
		String message = " Please enter a query for setting id as (/supportedJukes?id=) to check for Jukes supporting that setting.";
		String optionalParams = " Other query parameters such as (model, offset and limit) can also be included but are optional.";
		return greeting + "<br>" +"<br>" + message + "<br>" + "<br>" + optionalParams;
	}

}
