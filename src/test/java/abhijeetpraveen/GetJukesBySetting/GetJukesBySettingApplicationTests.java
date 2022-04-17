package abhijeetpraveen.GetJukesBySetting;

import abhijeetpraveen.GetJukesBySetting.controller.GetJukesBySettingRestController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class GetJukesBySettingApplicationTests {

	@Autowired
	private GetJukesBySettingRestController restController;

	@Test
	void contextLoads() {
		assertThat(restController).isNotNull();
	}

}
