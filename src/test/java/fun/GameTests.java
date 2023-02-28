package fun;
import game.SomeGame;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
public class GameTests {

    @Test
    public void getTextWithoutQueryParam() {
        String endpoint = "http://localhost:8080/game";
        given().
                when().
                get(endpoint).
                then().
                log().
                body().
                assertThat().
                statusCode(200).
                body("text", equalTo("Playing Sudoku is fun!"));
    }

    @Test
    public void checkIdPattern() {
        String endpoint = "http://localhost:8080/game?name=Chess";
        Integer id1 = given().contentType(ContentType.JSON).log().all()
                .get(endpoint).then().extract().path("id");
        Integer id2 = given().contentType(ContentType.JSON).log().all()
                .get(endpoint).then().extract().path("id");


        Integer expectedId = id1 + id2;
        SomeGame game = new SomeGame();

        given().
                body(game).
                when().
                post(endpoint).
                then().log().body().
                assertThat().
                statusCode(200).
                body("id", samePropertyValuesAs(expectedId));
    }

    @Test
    public void getTextWithNameQueryParam() {
        String endpoint = "http://localhost:8080/game";
        given().
                queryParam("name", "Sudoku").
                when().
                get(endpoint).
                then().
                log().
                body().
                assertThat().
                statusCode(200).
                body("text", equalTo("Playing Sudoku is fun!"));
    }
}
