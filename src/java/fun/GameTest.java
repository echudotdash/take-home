package fun;

import data.GameResponse;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.annotation.Order;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class GameTest {


    @BeforeAll
    public static void connectionSetUp() throws Exception {
        baseURI = "http://localhost";
        port = 8080;
    }

    @Test
    @DisplayName("GET /game")
    @Order(1)
    void testDefaultGameResponse() {
        JsonPath jsonPath = given()
                .contentType("application/json")
                .accept("application/json")
                .when().get("/game").prettyPeek()
                .then()
                .statusCode(200)
                .extract().jsonPath();

            GameResponse gameResponse = jsonPath.getObject("$", GameResponse.class);
            assertEquals("Playing Sudoku is fun!", gameResponse.getText());
    }

    @Order(2)
    @Test
    @DisplayName("GET /game?name=Chess")
    void testQueryParamGameResponse() {
        JsonPath jsonPath = given()
                .contentType("application/json")
                .accept("application/json")
                .queryParam("name", "Chess")
                .when().get("/game").prettyPeek()
                .then()
                .statusCode(200)
                .extract().jsonPath();

        GameResponse gameResponse = jsonPath.getObject("$", GameResponse.class);
        assertEquals("Playing Chess is fun!", gameResponse.getText());
    }

    @Order(3)
    @ParameterizedTest
    @DisplayName("GET /game?name={}")
    @ValueSource(strings = {"Baseball", "Poker", "Monopoly", "Carrom", "Snooker"} )
    void testForMultipleGameRequests(String game) {
            given()
                .contentType("application/json")
                .accept("application/json")
                .queryParam("name", game)
                .when().get("/game").prettyPeek()
                .then()
                .statusCode(200)
                    .and().assertThat().body("text", equalTo("Playing " + game + " is fun!"))
                    .log().all();
    }
    @Order(4)
    @DisplayName("GET /game?name={} fib counter test")
    @Test
    void testFibCounterForMultipleGameRequests() {
        List<String> games = Arrays.asList("Baseball", "Poker", "Monopoly", "Carrom", "Snooker");
        int[] fibCounter = new int[games.size()];
        int i = 0;
        while (i < games.size()) {
            String expectedText = "Playing " + games.get(i) + " is fun!";
            JsonPath jsonPath = given()
                    .contentType("application/json")
                    .accept("application/json")
                    .queryParams("name", games.get(i))
                    .when().get("/game").prettyPeek()
                    .then()
                    .statusCode(200)
                    .extract().jsonPath();
            GameResponse gameResponse = jsonPath.getObject("$", GameResponse.class);
            assertEquals(expectedText, gameResponse.getText());
            fibCounter[i] = gameResponse.getId();
            i++;
        }
        assertTrue(fibCounter[2] - fibCounter[1] == fibCounter[0]);
    }

    @Order(5)
    @ParameterizedTest
    @DisplayName("GET /game?name={} json schema validation")
    @ValueSource(strings = {"Baseball", "Poker", "Monopoly", "Carrom", "Snooker"} )
    public void testGameResponseSchemaValidation(String game) {
        given().contentType("application/json")
                .accept("application/json")
                .queryParams("name", game)
                .when().get("/game").prettyPeek()
                .then().statusCode(200)
                .and().body(JsonSchemaValidator.matchesJsonSchema(
                        new File("src/test/resources/schema.json")))
                .and().log().all();
    }
}
