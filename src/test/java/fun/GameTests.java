package fun;

import com.networknt.schema.ValidationMessage;
import io.restassured.response.Response;

import static org.junit.Assert.*;


import org.junit.Test;
import api.GameAPI;
import pojo.GameDetails;
import util.JsonValidator;

import java.io.IOException;
import java.util.Set;


public class GameTests {

    @Test
    public void testDefaultGame() {
        Response response = GameAPI.getDefaultGameDetails();
        GameDetails gameDetails = response.as(GameDetails.class);

        //Assuming  Status code is 200
        assertEquals(200, response.statusCode());

        assertNotNull(gameDetails.getId());
        assertNotNull(gameDetails.getText());
    }

    @Test
    public void testSpecificGame() {
        String gameName = "Chess";
        Response response = GameAPI.getSpecificGameDetails(gameName);
        GameDetails gameDetails = response.as(GameDetails.class);

        //Assuming  Status code is 200
        assertEquals(200, response.statusCode());

        assertNotNull(gameDetails.getId());
        assertNotNull(gameDetails.getText());
    }

    @Test
    public void validateDefaultGameResponseAgainstJsonSchema() throws IOException {
        Response response = GameAPI.getDefaultGameDetails();
        String path = "src/test/java/resources/GameDetailsSchema.json";
        Set<ValidationMessage> validationResult = JsonValidator.validateResponseAgainstJsonSchema(response, path);
        assertTrue("Json Schema Validation failed", validationResult.isEmpty());
    }

    @Test
    public void validateSpecificGameResponseAgainstJsonSchema() throws IOException {
        String gameName = "Chess";
        Response response = GameAPI.getSpecificGameDetails(gameName);
        String path = "src/test/java/resources/GameDetailsSchema.json";
        Set<ValidationMessage> validationResult = JsonValidator.validateResponseAgainstJsonSchema(response, path);
        assertTrue("Json Schema Validation failed", validationResult.isEmpty());
    }

}
