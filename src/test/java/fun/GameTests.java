package fun;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class GameTests {
    private static final String BASE_URI = "http://localhost:8080";
    private static final String BASE_PATH = "/game";
    private static final int STATUS_CODE = 200;

    private static final String DEFAULT_GAME = "Sudoku";
    private static final String TEXT_TEMPLATE = "Playing %s is fun!";

    @BeforeAll
    static void setup() {
        RestAssured.baseURI = BASE_URI;
        RestAssured.basePath = BASE_PATH;
    }

    @Order(1)
    @Test
    void verifyDefaultGameName() {
        Response response = sendRequest();
        validateResponse(response, DEFAULT_GAME);
    }

    @Order(2)
    @ParameterizedTest
    @ValueSource(strings = {"Minecraft", "1.3.2.4", "123", "$@##@!#%)_(", ""})
    void multipleGameNames(String name) {
        Response response = sendRequestWithParam(name);
        validateResponse(response, name.equals("") ? DEFAULT_GAME : name);
    }

    @Order(3)
    @Test
    void validateJsonSchema() {
        Response response = sendRequest();
        response.then()
                .assertThat()
                .body(matchesJsonSchemaInClasspath("response-schema.json"));
    }

    private void validateResponse(Response response, String gameName) {
        GameResponse gameResponse = response.as(GameResponse.class);
        String formattedText = String.format(TEXT_TEMPLATE, gameName);
        Assertions.assertEquals(formattedText, gameResponse.getText());
    }

    private Response sendRequest() {
        RequestSpecification request = RestAssured.given();
        Response getResponse = request.get();
        assertStatusCode(getResponse);
        return getResponse;
    }

    private Response sendRequestWithParam(String name) {
        RequestSpecification request = RestAssured.given();
        request.queryParam("name", name);

        Response getResponse = request.get();
        assertStatusCode(getResponse);
        return getResponse;
    }

    private void assertStatusCode(Response response) {
        Assertions.assertEquals(
                STATUS_CODE, response.statusCode(),
                "Expected status code was 200 but received: " + response.statusCode()
        );
    }
}
