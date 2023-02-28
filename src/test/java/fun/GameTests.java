package fun;



import data.GamePojoResponse;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.io.File;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GameTests {

    int[] fibonacci = new int[3];
    GamePojoResponse gamePojo;
    String [] games = {"Chess", "Cricket", "Backgammon"};

    @DisplayName("GET json schema validation")
    @Test
    public void testValidateJsonSchema() {
        given().contentType("application/json")
                .accept("application/json")
                .queryParam("name")
                .when().get("/game").prettyPeek()
                .then()
                .statusCode(200)
                .body(JsonSchemaValidator.matchesJsonSchema
                        (new File("src/resources/schema.json")));
    }

    @DisplayName("Test with query param: Validate counter and text /game/?name=gameName")
    @Test
    public void testVerifyCounterInGameServiceResponse() {
        int i = 0;
        while (i < 3) {
            String expectedText = "Playing " + games[i] + " is fun!";
            JsonPath jsonPath = given()
                    .contentType("application/json")
                    .accept("application/json")
                    .queryParams("name", games[i])
                    .when().get("/game").prettyPeek()
                    .then()
                    .statusCode(200)
                    .extract().jsonPath();
            gamePojo = jsonPath.getObject("$", GamePojoResponse.class);
            assertEquals(expectedText, gamePojo.getText());
            fibonacci[i] = gamePojo.getId();
            i++;
        }
        assertTrue(fibonacci[2] - fibonacci[1] == fibonacci[0]);

    }

    @DisplayName("Test without query parameter")
    @Test
    public void testWithoutQueryParam() {
        String defaultParam = "Sudoku";
        given().accept(ContentType.JSON)
                .when()
                .get("/game")
                .then()
                .statusCode(200)
                .body("id", greaterThan(0))
                .body("text", containsString(defaultParam));

    }

    @DisplayName("Convert JSON to POJO")
    @Test
    public void testPojoConversion() {
        String param = "Chess";
        Response response = given().param("name", param).get("/game");

        assertEquals(200,response.getStatusCode());

        GamePojoResponse serviceResponse = response.as(GamePojoResponse.class);
        assertTrue(serviceResponse.getId()>0);
        assertTrue(serviceResponse.getText().contains(param));


    }

}
