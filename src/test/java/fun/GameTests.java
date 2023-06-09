package fun;

import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import org.junit.jupiter.api.Test;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.io.IOException;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GameTests {

    @Test
    public void testGameWithParam() {
        Response response = given().param("name", "Chess").
                when().get("http://localhost:8080/game").
                then().assertThat().statusCode(200).
                contentType(ContentType.JSON).extract().response();

        String jsonResponse = response.asString();

        JsonValidator validator = new JsonValidator();
        boolean isValid = false;

        try {
            isValid = validator.isValid(jsonResponse);
        } catch (IOException | ProcessingException e) {
            e.printStackTrace();
        }

        assertTrue(isValid);

        Game game = response.as(Game.class);
        assertTrue(game.getText().contains("Chess"));
    }

    @Test
    public void testGameWithoutParam() {
        Response response = given().
                when().get("http://localhost:8080/game").
                then().assertThat().statusCode(200).
                contentType(ContentType.JSON).extract().response();

        String jsonResponse = response.asString();

        JsonValidator validator = new JsonValidator();
        boolean isValid = false;

        try {
            isValid = validator.isValid(jsonResponse);
        } catch (IOException | ProcessingException e) {
            e.printStackTrace();
        }

        assertTrue(isValid);

        Game game = response.as(Game.class);
        assertTrue(game.getText().contains("Sudoku"));
    }
}