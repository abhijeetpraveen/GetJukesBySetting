package abhijeetpraveen.GetJukesBySetting;

import abhijeetpraveen.GetJukesBySetting.model.Jukebox;
import abhijeetpraveen.GetJukesBySetting.service.GetJukesBySettingService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;


@SpringBootTest
class GetJukesBySettingApplicationTests {

	@InjectMocks
	private GetJukesBySettingService service;

	private static final String EMPTY_PARAM = "";

	@Test
	void contextLoads() {
	}


	@Test
	public void inputSettingIsAcceptable() {
		String settingID = "86506865-f971-496e-9b90-75994f251459";
		try {
			service.getJukesBySetting(settingID,EMPTY_PARAM,EMPTY_PARAM,EMPTY_PARAM);
		}
		catch (Exception error) {
			fail(); // ensure no error is thrown and we have a success
		}

	}

	@Test
	public void inputSettingIDIsEmpty() {
		String errorMessage = null;
		try {
			service.getJukesBySetting(EMPTY_PARAM,EMPTY_PARAM,EMPTY_PARAM,EMPTY_PARAM);
		} catch(Exception error) {
			errorMessage = error.getMessage();
		}
		assertEquals("Error " + HttpStatus.NOT_ACCEPTABLE + " : No Setting ID was given. Please enter a valid id", errorMessage);
	}

	@Test
	public void inputSettingNotMatchingPatternByBeingTooLong() {
		String tooLongSettingID = "86506865-f971-496e-9b90-75994f251459tl";
		String errorMessage = null;
		try {
			service.getJukesBySetting(tooLongSettingID,EMPTY_PARAM,EMPTY_PARAM,EMPTY_PARAM);
		} catch(Exception error) {
			errorMessage = error.getMessage();
		}
		assertEquals("Error " + HttpStatus.NOT_ACCEPTABLE + " : " + tooLongSettingID + " is an invalid ID", errorMessage);
	}

	@Test
	public void inputSettingNotMatchingPatternByBeingTooShort() {
		String tooShortSettingID = "86506865-f971-496e-9b90-75994f25ts";
		String errorMessage = null;
		try {
			service.getJukesBySetting(tooShortSettingID,EMPTY_PARAM,EMPTY_PARAM,EMPTY_PARAM);
		} catch(Exception error) {
			errorMessage = error.getMessage();
		}
		assertEquals("Error " + HttpStatus.NOT_ACCEPTABLE + " : " + tooShortSettingID + " is an invalid ID", errorMessage);
	}

	@Test
	public void inputSettingIDHasNoMatchingSetting() {
		String noMatchingSettingID = "86506865-f971-496e-9b90-75994f25145a";
		String errorMessage = null;
		try {
			service.getJukesBySetting(noMatchingSettingID,EMPTY_PARAM,EMPTY_PARAM,EMPTY_PARAM);
		} catch(Exception error) {
			errorMessage = error.getMessage();
		}
		assertEquals("Error " + HttpStatus.NOT_FOUND + " : The ID (" + noMatchingSettingID + ")" + " has no matching setting",errorMessage);
	}

	@Test
	public void inputSettingIDHasNonAlphaNumericCharacter() {
		String settingIDWithNonAlphaNumericCharacter = "86506865-f971-496e-9b90-75994f25145-";
		String errorMessage = null;
		try {
			service.getJukesBySetting(settingIDWithNonAlphaNumericCharacter,EMPTY_PARAM,EMPTY_PARAM,EMPTY_PARAM);
		} catch(Exception error) {
			errorMessage = error.getMessage();
		}
		assertEquals("Error " + HttpStatus.NOT_ACCEPTABLE + " : " + settingIDWithNonAlphaNumericCharacter + " is an invalid ID", errorMessage);
	}

	@Test
	public void inputModelIsValid() {
		String validSettingID = "86506865-f971-496e-9b90-75994f251459";
		String validModelName = "angelina";
		try {
			service.getJukesBySetting(validSettingID,validModelName,EMPTY_PARAM,EMPTY_PARAM);
		} catch(Exception error) {
			fail(); // ensure no error is thrown and we have a success
		}
	}

	@Test
	public void inputModelIsNotValid() {
		String validSettingID = "86506865-f971-496e-9b90-75994f251459";
		String invalidModelName = "Liverpool FC are the best in the world";
		String errorMessage = null;
		try {
			service.getJukesBySetting(validSettingID, invalidModelName, EMPTY_PARAM, EMPTY_PARAM);
		} catch(Exception error) {
			errorMessage = error.getMessage();
		}
		assertEquals("Error " + HttpStatus.NOT_FOUND + " : " + invalidModelName + " is not a valid model", errorMessage);
	}

