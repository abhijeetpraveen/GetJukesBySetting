package abhijeetpraveen.GetJukesBySetting;

import abhijeetpraveen.GetJukesBySetting.controller.GetJukesBySettingRestController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class GetJukesBySettingApplicationTests {

	@Autowired
	private GetJukesBySettingRestController restController;

	@Autowired
	private MockMvc mockMvc;

	@Test
	void contextLoads() {
		assertThat(restController).isNotNull();
	}


	@Test
	public void inputSettingIsAcceptable() throws Exception {
		String settingID = "86506865-f971-496e-9b90-75994f251459";
		this.mockMvc.perform(get("/supportedJukes?id=" + settingID)).andExpect(status().isOk());
	}

	@Test
	public void inputSettingIDIsEmpty() throws Exception {
		String emptySettingID = "";
		this.mockMvc.perform(get("/supportedJukes?id=" + emptySettingID)).andExpect(content().string(containsString("Error " + HttpStatus.NOT_ACCEPTABLE + " : No Setting ID was given. Please enter a valid id")));
	}

	@Test
	public void inputSettingNotMatchingPatternByBeingTooLong() throws Exception {
		String tooLongSettingID = "86506865-f971-496e-9b90-75994f251459tl";
		this.mockMvc.perform(get("/supportedJukes?id=" + tooLongSettingID)).andExpect(content().string(containsString("Error " + HttpStatus.NOT_ACCEPTABLE + " : " + tooLongSettingID + " is an invalid ID")));

	}

	@Test
	public void inputSettingNotMatchingPatternByBeingTooShort() throws Exception {
		String tooShortSettingID = "86506865-f971-496e-9b90-75994f25ts";
		this.mockMvc.perform(get("/supportedJukes?id=" + tooShortSettingID)).andExpect(content().string(containsString("Error " + HttpStatus.NOT_ACCEPTABLE + " : " + tooShortSettingID + " is an invalid ID")));
	}

	@Test
	public void inputSettingIDHasNoMatchingSetting() throws Exception {
		String noMatchingSettingID = "86506865-f971-496e-9b90-75994f25145a";
		this.mockMvc.perform(get("/supportedJukes?id=" + noMatchingSettingID)).andExpect(content().string(containsString("Error " + HttpStatus.NOT_FOUND + " : The ID (" + noMatchingSettingID + ")" + " has no matching setting")));
	}

	@Test
	public void inputSettingIDHasNoNonAlphaNumericCharacter() throws Exception {
		String settingIDWithNonAlphaNumericCharacter = "86506865-f971-496e-9b90-75994f25145-";
		this.mockMvc.perform(get("/supportedJukes?id=" + settingIDWithNonAlphaNumericCharacter)).andExpect(content().string(containsString("Error " + HttpStatus.NOT_ACCEPTABLE + " : " + settingIDWithNonAlphaNumericCharacter + " is an invalid ID")));
	}

	@Test
	public void inputModelIsValid() throws Exception {
		String validSettingID = "86506865-f971-496e-9b90-75994f251459";
		String validModelName = "angelina";
		this.mockMvc.perform(get("/supportedJukes?id=" + validSettingID + "&model=" + validModelName)).andExpect(status().isOk());
	}

	@Test
	public void inputModelIsNotValid() throws Exception {
		String validSettingID = "86506865-f971-496e-9b90-75994f251459";
		String invalidModelName = "Liverpool FC are the best in the world";
		this.mockMvc.perform(get("/supportedJukes?id=" + validSettingID + "&model=" + invalidModelName)).andExpect(content().string(containsString("Error " + HttpStatus.NOT_FOUND + " : " + invalidModelName + " is not a valid model")));
	}

	@Test
	public void inputSettingIDIsValidButEmptyListIsReturned() throws Exception {
		String validSettingIDThatReturnsEmptyList = "e9869bbe-887f-4d0a-bb9d-b81eb55fbf0a";
		this.mockMvc.perform(get("/supportedJukes?id=" + validSettingIDThatReturnsEmptyList)).andExpect(content().string(containsString("No Jukes support the given parameter(s)")));
	}

	@Test
	public void inputSettingIsValidAndReturnsNonEmptyList() throws Exception {
		String validSettingIDThatReturnsNonEmptyList = "86506865-f971-496e-9b90-75994f251459";
		this.mockMvc.perform(get("/supportedJukes?id=" + validSettingIDThatReturnsNonEmptyList)).andExpect(content().string(containsString("1. ID: 5ca94a8ac470d3e47cd4713c<br>Model: fusion<br>Components: [led_panel, amplifier, pcb]<br><br>2. ID: 5ca94a8a77e20d15a7d16d0a<br>Model: angelina<br>Components: [money_pcb, speaker, touchscreen, pcb]<br><br>3. ID: 5ca94a8a75c231bb18715112<br>Model: fusion<br>Components: [led_panel, amplifier]<br><br>4. ID: 5ca94a8a20905ffff6f0561c<br>Model: virtuo<br>Components: [led_panel, money_receiver]<br><br>5. ID: 5ca94a8a3227b0a360f41078<br>Model: fusion<br>Components: []<br><br>6. ID: 5ca94a8ac3f21c47a72473ec<br>Model: virtuo<br>Components: [led_panel, amplifier, pcb]<br><br>7. ID: 5ca94a8ad82e60f2448d2fc9<br>Model: angelina<br>Components: [speaker, touchscreen]<br><br>8. ID: 5ca94a8ab592da6c6f2d562e<br>Model: fusion<br>Components: [amplifier, camera]<br><br>9. ID: 5ca94a8ac5f85b8a59f9e3c8<br>Model: virtuo<br>Components: [money_pcb, camera]<br><br>10. ID: 5ca94a8a13385f0c82aa9f2e<br>Model: virtuo<br>Components: [touchscreen, money_storage, pcb]<br><br>11. ID: 5ca94a8aafb9d8c4e4fddf02<br>Model: angelina<br>Components: []<br><br>12. ID: 5ca94a8a1639eb9ea30609f0<br>Model: virtuo<br>Components: []<br><br>13. ID: 5ca94a8a4aeb7ab33a5e1047<br>Model: virtuo<br>Components: [money_pcb, camera]<br><br>14. ID: 5ca94a8ad2d584257d25ae50<br>Model: fusion<br>Components: [money_pcb, led_panel, amplifier, money_receiver]<br><br>15. ID: 5ca94a8a8b58770bb38055a0<br>Model: angelina<br>Components: [money_storage, pcb]<br><br>16. ID: 5ca94a8afa2bc9887b28ce87<br>Model: angelina<br>Components: [amplifier]<br><br>17. ID: 5ca94a8adb81479f94dda744<br>Model: fusion<br>Components: [led_matrix]<br><br>18. ID: 5ca94a8a0735998f945f7276<br>Model: fusion<br>Components: [touchscreen, camera]<br><br>19. ID: 5ca94a8a59b8061f89644f43<br>Model: fusion<br>Components: [speaker, amplifier, touchscreen]<br><br>20. ID: 5ca94a8acc046e7aa8040605<br>Model: angelina<br>Components: [money_pcb, speaker, touchscreen, money_receiver, led_matrix]<br><br>21. ID: 5ca94a8af0853f96c44fa858<br>Model: virtuo<br>Components: [touchscreen, led_matrix, money_storage, money_receiver, pcb]<br><br>22. ID: 5ca94a8a18f5576210fd012e<br>Model: virtuo<br>Components: []<br><br>23. ID: 5ca94a8ae2b3a4fb2f0cfd78<br>Model: fusion<br>Components: [money_pcb, led_matrix, money_receiver]<br><br>24. ID: 5ca94a8acfdeb5e01e5bdbe8<br>Model: virtuo<br>Components: [money_pcb, money_storage, money_receiver, camera]<br><br>25. ID: 5ca94a8a2c516506b1f49500<br>Model: angelina<br>Components: [money_pcb, led_matrix]<br><br>26. ID: 5ca94a8af9985926172d6e8d<br>Model: angelina<br>Components: [money_pcb, speaker, led_matrix, pcb]<br><br>27. ID: 5ca94a8a1d1bc6d59afb9392<br>Model: virtuo<br>Components: [speaker, money_storage]<br><br>28. ID: 5ca94a8aa2330a0762019ac0<br>Model: angelina<br>Components: [amplifier, money_storage]<br><br>29. ID: 5ca94a8ab2c1285e53a89991<br>Model: fusion<br>Components: []<br><br>30. ID: 5ca94a8a72473ac501b99033<br>Model: angelina<br>Components: []")));
	}

	@Test
	public void inputSettingIsValidAndReturnsNonEmptyList2() throws Exception {
		String validSettingIDThatReturnsNonEmptyList = "3a6423cf-f226-4cb1-bf51-2954bc0941d1";
		this.mockMvc.perform(get("/supportedJukes?id=" + validSettingIDThatReturnsNonEmptyList)).andExpect(content().string(containsString("1. ID: 5ca94a8acc046e7aa8040605<br>Model: angelina<br>Components: [money_pcb, speaker, touchscreen, money_receiver, led_matrix]")));
	}

	@Test
	public void inputSettingIDAndModelAreValidButReturnEmptyList() throws Exception {
		String validSettingID = "3a6423cf-f226-4cb1-bf51-2954bc0941d1";
		String validModelThatWillReturnEmptyList = "fusion";
		this.mockMvc.perform(get("/supportedJukes?id=" + validSettingID + "&model=" + validModelThatWillReturnEmptyList)).andExpect(content().string(containsString("No Jukes support the given parameter(s)")));
	}

	@Test
	public void inputOffsetIsNotAnInteger() throws Exception {
		String invalidOffset = "Liverpool FC are the best in the world";
		String validSettingID = "3a6423cf-f226-4cb1-bf51-2954bc0941d1";
		this.mockMvc.perform(get("/supportedJukes?id=" + validSettingID + "&offset=" + invalidOffset)).andExpect(content().string(containsString("Error " + HttpStatus.NOT_ACCEPTABLE + " : Offset must be an integer")));
	}

	@Test
	public void inputOffsetIsZero() throws Exception {
		String zeroOffset = "0";
		String validSettingID = "3a6423cf-f226-4cb1-bf51-2954bc0941d1";
		this.mockMvc.perform(get("/supportedJukes?id=" + validSettingID + "&offset=" + zeroOffset)).andExpect(content().string(containsString("Error " + HttpStatus.NOT_ACCEPTABLE + " : Please provide a positive Offset")));
	}

	@Test
	public void inputOffsetIsNegative() throws Exception {
		String negativeOffset = "-1";
		String validSettingID = "3a6423cf-f226-4cb1-bf51-2954bc0941d1";
		this.mockMvc.perform(get("/supportedJukes?id=" + validSettingID + "&offset=" + negativeOffset)).andExpect(content().string(containsString("Error " + HttpStatus.NOT_ACCEPTABLE + " : Please provide a positive Offset")));
	}

	@Test
	public void requestingOffsetWhenListIsEmpty() throws Exception {
		String offset = "32";
		String validSettingIDThatReturnsEmptyList = "e9869bbe-887f-4d0a-bb9d-b81eb55fbf0a";
		this.mockMvc.perform(get("/supportedJukes?id=" + validSettingIDThatReturnsEmptyList + "&offset=" + offset)).andExpect(content().string(containsString("No Jukes support the given parameter(s)")));
	}

	@Test
	public void inputOffsetIsBiggerThanOutputListSize() throws Exception {
		String tooBigOffset = "900";
		String validSettingID = "86506865-f971-496e-9b90-75994f251459";
		this.mockMvc.perform(get("/supportedJukes?id=" + validSettingID + "&offset=" + tooBigOffset)).andExpect(content().string(containsString("Error " + HttpStatus.NOT_ACCEPTABLE + " : There are only " + "30" + " jukes that support the given parameter(s). Please specify an offset lower than this number.")));
	}

	@Test
	public void inputOffsetReturnsExpectedList() throws Exception {
		String validOffset = "28";
		String validSettingID = "86506865-f971-496e-9b90-75994f251459";
		this.mockMvc.perform(get("/supportedJukes?id=" + validSettingID + "&offset=" + validOffset)).andExpect(content().string(containsString("1. ID: 5ca94a8aa2330a0762019ac0<br>Model: angelina<br>Components: [amplifier, money_storage]<br><br>2. ID: 5ca94a8ab2c1285e53a89991<br>Model: fusion<br>Components: []<br><br>3. ID: 5ca94a8a72473ac501b99033<br>Model: angelina<br>Components: []")));
	}

	@Test
	public void inputLimitIsNotAnInteger() throws Exception {
		String invalidLimit = "Liverpool FC are the best in the world";
		String validSettingID = "3a6423cf-f226-4cb1-bf51-2954bc0941d1";
		this.mockMvc.perform(get("/supportedJukes?id=" + validSettingID + "&limit=" + invalidLimit)).andExpect(content().string(containsString("Error " + HttpStatus.NOT_ACCEPTABLE + " : Limit must be an integer")));
	}

	@Test
	public void inputLimitIsZero() throws Exception {
		String zeroLimit = "0";
		String validSettingID = "3a6423cf-f226-4cb1-bf51-2954bc0941d1";
		this.mockMvc.perform(get("/supportedJukes?id=" + validSettingID + "&limit=" + zeroLimit)).andExpect(content().string(containsString("Error " + HttpStatus.NOT_ACCEPTABLE + " : Please provide a positive Limit")));
	}

	@Test
	public void inputLimitIsNegative() throws Exception {
		String negativeLimit = "-1";
		String validSettingID = "3a6423cf-f226-4cb1-bf51-2954bc0941d1";
		this.mockMvc.perform(get("/supportedJukes?id=" + validSettingID + "&limit=" + negativeLimit)).andExpect(content().string(containsString("Error " + HttpStatus.NOT_ACCEPTABLE + " : Please provide a positive Limit")));
	}

	@Test
	public void inputLimitReturnsExpectedList() throws Exception {
		String validLimit = "1";
		String validSettingID = "86506865-f971-496e-9b90-75994f251459";
		this.mockMvc.perform(get("/supportedJukes?id=" + validSettingID + "&limit=" + validLimit)).andExpect(content().string(containsString("1. ID: 5ca94a8ac470d3e47cd4713c<br>Model: fusion<br>Components: [led_panel, amplifier, pcb]")));
	}

	@Test
	public void inputLimitIsBiggerThanListSizeButStillOk() throws Exception {
		String tooLargeLimit = "35";
		String validSettingID = "3a6423cf-f226-4cb1-bf51-2954bc0941d1";
		this.mockMvc.perform(get("/supportedJukes?id=" + validSettingID + "&limit=" + tooLargeLimit)).andExpect(content().string(containsString("1. ID: 5ca94a8acc046e7aa8040605<br>Model: angelina<br>Components: [money_pcb, speaker, touchscreen, money_receiver, led_matrix]")));
	}

	@Test
	public void inputOffsetAndLimitReturnExpectedList() throws Exception {
		String validOffset = "15";
		String validLimit = "5";
		String validSettingID = "86506865-f971-496e-9b90-75994f251459";
		this.mockMvc.perform(get("/supportedJukes?id=" + validSettingID + "&offset=" + validOffset + "&limit=" + validLimit)).andExpect(content().string(containsString("1. ID: 5ca94a8a8b58770bb38055a0<br>Model: angelina<br>Components: [money_storage, pcb]<br><br>2. ID: 5ca94a8afa2bc9887b28ce87<br>Model: angelina<br>Components: [amplifier]<br><br>3. ID: 5ca94a8adb81479f94dda744<br>Model: fusion<br>Components: [led_matrix]<br><br>4. ID: 5ca94a8a0735998f945f7276<br>Model: fusion<br>Components: [touchscreen, camera]<br><br>5. ID: 5ca94a8a59b8061f89644f43<br>Model: fusion<br>Components: [speaker, amplifier, touchscreen]")));
	}

	@Test
	public void inputModelOffsetAndLimitReturnExpectedList() throws Exception {
		String validModel = "fusion";
		String validOffset = "5";
		String validLimit = "4";
		String validSettingID = "86506865-f971-496e-9b90-75994f251459";
		this.mockMvc.perform(get("/supportedJukes?id=" + validSettingID + "&model=" + validModel + "&offset=" + validOffset + "&limit=" + validLimit)).andExpect(content().string(containsString("1. ID: 5ca94a8ad2d584257d25ae50<br>Model: fusion<br>Components: [money_pcb, led_panel, amplifier, money_receiver]<br><br>2. ID: 5ca94a8adb81479f94dda744<br>Model: fusion<br>Components: [led_matrix]<br><br>3. ID: 5ca94a8a0735998f945f7276<br>Model: fusion<br>Components: [touchscreen, camera]<br><br>4. ID: 5ca94a8a59b8061f89644f43<br>Model: fusion<br>Components: [speaker, amplifier, touchscreen]")));

	}
}
