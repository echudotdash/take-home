package fun;



import org.junit.Assert;

import java.io.File;

import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import model.Game;
import utils.Constants;
import utils.Data;

public class GameTests {

	/**
	 * Validate Response with json schema
	 */
	@Test
	public void validateJsonSchemaTest() {

		// obtain response
		RestAssured.given().accept(ContentType.JSON).when().get(Constants.ENDPOINT_GAME)
				// verify JSON Schema
				.then().assertThat().body(JsonSchemaValidator.matchesJsonSchema(new File(Constants.PATH_SCHEMA)));
	}

	/**
	 * Validate Response without game query
	 */
	@Test
	public void validateGameAPIWithoutQueryTest() {
		// obtain response
		RestAssured.given().accept(ContentType.JSON).when().get(Constants.ENDPOINT_GAME)
				// verify JSON Response
				.then().statusCode(200).body("id", Matchers.greaterThan(0)).body("text", Matchers.is(Matchers.not(Matchers.empty())));

	}

	/**
	 * Validate Response with game query
	 */
	@ParameterizedTest
	@ValueSource(strings = { Data.GAME_ONE, Data.GAME_TWO})
	public void validateGameAPIWithQueryTest(String gameData) {

		// obtain response
		Response response = RestAssured.given().param("name", gameData).accept(ContentType.JSON)
				.get(Constants.ENDPOINT_GAME);
		
		// validate response is not null
		Assert.assertNotNull(response);
		// verify status code
		Assert.assertThat(response.getStatusCode(), Matchers.equalTo(HttpStatus.SC_OK));
		
		// parsing response to model and validate
		Game game = response.getBody().as(Game.class);
		
		Assert.assertNotNull(game);
		Assert.assertThat(game.getId(), Matchers.greaterThan(0));
		Assert.assertThat(game.getText(), Matchers.equalTo(Data.formattedText(gameData)));
	}
}
