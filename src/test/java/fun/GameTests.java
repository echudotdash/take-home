package fun;

import Model.Data;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.SpecVersion;
import com.networknt.schema.ValidationMessage;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.TestPropertySource;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Set;

import static org.assertj.core.api.Assertions.fail;

public class GameTests {
    @Autowired
    private GameController gameController = new GameController();
    private static Logger logger = LoggerFactory.getLogger(GameTests.class);

    private ObjectMapper mapper = new ObjectMapper();

    private String[] games = {"Checkers", "Chess", "Poker", "Xbox", "PlayStation", "Sports", "Uno"};
    private JsonSchema schema = getJsonSchemaFromClasspath("schema.json");

    protected JsonSchema getJsonSchemaFromClasspath(String name) {
        JsonSchemaFactory factory = JsonSchemaFactory.getInstance(SpecVersion.VersionFlag.V4);
        InputStream is = Thread.currentThread().getContextClassLoader()
                .getResourceAsStream(name);
        return factory.getSchema(is);
    }

    protected JsonNode getJsonNodeFromStringContent(String content) throws IOException {
        return mapper.readTree(content);
    }

    @BeforeAll
    static void setup() {
        logger.info("Beginning tests for: /game");
    }

    /**
     * Testing the method without a parameter in theory should default the name of the game
     * to Sudoku. However it does not initialize that variable unless method call is from
     * and endpoint. Therefore the text string will never contain a default value
     */
    @Test
    void testForDefaultUri() {
        logger.info("Testing http://localhost:8080/game?name=");
        String name = " ";
        Game game = gameController.greeting(name);
        assert(game.getText().contains("Sudoku"));
    }
    @Test
    void testIdGreaterThanZero() throws IOException {
        logger.info("Testing for id constraints");
        Game game = gameController.greeting("Checkers");
        Data data = new Data(0, game.getText());    // Intentional fail as id does not meet test requirements

        JsonNode node = getJsonNodeFromStringContent(mapper.writeValueAsString(data));
        Set<ValidationMessage> errors = schema.validate(node);
        for (ValidationMessage msg : errors) {
            System.out.println(msg);
        }
        assert(errors.size() == 1);
    }
    @Test
    void testFibNumbers() {
        logger.info("Inside fib test");
        ArrayList<Data> dataList = new ArrayList<>();
        for(String gameName : games) {
            Game game = gameController.greeting(gameName);
            Data data = new Data(game.getId(), game.getText());
            dataList.add(data);
        }
        int highestIdIndex = dataList.size();

        assert(dataList.get(highestIdIndex-1).getId() == 13);   //Seventh Sequence in fib should be 13
    }

    @Test
    void testTextContainsString() {
        logger.info("Testing response string for appended message");
        String pathVar = "Test";
        Game game = gameController.greeting(pathVar);
        assert(game.getText().contains(pathVar));
    }
    @AfterAll
    static void finish() {
        logger.info("Finished running tests.");
    }
}
