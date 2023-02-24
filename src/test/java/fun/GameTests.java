package fun;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@WebMvcTest(GameController.class)
public class GameTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private Map<Integer, Integer> map;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGreeting() throws Exception {
        when(map.containsKey(1)).thenReturn(false);
        when(map.containsKey(2)).thenReturn(false);
        when(map.containsKey(3)).thenReturn(true);
        when(map.get(3)).thenReturn(2);

        mockMvc.perform(get("/game").param("name", "Chess"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.text").value("Playing Chess is fun!"));

        mockMvc.perform(get("/game").param("name", "Sudoku"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.text").value("Playing Sudoku is fun!"));

        mockMvc.perform(get("/game").param("name", "Chess"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.text").value("Playing Chess is fun!"));
    }
}
