package fun;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GameControllerTest {

    @LocalServerPort
    private int port;

    private String baseUrl;

    @BeforeEach
    public void setUp() {
        baseUrl = "http://localhost:" + port;
    }

    @Test
    public void testGetGame() {
        given()
            .param("name", "Chess")
        .when()
            .get(baseUrl + "/game")
        .then()
            .statusCode(200)
            .body("text", equalTo("Playing Chess is fun!"));
    }

    @Test
    public void testGetGameWithDefaultName() {
        given()
        .when()
            .get(baseUrl + "/game")
        .then()
            .statusCode(200)
            .body("text", equalTo("Playing Sudoku is fun!"));
    }
}

!jsut run the test that was added to the file running mvn test maven command
