package fun;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

/*In this example, the test method sends a GET request to http://localhost:8080/game with a query parameter name=Chess. It then validates that the response has a status code of 200, the content type is application/json, and
 *  the response body contains an id field with a value greater than 0 and a non-null text field.*/
public class GameTestsFunctional {
	@Test
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
	
	/*testGameServiceWithNoName: This test case sends a GET request to the /game endpoint without specifying the name parameter. 
	 * It verifies that the response has a status code of 200, the content type is application/json, and the response body contains an id field with a value greater than 0 and a non-null text field. 
	This tests the default behavior of the endpoint when no name is provided.*/
	    @Test
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
	    
	    
	    /* testGameServiceWithInvalidName: This test case sends a GET request to the /game endpoint with an invalid name parameter value. 
	     * It verifies that the response has a status code of 404, indicating that the requested game is not found.*/
	    @Test
	    public void testGameServiceWithInvalidName() {
	        given()
	            .param("name", "InvalidGame")
	        .when()
	            .get("http://localhost:8080/game")
	        .then()
	            .statusCode(404);
	    }


}
