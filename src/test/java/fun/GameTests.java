package fun;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.SpecVersion;
import com.networknt.schema.ValidationMessage;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
@SpringBootTest
public class GameTests {

    //Declarations
    private static final String APPLICATION_URL = "http://localhost:8080/game?name=";
    private static final String DEFAULT_RESPONSE = "Playing Sudoku is fun!";
    private static final String SAMPLE_RESPONSE = "{\"id\":4181,\"text\":\"Playing Sudoku is fun!\"}";
    private static final String SCHEMA_PATH = "src/main/resources/schema/game-schema.json";
    private enum HttpMethod {
        GET("GET"), POST("POST"), PUT("PUT"), DELETE("DELETE");
        private final String method;
        HttpMethod(String method) { this.method = method; }
        public String toString() { return method; }
    }
    private GameController gameController;

    @BeforeEach
    public void setUp(){
        gameController = new GameController();
    }

    //Basic Test case
    @Test
    void chessGreeting() {
        Game greeting = gameController.greeting("Chess");
        String expectedResult = "Playing Chess is fun!";
        assertEquals( expectedResult, greeting.getText());
    }

    //Test case to check if the latest ID is greater than first ID
    @Test
    void compareGreetings() {
        Game greeting1 = gameController.greeting("Chess");
        Game greeting2 = gameController.greeting("Snake-Ladder");
        Game greeting3 = gameController.greeting("Uno");
        String expectedResult1 = "Playing Chess is fun!";
        String expectedResult2 = "Playing Snake-Ladder is fun!";
        String expectedResult3 = "Playing Uno is fun!";
        int expectedId1 = greeting1.getId();
        int expectedId3 = greeting3.getId();

        //ID of 3rd request should be greater than ID of First result because of Fibonacci
        assertEquals(true, expectedId3>expectedId1);
    }

    //TODO - Server needs to be ON for this test case as it expects response from localhost:8080
    //This test case checks the response for calling /game without any parameters and expects Sudoku
    @Test
    public void withoutNameParameter() {
        StringBuffer response = null;
        Game game = null ;
        try {
            URL url = new URL(APPLICATION_URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod(HttpMethod.GET.toString());
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            response = new StringBuffer();
            while ((line = in.readLine()) != null) {
                response.append(line);
            }
            ObjectMapper objectMapper = new ObjectMapper();
            game = objectMapper.readValue(response.toString(), Game.class);
            log.info("Server Response: "+ game.getText());
        } catch (Exception e) {
            System.out.println("Server Response Error: " + e.getMessage());
        }
        assertEquals(DEFAULT_RESPONSE, game.getText());
    }

    //This test case validates the JSON Schema and errors (if any) will be stored in validationResult
    @Test
    public void validateJSONSchema(){
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode json = null;
        InputStream schemaStream = null;
        try {
            JsonSchemaFactory schemaFactory = JsonSchemaFactory.getInstance( SpecVersion.VersionFlag.V7 );
            json = objectMapper.readTree(SAMPLE_RESPONSE);
            schemaStream = new FileInputStream(SCHEMA_PATH);
            JsonSchema schema = schemaFactory.getSchema(schemaStream);
            Set<ValidationMessage> validationResult = schema.validate( json );
            assertTrue(validationResult.isEmpty());
        } catch (JsonProcessingException | FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    //This test case picks a random number (N) between 5 and 20 and then creates game objects
    //named GameLoop1, GameLoop2, GameLoop3 so on... till GameLoopN
    //It then checks the sum of latest ID and compares it with the last two games ids
    //and verifies if the Fibonacci Sequence is being followed i.e. latest ID = sum of last two IDs
    @Test
    public void verifyFibonacciSequence(){
        int count = getRandomNumber();
        List<Game> games = new ArrayList<>();
        for(int i=1; i<=count; i++) {
            String gameName = "GameLoop"+i ;
            Game newGame = gameController.greeting(gameName);
            games.add(newGame);
            log.info("Adding game : " + newGame.toString());
        }
        int lastTwoSum = games.get(games.size()-2).getId() + games.get(games.size()-3).getId();
        int latestResult = games.get(games.size()-1).getId();
        log.info("Last Two Sum: "+games.get(games.size()-2).getId()+"+"+games.get(games.size()-3).getId()+"=" +lastTwoSum);
        log.info("Latest Result: "+games.get(games.size()-1).getId());
        assertEquals(true, latestResult==lastTwoSum);
    }

    //Utility function to get a Random Number
    public static int getRandomNumber() {
        int random = 5 + new Random().nextInt(20);
        log.info("Setting Random Number as: " + random);
        return random; //between 5 and 20
    }
}
