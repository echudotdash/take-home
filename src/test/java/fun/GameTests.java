package fun;

import fun.uitl.ConfigurationReader;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.File;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.not;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class GameTests {

    @Test
    @DisplayName("GET request")
    public void test1(){
        Response response = given().accept(ContentType.JSON)
                .and().get(ConfigurationReader.getProperty("base_url"));
        assertEquals(200, response.statusCode());
        assertEquals("application/json;charset=UTF-8",response.contentType());
        assertEquals("Playing Sudoku is fun!", response.path("text"));
    }

    @Test
    @DisplayName("GET Chess")
    public void test2(){
        Response response = given().accept(ContentType.JSON)
                .queryParam("name", "Chess")
                .get(ConfigurationReader.getProperty("base_url"));
        assertEquals(200, response.statusCode());
        assertEquals("application/json;charset=UTF-8",response.contentType());
        String responseText = response.path("text");
        assertEquals("Playing Chess is fun!", responseText);
    }

    @ParameterizedTest
    @DisplayName("GET all games test")
    @Test
    @ValueSource(strings = {"Chess", "Catan", "Uno", "Poker"} )
    public void testNames(String name) {
        given().accept(ContentType.JSON)
                .and().queryParam("name", name)
                .when().get(ConfigurationReader.getProperty("base_url"))
                .then().assertThat().statusCode(200)
                .log().all(); //TODO assert that body contains name
    }

    @ParameterizedTest
    @DisplayName("GET  json schema validation")
    @Test
    @ValueSource(strings = {"Chess", "Catan", "Uno", "Poker"} )
    public void singleSpartanSchemaValidationTest(String name) {
        given().accept(ContentType.JSON)
                .and().queryParam("name", name)
                .when().get(ConfigurationReader.getProperty("base_url"))
                .then().statusCode(200)
                .and().body(JsonSchemaValidator.matchesJsonSchema(
                        new File("src/schema/schema.json")))
                .and().log().all();
    }
}
