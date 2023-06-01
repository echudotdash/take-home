package fun;

import static io.restassured.RestAssured.given;
import org.apache.http.HttpStatus;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class Game_Test {
	private static final String BASE_URL = "http://localhost:8080";
	private static final String VALID_END_POINT = "/game";
	private static final String INVALID_END_POINT = "/game1";

    // To check valid response is received with endpoints 
	@Test
	public void testGameEndpointReturnsValidResponse() {
		String name = "Baseball";

		Response response = given().contentType(ContentType.JSON).baseUri(BASE_URL).when()
				.get(VALID_END_POINT + "?name=" + name).then().assertThat().statusCode(HttpStatus.SC_OK).extract()
				.response();

		JsonPath jsonPathEvaluator = response.jsonPath();
		String id = jsonPathEvaluator.get("id").toString();
		String text = jsonPathEvaluator.get("text").toString();
		
        // Printing the id and text in console
		System.out.println("id received from Response: " + id);
		System.out.println("text received from Response: " + text);

		// Validate the response with assertion
		Assert.assertTrue(text.contains(name));
		Assert.assertNotNull(id);
	}

	// To check the response with valid parameter
	@Test
	public void testGameWithNameParameters() {
		given().queryParam("name", "Monopoly").when().get(VALID_END_POINT).then().statusCode(HttpStatus.SC_OK)
				.contentType(ContentType.JSON)
				.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("game_json_schema.json"));
	}

    //To check the response code 404 is received if path is invalid
	@Test
	public void testGameWithInvalidPath() {
		given().queryParam("name", "NotAGame").when().get(INVALID_END_POINT).then().statusCode(HttpStatus.SC_OK);
	}

    // To validate json schema from json file
	@Test
	public void testGameWithDefaultName() {
		given().when().get(VALID_END_POINT).then().statusCode(HttpStatus.SC_OK).contentType(ContentType.JSON)
				.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("game_json_schema.json"));
	}
}