package fun;

import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pojo.GamePojo;
import utilities.TestBase;

import java.io.File;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


class GameTests extends TestBase {

    GamePojo gamePojo;
    JsonPath jsonPath;
    String expectedText = "Playing Sudoku is fun!";
    int[] fibonacci = new int[3];
    String [] games = {"Chess", "Checkers", "Backgammon"};


    @DisplayName("GET /game")
    @Test
    public void test01() {
        int i = 0;
        while (i < 3) {
            jsonPath = given().contentType("application/json")
                    .accept("application/json")
                    .when().get("/game").prettyPeek()
                    .then()
                    .statusCode(200)
                    .extract().jsonPath();
            gamePojo = jsonPath.getObject("$", GamePojo.class);
            assertEquals(expectedText, gamePojo.getText());
            fibonacci[i] = gamePojo.getId();
            i++;
        }
        assertTrue(fibonacci[2] - fibonacci[1] == fibonacci[0]);
    }


    @DisplayName("GET /game/?name=gameName")
    @Test
    public void test02() {
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
            gamePojo = jsonPath.getObject("$", GamePojo.class);
            assertEquals(expectedText, gamePojo.getText());
            fibonacci[i] = gamePojo.getId();
            i++;
        }
        assertTrue(fibonacci[2] - fibonacci[1] == fibonacci[0]);
    }


    @DisplayName("GET /game/name= JsonSchemaValidator")
    @Test
    public void test03() {

        given().contentType("application/json")
                .accept("application/json")
                .queryParam("name")
                .when().get("/game").prettyPeek()
                .then()
                .statusCode(200)
                .body(JsonSchemaValidator.matchesJsonSchema
                        (new File("src/test/resources/jsonSchema.json")));
    }
}
