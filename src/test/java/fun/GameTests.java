package fun;

import Model.Pojo;
import org.junit.jupiter.api.*;
import io.restassured.response.Response;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.*;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import java.io.File;


public class GameTests {

    @Autowired
    private static Logger logger = LoggerFactory.getLogger(GameTests.class);

    public final static String BaseURL= "http://localhost:8080/game?name=";


    @BeforeAll
    static void setup(){
        logger.info("Starting tests for endpoint /game");
    }
    @Test
    public void testWithParams(){
        logger.info("Testing with random parameters with URL");
        String queryParams[] = {"PS5", "Xbox", "Chess"};
        for(String gameName: queryParams){
            Response response = get(BaseURL+gameName);
            Pojo data = response.as(Pojo.class);
            response.then()
                    .log().all()
                    .statusCode(200)
                    .body("text", equalTo("Playing "+gameName+" is fun!"))
                    .contentType("application/json");

            assertNotNull(data.getId());
            assertTrue(data.getText().contains(gameName));
        }
    }

    @Test
    public void testWithoutParams(){
        logger.info("Testing without any parameters (default would be Sudoku)");
        String defaultGameName = "Sudoku";
        Response response = get(BaseURL);
        Pojo data = response.as(Pojo.class);
        response.then()
                .log().all()
                .statusCode(200)
                .contentType("application/json")
                .body("text",equalTo("Playing "+defaultGameName+" is fun!"));
        assertNotNull(data.getId());
        assertTrue(data.getText().contains(defaultGameName));
    }

    @Test
    public void schemaTest(){
        given().get(BaseURL).then()
                .assertThat().body(JsonSchemaValidator.matchesJsonSchema(new File("src/main/java/Resources/schema.json")));
    }


    @AfterAll
    static void end(){
        logger.info("Finished testing");
    }

}
