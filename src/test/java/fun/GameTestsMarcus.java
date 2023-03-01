package fun;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;


public class GameTestsMarcus {

    private static final String BASE_URL = "httphttp://localhost:8080/game";
    private int requestCount;
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        objectMapper = new ObjectMapper();
        requestCount=0;
    }


    @Test
    @DisplayName("returns ID and text for requested game ")
    public void testGetGameById() throws IOException, MalformedURLException {
        String gameId = "123";
        URL url = new URL(BASE_URL +"?id+"+gameId);
        URLConnection connection = url.openConnection();
        connection.connect();
        GameResponseData responseData = objectMapper.readValue(connection.getInputStream(), GameResponseData.class);

Assert.assertEquals("123",responseData.getId());
Assert.assertEquals("Sudoku",responseData.getText());
        }
@Test
@DisplayName("sets Sudoku if no Query Parameter is provided and increments the ID")
public void testGetDefaultGame() throws MalformedURLException, IOException{
        URL url = new URL(BASE_URL);
        URLConnection connection = url.openConnection();
        connection.connect();

    GameResponseData responseData = objectMapper.readValue(connection.getInputStream(), GameResponseData.class);
Assert.assertEquals("default"+ requestCount, responseData.getId());
Assert.assertEquals("Sudoku", responseData.getText());
requestCount++;
}


    public void testGameNameEndPoint() throws IOException, ProcessingException {
        String[] gamesNames = {"Sudoku", "Chess", "Go", "Backgamon", "@#$%^GAme"};
        for (String gameName : gamesNames) {
            Response response = RestAssured.get("http://localhost:8080/game?name="  + gameName);
            GameResponseData Response = response.as(GameResponseData.class);

            response.then()
                    .log().all()
                    .assertThat()
                    .statusCode(200)
                    .body("id", greaterThan(0))
                    .body("text", equalTo("Playing " + gameName + " is fun!"));

            assert (Response.getId() > 0);
            assert (Response.getText().equals("Playing " + gameName + " is fun!"));
        }


    }
}




