package fun;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.github.fge.jsonschema.main.JsonSchema;
import com.github.fge.jsonschema.main.JsonSchemaFactory;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class GameTests {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ResourceLoader resourceLoader;

    private String baseUrl;
    private JsonSchema schema;

    @BeforeEach
    public void setup() throws IOException, ProcessingException {
        this.baseUrl = "http://localhost:" + port + "/game";

        // Load and compile the JSON schema
        Resource schemaResource = resourceLoader.getResource("classpath:game-schema.json");
        JsonSchemaFactory schemaFactory = JsonSchemaFactory.byDefault();
        this.schema = schemaFactory.getJsonSchema(schemaResource.getInputStream());
    }

    @Test
    public void shouldReturnDefaultGame() {
        ResponseEntity<Game> response = restTemplate.getForEntity(baseUrl, Game.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        Game game = response.getBody();
        assertNotNull(game);
        assertNotNull(game.getId());
        assertNotNull(game.getText());
        assertEquals("Playing Sudoku is fun!", game.getText());
    }

    @Test
    public void shouldReturnCustomGame() {
        ResponseEntity<Game> response = restTemplate.getForEntity(baseUrl + "?name=Chess", Game.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        Game game = response.getBody();
        assertNotNull(game);
        assertNotNull(game.getId());
        assertNotNull(game.getText());
        assertEquals("Playing Chess is fun!", game.getText());
    }

    @Test
    public void shouldParseJson() throws IOException {
        Resource responseResource = new ClassPathResource("game-response.json");
        Game game = objectMapper.readValue(responseResource.getInputStream(), Game.class);
        assertNotNull(game);
        assertNotNull(game.getId());
        assertNotNull(game.getText());
        assertEquals("Playing Sudoku is fun!", game.getText());
    }

    @Test
    public void shouldValidateJsonSchema() throws IOException, ProcessingException {
        String json = restTemplate.exchange(RequestEntity.get(baseUrl).accept(MediaType.APPLICATION_JSON).build(), String.class).getBody();
        schema.validate(objectMapper.readTree(json));
    }
}