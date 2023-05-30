package fun;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class GameTests {

    @LocalServerPort
    private int port;

    @BeforeEach
    public void setup() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
    }

    @Test
    public void shouldReturnDefaultGame() {
        given()
            .when()
                .get("/game")
            .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("text", equalTo("Playing Sudoku is fun!"));
    }

    @Test
    public void shouldReturnCustomGame() {
        given()
            .queryParam("name", "Chess")
            .when()
                .get("/game")
            .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("text", equalTo("Playing Chess is fun!"));
    }

    // Add more test cases as needed

}