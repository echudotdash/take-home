package fun;

import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.SC_NOT_FOUND;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.Matchers.any;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.io.InputStream;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import io.restassured.RestAssured;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;

public class GameTests {
	
	@BeforeAll
	public static void beforeAll() {
		RestAssured.baseURI = "http://localhost:8080";
		RestAssured.basePath = "/game";
	}
	
	@Test
	public void givenRequestPassingGameNameThenReturnShouldBeOk() throws Exception {
		final String gameName = "Counter-Strike";
		
		final Response response = given()
				.param("name", gameName)
				.get();

		assertResponse(response, SC_OK);
		assertGameResponse(gameName, response);
	}

	@Test
	public void givenRequestNoPassingGameNameThenReturnShouldBeOkWithDefaultGame() throws Exception {
		final String gameName = "Sudoku";
		
		final Response response = given()
				.get();
		
		assertResponse(response, SC_OK);
		assertGameResponse(gameName, response);
	}
	
	@Test
	public void givenRequestThenReturnSchemaShouldBeEqualFile() throws Exception {
		InputStream createBookingJsonSchema = getClass().getClassLoader()
			    .getResourceAsStream("gameResponse-Schema.json");
        given()
        	.param("nome", "")
		    .get()
		    .then()
		    .statusCode(SC_OK)
		    .and()
		    .assertThat()
		    .body(JsonSchemaValidator.matchesJsonSchema(createBookingJsonSchema));
	}
	
	@Test
	public void givenRequestPassingWrongParamThenReturnShouldBeOkWithDefaultGame() throws Exception {
		final String gameName = "Sudoku";
		
		final Response response = given()
				.param("id", 123)
				.get();
		
		assertResponse(response, SC_OK);
		assertGameResponse(gameName, response);
	}
	
	@Test
	public void givenRequestWithWrongPATHThenReturnShouldBeNOk() throws Exception {
		RestAssured.basePath = "/newGame";
		
		final Response response = given()
				.get();
		assertResponse(response, SC_NOT_FOUND);
		
		RestAssured.basePath = "/game";
	}
	
	private void assertResponse(final Response response, int statusResponse) {
		assertNotNull(response);
		assertThat(response.getStatusCode(), equalTo(statusResponse));
	}

	private void assertGameResponse(final String gameName, final Response response) {
		final GameResponse gameResponse = response.getBody().as(GameResponse.class);
		assertNotNull(gameResponse);
		assertThat(gameResponse.getId(), any(Integer.class));
		
		assertNotNull(gameResponse.getText());
		final String formattedText = String.format("Playing %s is fun!", gameName);
        assertThat(formattedText, equalTo(gameResponse.getText()));
	}
}
