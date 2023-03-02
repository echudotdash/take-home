package fun;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(GameController.class)
public class GameTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getGame_returnsDefaultGame() throws Exception {
        Game expectedGame = new Game("Playing Sudoku");

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/game"))
                .andExpect(status().isOk())
                .andReturn();

        Game actualGame = fromJson(result.getResponse().getContentAsString(), Game.class);
        assertEquals(expectedGame.getId(), actualGame.getId());
        assertEquals(expectedGame.getText(), actualGame.getText());
    }


    @Test
    void getGame_returnsCustomGame() throws Exception {
        Game expectedGame = new Game("Playing Chess");

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/game?name=Chess"))
                .andExpect(status().isOk())
                .andReturn();

        Game actualGame = fromJson(result.getResponse().getContentAsString(), Game.class);
        assertEquals(expectedGame.getId(), actualGame.getId());
        assertEquals(expectedGame.getText(), actualGame.getText());
    }

    private static <T> T fromJson(String json, Class<T> clazz) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(json, clazz);
    }

}
