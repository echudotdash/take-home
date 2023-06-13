package fun;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

import org.hamcrest.Matcher;
import org.junit.jupiter.api.Test;


/*Define a Json Schema and Do a Schema check for the response of http://localhost:8080/game?name= 
 * JSON schema validation library like json-schema-validator. */
public class GameServiceTests {

    @Test
    public void testGameService() {
        given()
            .param("name", "Chess")
        .when()
            .get("http://localhost:8080/game")
        .then()
            .statusCode(200)
            .contentType("application/json")
            .body(matchesJsonSchemaInClasspath("game-schema.json"));
    }

	private Matcher<?> matchesJsonSchemaInClasspath(String string) {
		// TODO Auto-generated method stub
		return null;
	}
}
