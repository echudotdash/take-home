package fun;

import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;

@SpringBootTest(classes = Application.class, webEnvironment = WebEnvironment.RANDOM_PORT)
public class GameTests {
	@Test
	public void testNoGameNameInput() {
		String response = callAPI("http://localhost:8080/game?name=");
		assertTrue(response.toString().contains("Playing Sudoku is fun!"));
	}

	@Test
	public void testGameNameInput() {
		String response = callAPI("http://localhost:8080/game?name=testing");
		assertTrue(response.toString().contains("Playing testing is fun!"));

	}

	@Test
	public void validateWithJsonPath() {
		String json = callAPI("http://localhost:8080/game?name=testing");

		List<String> value = JsonPath.read(json, "$..text");
		assertTrue(value.get(0).contains("Playing"));
	}

	@Test
	public void validateJsonParsing() throws JsonParseException, JsonMappingException, IOException {
		String json = callAPI("http://localhost:8080/game?name=testing");
		ObjectMapper objectMapper = new ObjectMapper();

		GameResponse gameResponse = objectMapper.readValue(json, GameResponse.class);

		assertTrue(gameResponse.getText().contains("Playing"));
	}

	private static String callAPI(String urlString) {
		URL url;
		StringBuilder response = new StringBuilder();
		try {
			url = new URL(urlString);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

			String line = null;
			while ((line = br.readLine()) != null) {
				response.append(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return response.toString();
	}

}
