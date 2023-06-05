package fun;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.hamcrest.Matcher;
import org.junit.Assert;
import org.junit.Test;

import javax.validation.constraints.AssertTrue;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertNotNull;

public class GameTests {


    @Test
    public void testChess() { //method to validate for chess
       Response res= given().log().all()
                .param("name", "Chess")
                .when()
                .get("http://localhost:8080/game")
                .then()
                .assertThat().statusCode(200).
                 extract().response();

        JsonPath jsonPath = new JsonPath(res.asString());
        int userId = Integer.parseInt(jsonPath.get("id"));
        assertNotNull(userId);
        String text =jsonPath.getString("text");
        assertNotNull(text);


    }

   @Test
    public void testDefault(){ //method to validate default value
        given()
                .when().get("http://localhost:8080/game")
                .then()
                .statusCode(200)
                .contentType("application/json")
                .body("id",greaterThan(0))//Validates text of the result
                .body("text",notNullValue());//Validates text of the result
   }
}

