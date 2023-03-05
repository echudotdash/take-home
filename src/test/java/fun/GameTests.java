package fun;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;
import java.util.regex.Pattern;

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

    @BeforeEach
    public void setUp() {
        this.baseUrl = "http://localhost:" + port + "/game";
    }

    @Test
    public void testDefaultGame() {
        ResponseEntity<Game> response = restTemplate.getForEntity(baseUrl, Game.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        Game game = response.getBody();
        assertNotNull(game);
        assertNotNull(game.getId());
        assertNotNull(game.getText());
        assertEquals("Playing Sudoku is fun!", game.getText());
    }

    @Test
    public void testCustomGame() {
        ResponseEntity<Game> response = restTemplate.getForEntity(baseUrl + "?name=Chess", Game.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        Game game = response.getBody();
        assertNotNull(game);
        assertNotNull(game.getId());
        assertNotNull(game.getText());
        assertEquals("Playing Chess is fun!", game.getText());
    }

    @Test
    public void testJsonParsing() throws IOException {
        Resource resource = new ClassPathResource("game_testResponse.json");
        Game game = objectMapper.readValue(resource.getInputStream(), Game.class);
        assertNotNull(game);
        assertNotNull(game.getId());
        assertNotNull(game.getText());
        assertEquals("Playing Sudoku is fun!", game.getText());
    }

    @Test
    public void testJsonSchema() throws IOException {
        Resource resource = new ClassPathResource("game_testResponse.json");
        String json = restTemplate.exchange(baseUrl, RequestEntity.get(baseUrl).accept(MediaType.APPLICATION_JSON).build(), String.class).getBody();
        JsonSchemaValidator.createValidator(resourceLoader.getResource("classpath:game_testResponse.json").getInputStream()).validate(json);
    }
}



}
