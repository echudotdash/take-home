package fun;



import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import static io.restassured.RestAssured.*;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

/**
 * This is a simple Junit 5 test class for 3 cases: default game id and text(Sudoku), param game id and text, schema validation
 */
public class GameTests {

    @BeforeAll
    public static void setUp(){
        baseURI = "http://localhost:8080";
        basePath = "/game";
    }


    @DisplayName("Get default game (Sudoku) and id")
    @Test
    public void getDefaultGameAndId(){

        Response rp = given().
                       when()
                          .get();

        GamePojo gamePojo = rp.getBody().as(GamePojo.class);

        String defaultGameText = gamePojo.getText();
        Integer defaultGameId = gamePojo.getId();

        System.out.println("Retrieved default game id is: " + defaultGameId);
        System.out.println("Retrieved default game text is: " + defaultGameText);

        Assertions.assertTrue(defaultGameText.contains("Sudoku"));

    }


    @DisplayName("Get requested game name and id")
    @Test
    public void getRequestedGameAndId(){


              Response rp =given()
                           .queryParam("name","chess"). //Hard coded
                        when()
                           .get();
             GamePojo gamePojo = rp.getBody().as(GamePojo.class);

             String queryParamGameText = gamePojo.getText();
             Integer queryParamGameId = gamePojo.getId();

        System.out.println("Query Param Game text is: " + queryParamGameText);
        System.out.println("Query Param Game id is: " + queryParamGameId);

        Assertions.assertTrue(queryParamGameText.contains("chess"), "=> Assertion Fails! param text not equals " + queryParamGameText);


    }



    @DisplayName("Schema check the game query parameter and id")
    @Test
    public void checkQueryParamSchema(){

        given()
                .queryParam("name","chess").
        when()
                .get().
        then()
                .log().body()
                .body(matchesJsonSchemaInClasspath("game.json"));



    }



}