	@Test
	public void inputSettingIDIsValidButEmptyListIsReturned() {
		String validSettingIDThatReturnsEmptyList = "e9869bbe-887f-4d0a-bb9d-b81eb55fbf0a";
		List<Jukebox> expectedJukes = new ArrayList<>();
		try {
			expectedJukes = service.getJukesBySetting(validSettingIDThatReturnsEmptyList, EMPTY_PARAM, EMPTY_PARAM, EMPTY_PARAM);
		}
		catch(Exception error) {
			fail(); // ensure no error is thrown and we have a success
		}
		assertEquals(0, expectedJukes.size());
	}

	@Test
	public void inputSettingIsValidAndReturnsNonEmptyList() {
		String validSettingIDThatReturnsNonEmptyList = "86506865-f971-496e-9b90-75994f251459";
		List<Jukebox> expectedJukes = new ArrayList<>();
		try {
			expectedJukes = service.getJukesBySetting(validSettingIDThatReturnsNonEmptyList, EMPTY_PARAM, EMPTY_PARAM, EMPTY_PARAM);
		}
		catch(Exception error) {
			fail(); // ensure no error is thrown and we have a success
		}
		assertEquals(30, expectedJukes.size());
	}

	@Test
	public void inputSettingIsValidAndReturnsNonEmptyList2() {
		String validSettingIDThatReturnsNonEmptyList = "3a6423cf-f226-4cb1-bf51-2954bc0941d1";
		List<Jukebox> expectedJukes = new ArrayList<>();
		try {
			expectedJukes = service.getJukesBySetting(validSettingIDThatReturnsNonEmptyList, EMPTY_PARAM, EMPTY_PARAM, EMPTY_PARAM);
		}
		catch(Exception error) {
			fail(); // ensure no error is thrown and we have a success
		}
		assertEquals(1, expectedJukes.size());
	}

	@Test
	public void inputSettingIDAndModelAreValidButReturnEmptyList() {
		String validSettingID = "3a6423cf-f226-4cb1-bf51-2954bc0941d1";
		String validModelThatWillReturnEmptyList = "fusion";
		List<Jukebox> expectedJukes = new ArrayList<>();
		try {
			expectedJukes = service.getJukesBySetting(validSettingID, validModelThatWillReturnEmptyList, EMPTY_PARAM, EMPTY_PARAM);
		}
		catch(Exception error) {
			fail(); // ensure no error is thrown and we have a success
		}
		assertEquals(0, expectedJukes.size());
	}

	@Test
	public void inputOffsetIsNotAnInteger() {
		String invalidOffset = "Liverpool FC are the best in the world";
		String validSettingID = "3a6423cf-f226-4cb1-bf51-2954bc0941d1";
		String errorMessage = null;
		try {
			service.getJukesBySetting(validSettingID, EMPTY_PARAM, invalidOffset, EMPTY_PARAM);
		}
		catch(Exception error) {
			errorMessage = error.getMessage();
		}
		assertEquals("Error " + HttpStatus.NOT_ACCEPTABLE + " : Offset must be an integer", errorMessage);
	}

	@Test
	public void inputOffsetIsZero() {
		String zeroOffset = "0";
		String validSettingID = "3a6423cf-f226-4cb1-bf51-2954bc0941d1";
		String errorMessage = null;
		try {
			service.getJukesBySetting(validSettingID, EMPTY_PARAM, zeroOffset, EMPTY_PARAM);
		}
		catch(Exception error) {
			errorMessage = error.getMessage();
		}
		assertEquals("Error " + HttpStatus.NOT_ACCEPTABLE + " : Please provide a positive Offset", errorMessage);
	}

	@Test
	public void inputOffsetIsNegative() {
		String negativeOffset = "-1";
		String validSettingID = "3a6423cf-f226-4cb1-bf51-2954bc0941d1";
		String errorMessage = null;
		try {
			service.getJukesBySetting(validSettingID, EMPTY_PARAM, negativeOffset, EMPTY_PARAM);
		}
		catch(Exception error) {
			errorMessage = error.getMessage();
		}
		assertEquals("Error " + HttpStatus.NOT_ACCEPTABLE + " : Please provide a positive Offset", errorMessage);
	}

	@Test
	public void requestingOffsetWhenListIsEmpty() {
		String offset = "32";
		String validSettingIDThatReturnsEmptyList = "e9869bbe-887f-4d0a-bb9d-b81eb55fbf0a";
		String errorMessage = null;
		try {
			service.getJukesBySetting(validSettingIDThatReturnsEmptyList, EMPTY_PARAM, offset, EMPTY_PARAM);
		}
		catch(Exception error) {
			errorMessage = error.getMessage();
		}
		assertEquals("Error " + HttpStatus.NOT_ACCEPTABLE + " : There are only " + "0" + " jukes that support the given parameter(s). Please specify an offset lower than this number.", errorMessage);
	}

