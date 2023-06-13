import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class GameTestsFunctional {
	
    @Test
    // Test case: Sending a GET request with a valid name parameter
    public void testGameService() {
        given()
            .param("name", "Chess")
        .when()
            .get("http://localhost:8080/game")
        .then()
            .statusCode(200)
            .contentType("application/json")
            .body("id", greaterThan(0))
            .body("text", notNullValue());
    }
	
    @Test
    // Test case: Sending a GET request without specifying the name parameter
    public void testGameServiceWithNoName() {
        given()
        .when()
            .get("http://localhost:8080/game")
        .then()
            .statusCode(200)
            .contentType("application/json")
            .body("id", greaterThan(0))
            .body("text", notNullValue());
    }
    
    @Test
    // Test case: Sending a GET request with an invalid name parameter value
    public void testGameServiceWithInvalidName() {
        given()
            .param("name", "InvalidGame")
        .when()
            .get("http://localhost:8080/game")
        .then()
            .statusCode(404);
    }
    
    @Test
    // Test case: Sending a GET request with a lowercase name parameter
    public void testGameServiceWithLowerCaseName() {
        given()
            .param("name", "chess")
        .when()
            .get("http://localhost:8080/game")
        .then()
            .statusCode(200)
            .contentType("application/json")
            .body("id", greaterThan(0))
            .body("text", notNullValue());
    }
    
    @Test
    // Test case: Sending a GET request with special characters in the name parameter
    public void testGameServiceWithSpecialCharactersInName() {
        given()
            .param("name", "!@#$%^&*()")
        .when()
            .get("http://localhost:8080/game")
        .then()
            .statusCode(200)
            .contentType("application/json")
            .body("id", greaterThan(0))
            .body("text", notNullValue());
    }
    
    @Test
    // Test case: Sending a GET request with a long name parameter that exceeds the maximum length allowed
    public void testGameServiceWithLongName() {
        given()
            .param("name", "ThisIsAVeryLongGameNameThatExceedsTheMaximumLengthAllowed")
        .when()
            .get("http://localhost:8080/game")
        .then()
            .statusCode(200)
            .contentType("application/json")
            .body("id", greaterThan(0))
            .body("text", notNullValue());
    }
}
