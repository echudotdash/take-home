package fun;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.jsonschema.exceptions.ProcessingException;
import com.github.fge.jsonschema.main.JsonSchema;
import com.github.fge.jsonschema.main.JsonSchemaFactory;
import com.github.fge.jsonschema.report.ProcessingReport;
import com.github.fge.jsonschema.util.JsonLoader;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import pojo.GameResponsePojo;

import java.io.File;
import java.io.IOException;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class GameTests {

    @Test
    public void testGameEndpointWithDifferentInput() throws IOException, ProcessingException {
        String[] gameName = {"Sudoku", "Chess"};

        for (String gameType : gameName) {
            Response response = RestAssured.get("http://localhost:8080/game?name=" + gameType);

            GameResponsePojo gameResponse = response.as(GameResponsePojo.class);

            response.then()
                    .log().all()
                    .assertThat()
                    .statusCode(200)
                    .body("id", greaterThan(0))
                    .body("text", equalTo("Playing " + gameType + " is fun!"));

            assert(gameResponse.getId() > 0);
            assert(gameResponse.getText().equals("Playing " + gameType + " is fun!"));


            JsonSchemaFactory schemaFactory = JsonSchemaFactory.byDefault();
            JsonNode schemaNode = JsonLoader.fromFile(new File("src/gameResponseSchema.json"));
            JsonSchema schema = schemaFactory.getJsonSchema(schemaNode);

            JsonNode responseJson = JsonLoader.fromString(response.getBody().asString());
            ProcessingReport report = schema.validate(responseJson);
            assertTrue(report.isSuccess(), "Response does not match schema:\n" + report);

        }

    }

}

