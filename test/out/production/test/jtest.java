// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.*;

public class FunctionalTests {

    @Test
    public void testGameEndpoint() {
        given()
                .when()
                .get("http://localhost:8080/game?name={name}", "example")
                .then()
                .statusCode(200)
                .body("property1", equalTo("expectedValue1"))
                .body("property2", equalTo("expectedValue2"));
    }
}


import com.fasterxml.jackson.annotation.JsonProperty;

public class GameResponse {
    @JsonProperty("property1")
    private String property1;

    @JsonProperty("property2")
    private String property2;

    // Getters and setters
}