	@Test
	public void inputOffsetIsBiggerThanOutputListSize() {
		String tooBigOffset = "900";
		String validSettingID = "86506865-f971-496e-9b90-75994f251459";
		String errorMessage = null;
		try {
			service.getJukesBySetting(validSettingID, EMPTY_PARAM, tooBigOffset, EMPTY_PARAM);
		}
		catch(Exception error) {
			errorMessage = error.getMessage();
		}
		assertEquals("Error " + HttpStatus.NOT_ACCEPTABLE + " : There are only " + "30" + " jukes that support the given parameter(s). Please specify an offset lower than this number.", errorMessage);
	}

	@Test
	public void inputOffsetReturnsExpectedList() {
		String validOffset = "28";
		String validSettingID = "86506865-f971-496e-9b90-75994f251459";
		List<Jukebox> expectedJukes = new ArrayList<>();
		try {
			expectedJukes = service.getJukesBySetting(validSettingID, EMPTY_PARAM, validOffset, EMPTY_PARAM);
		} catch(Exception error) {
			fail(); // ensure no error is thrown and we have a success
		}
		assertEquals(3, expectedJukes.size());
	}

	@Test
	public void inputLimitIsNotAnInteger() {
		String invalidLimit = "Liverpool FC are the best in the world";
		String validSettingID = "3a6423cf-f226-4cb1-bf51-2954bc0941d1";
		String errorMessage = null;
		try {
			service.getJukesBySetting(validSettingID,EMPTY_PARAM,EMPTY_PARAM,invalidLimit);
		} catch(Exception error) {
			errorMessage = error.getMessage();
		}
		assertEquals("Error " + HttpStatus.NOT_ACCEPTABLE + " : Limit must be an integer", errorMessage);
	}

	@Test
	public void inputLimitIsZero() {
		String zeroLimit = "0";
		String validSettingID = "3a6423cf-f226-4cb1-bf51-2954bc0941d1";
		String errorMessage = null;
		try {
			service.getJukesBySetting(validSettingID,EMPTY_PARAM,EMPTY_PARAM,zeroLimit);
		} catch(Exception error) {
			errorMessage = error.getMessage();
		}
		assertEquals("Error " + HttpStatus.NOT_ACCEPTABLE + " : Please provide a positive Limit", errorMessage);
	}

	@Test
	public void inputLimitIsNegative() {
		String negativeLimit = "-1";
		String validSettingID = "3a6423cf-f226-4cb1-bf51-2954bc0941d1";
		String errorMessage = null;
		try {
			service.getJukesBySetting(validSettingID,EMPTY_PARAM,EMPTY_PARAM,negativeLimit);
		} catch(Exception error) {
			errorMessage = error.getMessage();
		}
		assertEquals("Error " + HttpStatus.NOT_ACCEPTABLE + " : Please provide a positive Limit", errorMessage);
	}

	@Test
	public void inputLimitReturnsExpectedList() {
		String validLimit = "1";
		String validSettingID = "86506865-f971-496e-9b90-75994f251459";
		List<Jukebox> expectedJukes = new ArrayList<>();
		try {
			expectedJukes = service.getJukesBySetting(validSettingID, EMPTY_PARAM, EMPTY_PARAM, validLimit);
		} catch(Exception error) {
			fail(); // ensure no error is thrown and we have a success
		}
		assertEquals(Integer.parseInt(validLimit), expectedJukes.size());
	}

	@Test
	public void inputLimitIsBiggerThanListSizeButStillOk() {
		String tooLargeLimit = "35";
		String validSettingID = "3a6423cf-f226-4cb1-bf51-2954bc0941d1";
		List<Jukebox> expectedJukes = new ArrayList<>();
		try {
			expectedJukes = service.getJukesBySetting(validSettingID, EMPTY_PARAM, EMPTY_PARAM, tooLargeLimit);
		} catch(Exception error) {
			fail(); // ensure no error is thrown and we have a success
		}
		assertEquals(1, expectedJukes.size());
	}

	@Test
	public void inputOffsetAndLimitReturnExpectedList() {
		String validOffset = "15";
		String validLimit = "5";
		String validSettingID = "86506865-f971-496e-9b90-75994f251459";
		List<Jukebox> expectedJukes = new ArrayList<>();
		try {
			expectedJukes = service.getJukesBySetting(validSettingID, EMPTY_PARAM, validOffset, validLimit);
		} catch(Exception error) {
			fail(); // ensure no error is thrown and we have a success
		}
		assertEquals(Integer.parseInt(validLimit), expectedJukes.size());
	}

	@Test
	public void inputModelOffsetAndLimitReturnExpectedList() {
		String validModel = "fusion";
		String validOffset = "5";
		String validLimit = "4";
		String validSettingID = "86506865-f971-496e-9b90-75994f251459";
		List<Jukebox> expectedJukes = new ArrayList<>();
		try {
			expectedJukes = service.getJukesBySetting(validSettingID, validModel, validOffset, validLimit);
		} catch(Exception error) {
			fail(); // ensure no error is thrown and we have a success
		}
		assertEquals(Integer.parseInt(validLimit), expectedJukes.size());
	}
}
