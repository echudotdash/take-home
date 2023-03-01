package fun;

import io.restassured.RestAssured;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import pojo.GameResponse;

import java.io.InputStream;

import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.SC_NOT_FOUND;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.Matchers.equalTo;
import static utils.Constants.BASE_PATH;
import static utils.Constants.*;

public class GameTests {


    @BeforeAll
    public static void beforeAll() {
        RestAssured.baseURI = BASE_URI;
        RestAssured.basePath = BASE_PATH;
    }

    @Test
    public void validateDifferentGames() {

        for (String game : GAMES_LIST) {

            final Response response = given()
                    .param("name", game)
                    .get();

            validateResponse(response, SC_OK);
            validateGameResponse(game, response);
        }
    }

    @Test
    public void validateDefaultGameValue() {

        final Response response = given()
                .get();

        validateResponse(response, SC_OK);
        validateGameResponse(DEFAULT_GAME, response);
    }

    @Test
    public void validateSchema() {
        InputStream jsonSchema = getClass().getClassLoader()
                .getResourceAsStream("src/test/utils/gameResponseSchema.json");
        given()
                .param("nome", "")
                .get()
                .then()
                .statusCode(SC_OK)
                .and()
                .assertThat()
                .body(JsonSchemaValidator.matchesJsonSchema(jsonSchema));
    }

    @Test
    public void validateInvalidUrl() {
        RestAssured.basePath = "/gam";
        final Response response = given()
                .get();
        validateResponse(response, SC_NOT_FOUND);
    }


    private void validateResponse(Response response, int status) {
        Assertions.assertNotNull(response);
        MatcherAssert.assertThat(response.getStatusCode(), equalTo(status));
    }

    private void validateGameResponse(String gameName, Response response) {
        GameResponse gameRes = response.getBody().as(GameResponse.class);
        Assertions.assertNotNull(gameRes);
        assert(gameRes.getId()>0);

        Assertions.assertNotNull(gameRes.getText());
        String formattedText = String.format("Playing %s is fun!", gameName);

        MatcherAssert.assertThat(formattedText, equalTo(gameRes.getText()));
    }
}