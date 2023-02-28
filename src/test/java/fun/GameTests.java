package fun;

import pojo.GameResponse;
import java.io.InputStream;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import io.restassured.RestAssured;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.SC_NOT_FOUND;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.Matchers.any;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;


public class GameTests {

    final String defaultGameName = "Sudoku";
	
	@BeforeAll
	public static void beforeAll() {
		RestAssured.baseURI = "http://localhost:8080";
		RestAssured.basePath = "/game";
	}
	
	@Test
	public void testGameGreetingWithDifferentGameValues() throws Exception {
        String[] gamesList = {"Tic Tac Toe", "Poker", "Carrom"};

        for (String game : gamesList) {
		
		final Response response = given()
				.param("name", game)
				.get();

		assertResponse(response, SC_OK);
		assertGameResponse(game, response);
        }
	}

	@Test
	public void testGameGreetingWithDefaultValue() throws Exception {
		
		final Response response = given()
				.get();
		
		assertResponse(response, SC_OK);
		assertGameResponse(defaultGameName, response);
	}
	
	@Test
	public void givenRequestThenReturnSchemaShouldBeEqualFile() throws Exception {
		InputStream jsonSchema = getClass().getClassLoader()
			    .getResourceAsStream("gameResponse.json");
        given()
        	.param("nome", "")
		    .get()
		    .then()
		    .statusCode(SC_OK)
		    .and()
		    .assertThat()
		    .body(JsonSchemaValidator.matchesJsonSchema(jsonSchema));
	}
	
	@Test
	public void testInvalidUrl() throws Exception {
		RestAssured.basePath = "/newGame";
		final Response response = given()
				.get();
		assertResponse(response, SC_NOT_FOUND);
	}

    
    private void assertResponse(final Response response, int status) {
		assertNotNull(response);
		assertThat(response.getStatusCode(), equalTo(status));
	}

	private void assertGameResponse(final String gameName, final Response response) {
		final GameResponse gameRes = response.getBody().as(GameResponse.class);
		assertNotNull(gameRes);
		assertThat(gameRes.getId()>0);
		
		assertNotNull(gameRes.getText());
		final String formattedText = String.format("Playing %s is fun!", gameName);
        assertThat(formattedText, equalTo(gameRes.getText()));
	}	
}
