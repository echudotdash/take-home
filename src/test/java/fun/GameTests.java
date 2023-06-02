package fun;

import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Test;
import static io.restassured.RestAssured.given;



public class GameTests {
    private static final String BASE_URL = "http://localhost:8080";
    private static final String VALID_END_POINT = "/game";
    private static final String INVALID_END_POINT = "/notGame";
    // Confirm valid response by default
    @Test
    public void ValidResponseFromGameByDefault() {
        Response actualResponse =
                given()
                        .contentType(ContentType.JSON)
                        .baseUri(BASE_URL)
                        .log().all()
                        .when()
                        .get(VALID_END_POINT)
                        .then()
                        .assertThat()
                        .statusCode(200)
                        .log().all()
                        .extract().response();

        JsonPath jsonPathEvaluator = actualResponse.jsonPath();
        String id = jsonPathEvaluator.get("id").toString();
        String text = jsonPathEvaluator.get("text").toString();

        System.out.println("game id number: " + id);
        Assert.assertNotNull("id number is < 0", id);
        System.out.println("text about game: " + text);
        Assert.assertEquals("Game is not Sudoku", "Playing Sudoku is fun!", text);
    }
    // Confirm valid response match param
@Test
        public void ValidResponseFromGame(){
    String gameNameForChess = "=Chess";
    Response actualResponse =
            given()
                    .queryParam("name","Chess")
                    .contentType(ContentType.JSON)
                    .baseUri(BASE_URL)
                    .log().all()
                    .when()
                    .get(VALID_END_POINT)
                    .then()
                    .assertThat()
                    .statusCode(200)
                    .log().all()
                    .extract().response();

    JsonPath jsonPathEvaluator = actualResponse.jsonPath();
    String id = jsonPathEvaluator.get("id").toString();
    String text = jsonPathEvaluator.get("text").toString();

    System.out.println("game id number: "+id);
    Assert.assertNotNull("id number is < 0",id);
    System.out.println("text about game: "+text);
Assert.assertEquals("Text doesn't match","Playing Chess is fun!",text);
}
// Confirm code 404 when invalid End point
    @Test
    public void InvalidEndPointResponseFromGame() {
        Response actualResponse =
                given()
                        .contentType(ContentType.JSON)
                        .baseUri(BASE_URL)
                        .log().all()
                        .when()
                        .get(INVALID_END_POINT)
                        .then()
                        .assertThat()
                        .statusCode(404)
                        .log().all()
                        .extract().response();

    }
//Schema check for the response of http://localhost:8080/game?name=
@Test
    public void getResponseSchema(){
    String gameName = "Checkers";
        given()
                .queryParam("name",gameName)
                .contentType(ContentType.JSON)
                .baseUri(BASE_URL)
                .log().all()
                .when()
                .get(VALID_END_POINT)
                .then()
                .statusCode(200)
                .log().all()
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("response.json"));
    }


}
